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

import me.meloni.SolarLogAPI.DatabaseInteraction.SQLDatabase;
import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * This class includes a function to call a GUI capable of saving a SolarMap to the users likes.
 * @author Christoph Kohnen
 * @since 1.0.0
 */
public class BasicSaveOptions {
    /**
     * Invoke a GUI to save a {@link SolarMap} to a file or database
     * @param solarMap The map you want to save
     */
    public static void save(SolarMap solarMap) {
        JPanel panel = panel(solarMap);
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setBounds(30,30,200,400);
        f.setResizable(false);
        f.setTitle("Save");
        f.add(panel);
        f.setVisible(true);
    }

    private static JPanel panel(SolarMap solarMap) {
        JPanel panel = new JPanel();

        JButton writeToFile = new JButton("Write to file");
        writeToFile.addActionListener(actionEvent -> {

            try {
                File f = GetChosenFile.chosenSaveLocation();
                if(f != null) {
                    solarMap.writeToSolarLogFile(f);
                }
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        });

        JButton writeToInfluxDB = new JButton("Write to InfluxDB");
        writeToInfluxDB.addActionListener(actionEvent -> {
            try {
                solarMap.writeToInfluxDB(GetDatabase.influxDatabase());
            } catch (NullPointerException e) {
                Logger.warn(Logger.INFO_LEVEL_3 + "Database incorrect.");
            }
        });

        JButton writeToSQL = new JButton("Write to SQL");
        writeToSQL.addActionListener(actionEvent -> {
            SQLDatabase sqlDatabase = GetDatabase.SQLDatabase();
            try {
                assert sqlDatabase != null;
                solarMap.writeToMySQLDatabase(sqlDatabase.getHost(), sqlDatabase.getUser(), sqlDatabase.getPassword(), sqlDatabase.getDatabase(), sqlDatabase.getTable());
            } catch (NullPointerException | SQLException e) {
                Logger.warn(Logger.INFO_LEVEL_3 + "Unable to write to database");
            }
        });

        panel.add(writeToFile);
        panel.add(writeToInfluxDB);
        panel.add(writeToSQL);

        return panel;
    }
}
