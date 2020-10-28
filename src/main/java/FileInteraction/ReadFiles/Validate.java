package FileInteraction.ReadFiles;

import FileInteraction.GetFile;
import FileInteraction.Tools.FileVersion;
import Handling.Logger;

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
        if(file.getName().contains(".dat") & file.canRead()) {
            if(GetFileContent.FileContent(GetFile.Path(file)).startsWith("#")){
                if(FileVersion.isSupported(file)){
                        valid = true;
                }
            }
        }


        return valid;

    }

    public static List<File> validfiles(List<File> files) throws IOException {
        List<File> ValidFiles = new ArrayList<>();
        int i = 0;
        int i2 = 0;
        for (File file : files) {
            i++;
            Logger.log("Validating file " + file + " (" + (i - 1) + " of " + files.size() + " done, found: " + i2 + ")");
            if(IsValidDataFile(file)) {
                ValidFiles.add(file);
                i2++;
            }
        }
        Logger.log("Done. Checked " + i + ", found " + i2);
        return ValidFiles;
    }
}
