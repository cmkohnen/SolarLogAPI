package TransformUtilities.ReadFiles;

import Handling.CustomLogger;

import java.io.IOException;
import java.util.List;

public class Validate {
    static String identifier = "8;4";


    public static boolean IsValidFile(String path) throws IOException {
        return GetFileContent.FileContentAsList(path).get(1).equalsIgnoreCase(identifier);

    }
}
