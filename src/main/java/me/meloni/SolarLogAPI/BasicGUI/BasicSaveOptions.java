package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.DatabaseInteraction.SQLDatabase;
import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.Handling.Translation;
import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class BasicSaveOptions {
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

        JButton writeToFile = new JButton(Translation.get("gui_write_file"));
        writeToFile.addActionListener(actionEvent -> {

            try {
                File f = GetChosenFile.chosenSaveLocation();
                if(f != null) {
                    solarMap.writeToDataFile(f);
                }
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        });

        JButton writeToInfluxDB = new JButton(Translation.get("gui_write_influx"));
        writeToInfluxDB.addActionListener(actionEvent -> {
            try {
                solarMap.writeToInfluxDBDataBase(GetDatabase.influxDatabase().getInfluxDB(), 125000);
            } catch (NullPointerException e) {
                Logger.warn(Logger.INFO_LEVEL_3 + Translation.get("influx_error"));
            }
        });

        JButton writeToSQL = new JButton(Translation.get("gui_write_sql"));
        writeToSQL.addActionListener(actionEvent -> {
            SQLDatabase sqlDatabase = GetDatabase.SQLDatabase();
            try {
                assert sqlDatabase != null;
                solarMap.writeToMySQLDatabase(sqlDatabase.getHost(), sqlDatabase.getUser(), sqlDatabase.getPassword(), sqlDatabase.getDatabase(), sqlDatabase.getTable());
            } catch (NullPointerException | SQLException e) {
                Logger.warn(Logger.INFO_LEVEL_3 + String.format(Translation.get("sql_error"), "null"));
            }
        });

        panel.add(writeToFile);
        panel.add(writeToInfluxDB);
        panel.add(writeToSQL);

        return panel;
    }
}
