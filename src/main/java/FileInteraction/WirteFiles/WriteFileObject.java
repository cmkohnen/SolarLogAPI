package FileInteraction.WirteFiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
/**
 * This Class includes a function to write an object to a specified file.
 * @author ChaosMelone9
 * @since 0.0.2
 */
public class WriteFileObject {
    public static void write(File file, Object object) throws IOException {
        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream s = new ObjectOutputStream(f);
        s.writeObject(object);
        s.flush();
        s.close();
    }
}
