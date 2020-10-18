package TransformUtilities.ReadFiles;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * This Class includes functions to get a file.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class GetFile {
    public static File File(String path) {
        return new File(path);
    }

    public static Path Path(File file) {
        return Paths.get(file.getPath());
    }
}
