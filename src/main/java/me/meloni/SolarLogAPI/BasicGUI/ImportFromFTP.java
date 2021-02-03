package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;

public class ImportFromFTP {
    public static SolarMap importWithGUI() throws ParseException, IOException {
        SolarMap solarMap = new SolarMap();
        String server = JOptionPane.showInputDialog("Server?");
        String username = JOptionPane.showInputDialog("User?");
        String password = JOptionPane.showInputDialog("Password?");
        solarMap.addFromFTPServer(server, username, password);
        return solarMap;
    }
}
