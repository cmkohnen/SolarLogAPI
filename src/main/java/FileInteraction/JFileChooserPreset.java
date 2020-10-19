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

}
