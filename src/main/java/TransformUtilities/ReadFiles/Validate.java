package TransformUtilities.ReadFiles;

import java.io.IOException;
import java.nio.file.Path;
/**
 * This Class includes functions to validate a file.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class Validate {
    static String identifier = "8;4";


    public static boolean IsValidFile(Path path) throws IOException {
        return GetFileContent.FileContentAsList(path).get(1).equalsIgnoreCase(identifier);

    }
}
