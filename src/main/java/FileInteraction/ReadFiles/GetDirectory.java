package FileInteraction.ReadFiles;

import FileInteraction.JFileChooserPreset;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GetDirectory {
    public static List<File> files(File directory) {
        assert directory.isDirectory();
        return Arrays.asList(Objects.requireNonNull(directory.listFiles()));
    }

    public static List<File> validfiles(List<File> files) throws IOException {
        List<File> validfiles = new ArrayList<>();
        for (File file : files) {
            if(Validate.IsValidDataFile(file)){
                validfiles.add(file);
            }
        }
        return validfiles;
    }

    public static File ChosenDirectory(){
        File path = FileSystemView.getFileSystemView().getHomeDirectory();
        JFileChooser j = JFileChooserPreset.importdirectory(path);
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }
}