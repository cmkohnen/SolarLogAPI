package FileInteraction;

import FileInteraction.ReadFiles.GetDirectory;
import FileInteraction.ReadFiles.Validate;
import FileInteraction.Tools.JFileChooserPreset;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public static File ChosenTarArchive() {
        File path = FileSystemView.getFileSystemView().getHomeDirectory();
        JFileChooser j = JFileChooserPreset.importtar(path);
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static File ChosenSafeLocation(){
        File path = FileSystemView.getFileSystemView().getHomeDirectory();
        JFileChooser j = JFileChooserPreset.SafetoFile(path);
        j.showOpenDialog(null);
        File f = j.getSelectedFile();
        if(!f.getName().contains(".solarlog")) {
            return File(f.getAbsolutePath() + ".solarlog");
        } else {
            return f;
        }
    }

    public static File ChosenReadLocation(){
        File path = FileSystemView.getFileSystemView().getDefaultDirectory();
        JFileChooser j = JFileChooserPreset.ReadfromFile(path);
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static List<File> ChosenValidFilesInDirectory() throws IOException {
        return Validate.validfiles(GetDirectory.files(GetDirectory.ChosenDirectory()));
    }

    public static List<File> ChosenTarsInDirectory() {
        File dir = GetDirectory.ChosenDirectory();
        return Arrays.asList(Objects.requireNonNull(dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".tar.gz"))));
    }

}
