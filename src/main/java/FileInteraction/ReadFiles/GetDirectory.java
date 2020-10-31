package FileInteraction.ReadFiles;

import FileInteraction.Tools.JFileChooserPreset;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GetDirectory {
    public static List<File> files(File directory) {
        assert directory.isDirectory();
        return Arrays.asList(Objects.requireNonNull(directory.listFiles()));
    }

    public static File ChosenDirectory(){
        File path = FileSystemView.getFileSystemView().getHomeDirectory();
        JFileChooser j = JFileChooserPreset.importdirectory();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }
}
