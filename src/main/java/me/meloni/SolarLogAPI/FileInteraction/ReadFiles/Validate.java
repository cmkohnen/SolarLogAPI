package me.meloni.SolarLogAPI.FileInteraction.ReadFiles;

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
    public static boolean isValidDataFile(File file) throws IOException {
        boolean valid = false;
        if(file.getName().contains(".dat") & file.getName().contains("backup_data") & file.canRead()) {
                if(FileVersion.isSupported(file)){
                        valid = true;
                }
        }
        return valid;
    }

    public static List<File> getValidFiles(List<File> files) throws IOException {
        List<File> ValidFiles = new ArrayList<>();
        int i = 0;
        int i2 = 0;
        for (File file : files) {
            i++;
            Logger.logWithoutBreakup(Logger.INFO_LEVEL_3 + "Validating file " + file + " (" + i + " of " + files.size() + ")");
            if(isValidDataFile(file)) {
                ValidFiles.add(file);
                i2++;
                Logger.logWithoutBreakup("    yes\n");
            } else {
                Logger.logWithoutBreakup("    no\n");
            }
        }
        Logger.log(Logger.INFO_LEVEL_3 + "Done. Checked " + i + ", found " + i2);
        return ValidFiles;
    }

    public static boolean isValidSolarLogFile(File f) throws IOException, ClassNotFoundException {
        if(f.getName().contains(".solarlog")) {
            FileObject fileObject = ReadFileObject.getObjectFromFile(f);
            return (boolean) fileObject.getInformation("valid");
        }
        return false;
    }
}
