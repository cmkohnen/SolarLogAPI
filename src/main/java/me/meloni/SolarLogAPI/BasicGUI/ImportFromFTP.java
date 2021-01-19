package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

public class ImportFromFTP {
    public static SolarMap importWithGUI() throws ParseException, IOException, URISyntaxException {
        SolarMap solarMap = new SolarMap();
        String server = JOptionPane.showInputDialog("Server");
        String username = JOptionPane.showInputDialog("User");
        String password = JOptionPane.showInputDialog("Passwd");
        solarMap.addFromFTPServer(server, username, password);
        return solarMap;
    }
}
