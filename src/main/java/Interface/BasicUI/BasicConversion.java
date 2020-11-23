package Interface.BasicUI;

import FileInteraction.GetFile;
import FileInteraction.Tools.FileObject;
import FileInteraction.WirteFiles.WriteFileObject;

import javax.swing.*;
import java.io.IOException;

public class BasicConversion extends JPanel {

    public static void run() throws IOException {
        FileObject fileObject = new FileObject(BasicSolarMapCustomizer.solarMap());
        WriteFileObject.write(GetFile.ChosenSafeLocation(),fileObject);
    }
}
