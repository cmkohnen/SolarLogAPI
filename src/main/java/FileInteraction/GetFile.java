package FileInteraction;

import FileInteraction.ReadFiles.GetDirectory;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

    public static File ValidChosenDataFile() {
        File path = FileSystemView.getFileSystemView().getHomeDirectory();
        JFileChooser j = JFileChooserPreset.importer(path);
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static File ChosenSafeLocation(){
        File path = FileSystemView.getFileSystemView().getHomeDirectory();
        JFileChooser j = JFileChooserPreset.SafetoFile(path);
        j.showOpenDialog(null);
        File f = j.getSelectedFile();
        if(!f.getName().contains(".solarlog")) {
            f = File(f.getName() + ".solarlog");
        }
        return f;
    }

    public static File ChosenReadLocation(){
        File path = FileSystemView.getFileSystemView().getHomeDirectory();
        JFileChooser j = JFileChooserPreset.ReadfromFile(path);
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static List<File> ChosenValidFilesInDirectory() throws IOException {
        return GetDirectory.validfiles(GetDirectory.files(GetDirectory.ChosenDirectory()));
    }
}
