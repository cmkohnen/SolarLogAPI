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

import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.io.IOException;

/**
 * This class includes a function to call a GUI capable of importing data from an FTP server
 * @author Christoph Kohnen
 * @since 3.10.0
 */
public class ImportFromFTP {
    /**
     * Import all data from an FTP-Server with an GUI
     * @return A {@link SolarMap} with all data extracted from .js files on the server
     * @throws IOException If something goes wrong with the connection
     */
    public static SolarMap importWithGUI() throws IOException {
        SolarMap solarMap = new SolarMap();
        String server = JOptionPane.showInputDialog("Server?");
        String username = JOptionPane.showInputDialog("User?");
        String password = JOptionPane.showInputDialog("Password?");
        solarMap.addFromFTPServer(server, username, password);
        return solarMap;
    }
}
