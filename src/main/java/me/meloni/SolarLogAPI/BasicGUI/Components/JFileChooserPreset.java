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
package me.meloni.SolarLogAPI.BasicGUI.Components;

import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.Validate;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;

/**
 * This class includes presets for a JFileChooser to filter .dat files.
 * @author Christoph Kohnen
 * @since 0.0.2
 */
public class JFileChooserPreset {

    /**
     * Get a chosen .dat file preset
     * @return The chosen .dat file preset
     */
    public static JFileChooser importFromDat(){
        JFileChooser j = new JFileChooser();
        j.setDialogTitle("Import");
        j.setAcceptAllFileFilterUsed(false);
        j.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.isDirectory()) {return true;}
                if(f.getName().contains(".dat")){
                    try {
                        return Validate.isValidDatFile(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } return false;
            }

            @Override
            public String getDescription() {
                return "Data files (*.dat)";
            }
        });
        return j;
    }

    /**
     * Get a chosen SolarLog file preset
     * @return A chosen SolarLog file preset
     */
    public static JFileChooser importFromSolarLogFile() {
        JFileChooser j = new JFileChooser();
        j.setDialogTitle("Import");
        j.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().contains(".solarlog") || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Solarlog files (*.solarlog)";
            }
        });
        return j;

    }

    /**
     * Get a chosen GZipped tarball preset
     * @return A chosen GZipped tarball preset
     */
    public static JFileChooser importFromTar(){
        JFileChooser j = new JFileChooser();
        j.setDialogTitle("Import");
        j.setAcceptAllFileFilterUsed(false);
        j.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.isDirectory()) {return true;}
                return f.getName().contains(".tar.gz");
            }

            @Override
            public String getDescription() {
                return "GZipped tarballs (*.tar.gz)";
            }
        });
        return j;
    }

    /**
     * Get a chosen .eml file preset
     * @return A chosen .eml file preset
     */
    public static JFileChooser importFromEML(){
        JFileChooser j = new JFileChooser();
        j.setDialogTitle("Import");
        j.setAcceptAllFileFilterUsed(false);
        j.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().contains(".eml");
            }

            @Override
            public String getDescription() {
                return "EML files (*.eml)";
            }
        });
        return j;
    }

    /**
     * Get a chosen .js file preset
     * @return A chosen .js file preset
     */
    public static JFileChooser importFromJSFile(){
        JFileChooser j = new JFileChooser();
        j.setDialogTitle("Import");
        j.setAcceptAllFileFilterUsed(false);
        j.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().contains(".js");
            }

            @Override
            public String getDescription() {
                return "JS files (*.js)";
            }
        });
        return j;
    }

    /**
     * Get a chosen directory preset
     * @return A chosen directory preset
     */
    public static JFileChooser getChosenDirectory() {
        JFileChooser j = new JFileChooser();
        j.setDialogTitle("Import");
        j.setAcceptAllFileFilterUsed(false);
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        return j;
    }

    /**
     * Get a save location for a SolarLog file preset
     * @return The chosen Save location for a SolarLog file preset
     */
    public static JFileChooser saveToSolarLogFile() {
        JFileChooser j = new JFileChooser();
        j.setDialogTitle("Save");
        j.setAcceptAllFileFilterUsed(false);
        j.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.canWrite() & f.getName().contains(".solarlog") || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Solarlog files (*.solarlog)";
            }
        });
        return j;
    }
}
