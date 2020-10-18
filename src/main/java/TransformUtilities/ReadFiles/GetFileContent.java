package TransformUtilities.ReadFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class GetFileContent {

    private static String FileContent(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static List<String> FileContentAsList(String path) throws IOException {
        return Arrays.asList(FileContent(path).split("\n"));
    }
}
