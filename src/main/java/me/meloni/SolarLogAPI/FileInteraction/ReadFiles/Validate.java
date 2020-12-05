package me.meloni.SolarLogAPI.FileInteraction.ReadFiles;

import me.meloni.SolarLogAPI.FileInteraction.GetFile;
import me.meloni.SolarLogAPI.FileInteraction.Tools.FileObject;
import me.meloni.SolarLogAPI.FileInteraction.Tools.FileVersion;
import me.meloni.SolarLogAPI.Handling.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class includes functions to validate a file.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class Validate {
    public static boolean IsValidDataFile(File file) throws IOException {
        boolean valid = false;
        if(file.getName().contains(".dat") & file.getName().contains("backup_data") & file.canRead()) {
            if(GetFileContent.FileContent(GetFile.Path(file)).startsWith("#")){
                if(FileVersion.isSupported(file)){
                        valid = true;
                }
            }
        }


        return valid;

    }

    public static List<File> validFiles(List<File> files) throws IOException {
        List<File> ValidFiles = new ArrayList<>();
        int i = 0;
        int i2 = 0;
        for (File file : files) {
            i++;
            Logger.logWithIncomingBoolean("Validating file " + file + " (" + i + " of " + files.size() + ")");
            if(IsValidDataFile(file)) {
                ValidFiles.add(file);
                i2++;
                Logger.logTheBoolean(true);
            } else {
                Logger.logTheBoolean(false);
            }
        }
        Logger.log("Done. Checked " + i + ", found " + i2);
        return ValidFiles;
    }

    public static boolean isValidSolarLogFile(File f) throws IOException, ClassNotFoundException {
        if(f.getName().contains(".solarlog")) {
            FileObject fileObject = ReadFileObject.fileObject(GetFile.ChosenReadLocation());
            return (boolean) fileObject.getInformation("valid");
        }
        return false;
    }
}
