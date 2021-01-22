package me.meloni.SolarLogAPI.BasicGUI.Components;

import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.Validate;
import me.meloni.SolarLogAPI.Handling.Translation;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;

/**
 * This Class includes presets for a JFileChooser to filter .dat files.
 * @author ChaosMelone9
 * @since 0.0.2
 */
public class JFileChooserPreset {

    public static JFileChooser importFromDat(){
        JFileChooser j = new JFileChooser();
        j.setDialogTitle(Translation.get("gui_choose_file_title"));
        j.setAcceptAllFileFilterUsed(false);
        j.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.isDirectory()) {return true;}
                if(f.getName().contains(".dat")){
                    try {
                        return Validate.isValidDataFile(f);
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

    public static JFileChooser saveToSolarLogFile() {
        JFileChooser j = new JFileChooser();
        j.setDialogTitle(Translation.get("gui_save_file_title"));
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

    public static JFileChooser importFromSolarLogFile() {
        JFileChooser j = new JFileChooser();

        // invoke the showsSaveDialog function to show the save dialog
        j.setDialogTitle(Translation.get("gui_choose_file_title"));
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

    public static JFileChooser importFromTar(){
        JFileChooser j = new JFileChooser();
        j.setDialogTitle(Translation.get("gui_choose_file_title"));
        j.setAcceptAllFileFilterUsed(false);
        j.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.isDirectory()) {return true;}
                return f.getName().contains(".tar.gz");
            }

            @Override
            public String getDescription() {
                return "Tar archives (*.tar.gz)";
            }
        });
        return j;
    }

    public static JFileChooser importFromEML(){
        JFileChooser j = new JFileChooser();
        j.setDialogTitle(Translation.get("gui_choose_file_title"));
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

    public static JFileChooser importFromJSFile(){
        JFileChooser j = new JFileChooser();
        j.setDialogTitle(Translation.get("gui_choose_file_title"));
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

    public static JFileChooser getChosenDirectory() {
        JFileChooser j = new JFileChooser();
        j.setDialogTitle(Translation.get("gui_choose_file_title"));
        j.setAcceptAllFileFilterUsed(false);
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        return j;
    }
}
