package TransformUtilities.ReadFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class GetFileContent {

    private static String FileContent(Path path) throws IOException {
        return new String(Files.readAllBytes(path));
    }

    public static List<String> FileContentAsList(Path path) throws IOException {
        return Arrays.asList(FileContent(path).split("\n"));
    }
}
