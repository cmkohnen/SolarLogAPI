package me.meloni.Testbench;

import javax.swing.*;
import java.io.File;

public class Testfilechooser {
    public static JFileChooser chooser() {
        JFileChooser j = new JFileChooser();
        j.setDialogTitle("Open");
        return j;
    }

    public static File chosenFile() {
        JFileChooser j = chooser();
        j.showOpenDialog(null);
        return j.getSelectedFile();
    }
}
