package FileInteraction;

import FileInteraction.ReadFiles.Validate;

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

    public static JFileChooser importer(File path){
        JFileChooser j = new JFileChooser(path);
        j.setDialogTitle("Import");
        j.setAcceptAllFileFilterUsed(false);
        j.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.isDirectory()) {return true;}
                if(f.getName().contains(".dat")){
                    try {
                        return Validate.IsValidDataFile(f);
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

    public static JFileChooser SafetoFile(File path) {
        JFileChooser j = new JFileChooser(path);
        j.setDialogTitle("Safe");
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

    public static JFileChooser ReadfromFile(File path) {
        JFileChooser j = new JFileChooser(path);

        // invoke the showsSaveDialog function to show the save dialog
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

    public static JFileChooser importtar(File path){
        JFileChooser j = new JFileChooser(path);
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
                return "Tar archives (*.tar.gz)";
            }
        });
        return j;
    }

    public static JFileChooser importdirectory(File path) {
        JFileChooser j = new JFileChooser(path);
        j.setDialogTitle("Import");
        j.setAcceptAllFileFilterUsed(false);
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        return j;
    }

}
