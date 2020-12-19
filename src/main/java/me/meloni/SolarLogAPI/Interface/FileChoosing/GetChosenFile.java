package me.meloni.SolarLogAPI.Interface.FileChoosing;

import me.meloni.SolarLogAPI.FileInteraction.GetFile;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetDirectory;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.Validate;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * This Class includes functions to get a file via user input.
 * @author ChaosMelone9
 * @since 3.3.0
 */
public class GetChosenFile {

    public static File validChosenDataFile() {
        JFileChooser j = JFileChooserPreset.importer();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static File chosenTarArchive() {
        JFileChooser j = JFileChooserPreset.importFromTar();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static File chosenSaveLocation(){
        JFileChooser j = JFileChooserPreset.safeToFile();
        j.showOpenDialog(null);
        File f = j.getSelectedFile();
        if(f == null) {
            return null;
        }
        if(!f.getName().contains(".solarlog")) {
            return GetFile.file(f.getAbsolutePath() + ".solarlog");
        } else {
            return f;
        }
    }

    public static File chosenEMLFile() {
        JFileChooser j = JFileChooserPreset.importFromEML();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static File chosenReadLocation(){
        JFileChooser j = JFileChooserPreset.readFromFile();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static List<File> chosenValidFilesInDirectory() throws IOException {
        File dir = chosenDirectory();
        if(!(dir == null) && dir.exists()) {
            return Validate.validFiles(GetDirectory.files(dir));
        } else {
            return null;
        }
    }

    public static List<File> chosenTarsInDirectory() {
        File dir = chosenDirectory();
        if(!(dir == null) && dir.exists()) {
            return Arrays.asList(dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".tar.gz")));
        } else {
            return null;
        }
    }

    public static List<File> chosenEMLsInDirectory() {
        File dir = chosenDirectory();
        if(!(dir == null) && dir.exists()) {
            return Arrays.asList(dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".eml")));
        } else {
            return null;
        }
    }

    public static File chosenDirectory(){
        JFileChooser j = JFileChooserPreset.importFromDirectory();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }
}
