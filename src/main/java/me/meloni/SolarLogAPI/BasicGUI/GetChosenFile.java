/*
Copyright 2020 - 2021 Christoph Kohnen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.BasicGUI.Components.JFileChooserPreset;
import me.meloni.SolarLogAPI.FileInteraction.GetDirectory;
import me.meloni.SolarLogAPI.FileInteraction.GetFile;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.Validate;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * This class includes functions to get a file via user input.
 * @author Christoph Kohnen
 * @since 3.3.0
 */
public class GetChosenFile {

    /**
     * Get a chosen .dat file
     * @return A chosen .dat file
     */
    public static File chosenDatFile() {
        JFileChooser j = JFileChooserPreset.importFromDat();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    /**
     * Get a chosen GZipped tarball
     * @return A chosen GZipped tarball
     */
    public static File chosenTarArchive() {
        JFileChooser j = JFileChooserPreset.importFromTar();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    /**
     * Pick a save location for SolarLog files
     * @return A chosen save location for SolarLog files
     */
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

    /**
     * Get a chosen .eml file
     * @return A chosen .eml file
     */
    public static File chosenEMLFile() {
        JFileChooser j = JFileChooserPreset.importFromEML();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    /**
     * Get a chosen SolarLog file
     * @return A chosen SolarLog file
     */
    public static File chosenSolarLogFile(){
        JFileChooser j = JFileChooserPreset.importFromSolarLogFile();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    /**
     * Get a chosen .js file
     * @return A chosen .js file
     */
    public static File chosenJSFile(){
        JFileChooser j = JFileChooserPreset.importFromJSFile();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }

    /**
     * Get all .dat files inside a chosen directory
     * @return All .dat files inside a chosen directory
     */
    public static List<File> chosenDatFilesInDirectory() {
        File dir = chosenDirectory();
        if(!(dir == null) && dir.exists()) {
            return Validate.getValidDatFiles(GetDirectory.getFiles(dir));
        } else {
            return null;
        }
    }

    /**
     * Get all GZipped tarballs inside a chosen directory
     * @return All GZipped tarballs inside a chosen directory
     */
    public static List<File> chosenTarArchivesInDirectory() {
        File dir = chosenDirectory();
        if(!(dir == null) && dir.exists()) {
            return Arrays.asList(dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".tar.gz")));
        } else {
            return null;
        }
    }

    /**
     * Get all .eml files inside a chosen directory
     * @return All .eml files inside a chosen directory
     */
    public static List<File> chosenEMLFilesInDirectory() {
        File dir = chosenDirectory();
        if(!(dir == null) && dir.exists()) {
            return Arrays.asList(dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".eml")));
        } else {
            return null;
        }
    }

    /**
     * Get all .js files inside a chosen directory
     * @return All .js files inside a chosen directory
     */
    public static List<File> chosenJSFilesInDirectory() {
        File dir = chosenDirectory();
        if(!(dir == null) && dir.exists()) {
            return Arrays.asList(dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".js") && (name.startsWith("min") && !(name.contains("day") || name.contains("cur")))));
        } else {
            return null;
        }
    }

    /**
     * Get a chosen directory
     * @return A chosen directory
     */
    public static File chosenDirectory(){
        JFileChooser j = JFileChooserPreset.getChosenDirectory();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }
}
