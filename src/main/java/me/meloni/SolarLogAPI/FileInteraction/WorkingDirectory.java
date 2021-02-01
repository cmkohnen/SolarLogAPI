package me.meloni.SolarLogAPI.FileInteraction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * This Class provides a way to get a directory intended for short time data storage on the file system.
 * @author ChaosMelone9
 * @since 3.1.2
 */
public class WorkingDirectory {
    private static File workingDirectory = null;

    public static File getDirectory() throws IOException {
        if(workingDirectory == null) {
            Date time = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String run = dateFormat.format(time);
            workingDirectory = new File(Paths.get("").toAbsolutePath().toString(), run);
            if(!workingDirectory.mkdirs()) {
             throw new IOException("cant create " + run);
            }
        }
        return workingDirectory;
    }

    public static void clear() throws IOException {
        File file = getDirectory();
        for (File subFile : file.listFiles()) {
            if(subFile.isDirectory()) {
                deleteFolder(subFile);
            } else {
                if(!subFile.delete()) {
                    throw new IOException("Can't delete " + subFile);
                }
            }
        }
    }

    public static void setDirectory(File directory) throws FileNotFoundException, NotDirectoryException {
        if(directory.isDirectory()) {
            if(!directory.exists()) {
                throw new FileNotFoundException();
            }
        } else throw new NotDirectoryException(directory.getName() + "is no directory.");
    }

    private static void deleteFolder(File file) throws IOException {
        for (File subFile : file.listFiles()) {
            if(subFile.isDirectory()) {
                deleteFolder(subFile);
            } else {
                if(!subFile.delete()) {
                    throw new IOException("Can't delete " + subFile.getName());
                }
            }
        }
        if(!file.delete()) {
            throw new IOException("Can't delete " + file.getName());
        }
    }
}
