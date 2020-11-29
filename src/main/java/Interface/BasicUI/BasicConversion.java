package Interface.BasicUI;

import FileInteraction.GetFile;
import FileInteraction.Tools.FileObject;
import FileInteraction.WirteFiles.WriteFileObject;
import Handling.SolarMap;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * This Class includes a function to call a simple gui capable of transforming any data into persistent storage.
 * @author ChaosMelone9
 * @since 1.0.0
 */
public class BasicConversion extends JPanel {

    public static void run() throws IOException {
        SolarMap solarMap = BasicSolarMapCustomizer.solarMap();
        FileObject fileObject = new FileObject(solarMap.getAsMap());
        File saveLocation = GetFile.ChosenSafeLocation();
        WriteFileObject.write(saveLocation,fileObject);
        System.exit(0);
    }
}
