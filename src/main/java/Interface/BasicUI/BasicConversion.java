package Interface.BasicUI;

import FileInteraction.GetFile;
import FileInteraction.Tools.FileObject;
import FileInteraction.WirteFiles.WriteFileObject;

import javax.swing.*;
import java.io.IOException;

/**
 * This Class includes a function to call a simple gui capable of transforming any data into persistent storage.
 * @author ChaosMelone9
 * @since 1.0.0
 */
public class BasicConversion extends JPanel {

    public static void run() throws IOException {
        FileObject fileObject = new FileObject(BasicSolarMapCustomizer.solarMap().getAsMap());
        WriteFileObject.write(GetFile.ChosenSafeLocation(),fileObject);
    }
}
