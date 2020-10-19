package FileInteraction.ReadFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * This Class includes the fileObject function which results in a object read from a file.
 * @author ChaosMelone9
 * @since 0.0.2
 */
public class ReadFileObject {
    public static Object fileObject(File file) throws IOException, ClassNotFoundException {
        FileInputStream f = new FileInputStream(file);
        ObjectInputStream s = new ObjectInputStream(f);
        Object o = s.readObject();
        s.close();
        return o;
    }
}
