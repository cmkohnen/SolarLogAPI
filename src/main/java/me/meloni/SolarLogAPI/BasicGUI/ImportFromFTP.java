package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.Handling.Translation;
import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;

public class ImportFromFTP {
    public static SolarMap importWithGUI() throws ParseException, IOException {
        SolarMap solarMap = new SolarMap();
        String server = JOptionPane.showInputDialog(Translation.get("gui_ftp_server"));
        String username = JOptionPane.showInputDialog(Translation.get("gui_ftp_user"));
        String password = JOptionPane.showInputDialog(Translation.get("gui_ftp_password"));
        solarMap.addFromFTPServer(server, username, password);
        return solarMap;
    }
}
