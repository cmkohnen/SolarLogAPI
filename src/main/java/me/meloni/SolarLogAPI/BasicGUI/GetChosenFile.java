package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.BasicGUI.Components.JFileChooserPreset;
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

    public static File chosenDatFile() {
        JFileChooser j = JFileChooserPreset.importFromDat();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static File chosenTarArchive() {
        JFileChooser j = JFileChooserPreset.importFromTar();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static File chosenSaveLocation(){
        JFileChooser j = JFileChooserPreset.saveToSolarLogFile();
        j.showSaveDialog(null);
        File f = j.getSelectedFile();
        if(f == null) {
            return null;
        }
        if(!f.getName().contains(".solarlog")) {
            return GetFile.getFileFromPath(f.getAbsolutePath() + ".solarlog");
        } else {
            return f;
        }
    }

    public static File chosenEMLFile() {
        JFileChooser j = JFileChooserPreset.importFromEML();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static File chosenSolarLogFile(){
        JFileChooser j = JFileChooserPreset.importFromSolarLogFile();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static File chosenJSFile(){
        JFileChooser j = JFileChooserPreset.importFromJSFile();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    public static List<File> chosenDatFilesInDirectory() throws IOException {
        File dir = chosenDirectory();
        if(!(dir == null) && dir.exists()) {
            return Validate.getValidFiles(GetDirectory.getFiles(dir));
        } else {
            return null;
        }
    }

    public static List<File> chosenTarArchivesInDirectory() {
        File dir = chosenDirectory();
        if(!(dir == null) && dir.exists()) {
            return Arrays.asList(dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".tar.gz")));
        } else {
            return null;
        }
    }

    public static List<File> chosenEMLFilesInDirectory() {
        File dir = chosenDirectory();
        if(!(dir == null) && dir.exists()) {
            return Arrays.asList(dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".eml")));
        } else {
            return null;
        }
    }

    public static List<File> chosenJSFilesInDirectory() {
        File dir = chosenDirectory();
        if(!(dir == null) && dir.exists()) {
            return Arrays.asList(dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".js")));
        } else {
            return null;
        }
    }

    public static File chosenDirectory(){
        JFileChooser j = JFileChooserPreset.getChosenDirectory();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }
}
