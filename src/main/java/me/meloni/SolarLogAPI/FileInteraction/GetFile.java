package me.meloni.SolarLogAPI.FileInteraction;

import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetDirectory;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.Validate;
import me.meloni.SolarLogAPI.FileInteraction.Tools.JFileChooserPreset;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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
        JFileChooser j = JFileChooserPreset.importer();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static File ChosenTarArchive() {
        JFileChooser j = JFileChooserPreset.importFromTar();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static File ChosenSafeLocation(){
        JFileChooser j = JFileChooserPreset.SafeToFile();
        j.showOpenDialog(null);
        File f = j.getSelectedFile();
        if(!f.getName().contains(".solarlog")) {
            return File(f.getAbsolutePath() + ".solarlog");
        } else {
            return f;
        }
    }

    public static File ChosenReadLocation(){
        JFileChooser j = JFileChooserPreset.ReadFromFile();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static List<File> ChosenValidFilesInDirectory() throws IOException {
        File dir = GetDirectory.ChosenDirectory();
        if(!(dir == null) && dir.exists()) {
            return Validate.validFiles(GetDirectory.files(dir));
        } else {
            return null;
        }
    }

    public static List<File> ChosenTarsInDirectory() {
        File dir = GetDirectory.ChosenDirectory();
        if(!(dir == null) && dir.exists()) {
            return Arrays.asList(dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".tar.gz")));
        } else {
            return null;
        }
    }

}
