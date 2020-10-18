package TransformUtilities.ReadFiles;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetFile {
    public static File File(String path) {
        return new File(path);
    }

    public static Path Path(File file) {
        return Paths.get(file.getPath());
    }
}
