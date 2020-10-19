package FileInteraction.ReadFiles;

import FileInteraction.GetFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
/**
 * This Class includes functions to validate a file.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class Validate {
    static String identifier = "8;4";


    public static boolean IsValidDataFile(File file) throws IOException {
        boolean valid = false;
        if(file.getName().contains(".dat")) {
            if(GetFileContent.FileContent(GetFile.Path(file)).startsWith("#")){
                if(GetFileContent.FileContentAsList(GetFile.Path(file)).get(1).startsWith("8;4")){
                    valid = true;
                }
            }
        }


        return valid;

    }
}
