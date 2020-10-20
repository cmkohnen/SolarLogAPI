package FileInteraction.ReadFiles;

import FileInteraction.GetFile;

import java.io.File;
import java.io.IOException;

/**
 * This Class includes functions to validate a file.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class Validate {
    static String identifier = "8;4";


    public static boolean IsValidDataFile(File file) throws IOException {
        boolean valid = false;
        if(file.getName().contains(".dat") & file.canRead()) {
            if(GetFileContent.FileContent(GetFile.Path(file)).startsWith("#")){
                if(GetFileContent.FileContentAsList(GetFile.Path(file)).get(1).startsWith(identifier)){
                    valid = true;
                }
            }
        }


        return valid;

    }
}
