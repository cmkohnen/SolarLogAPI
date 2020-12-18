package me.meloni.SolarLogAPI.Interface.BasicUI;

import me.meloni.SolarLogAPI.DatabaseInteraction.GetDataBase;
import me.meloni.SolarLogAPI.FileInteraction.GetFile;
import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.io.IOException;

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

        JButton writeToFile = new JButton("Write to file");
        writeToFile.addActionListener(actionEvent -> {
            try {
                solarMap.writeToDataFile(GetFile.chosenSaveLocation());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        JButton writeToInfluxDB = new JButton("Write to InfluxDB");
        writeToInfluxDB.addActionListener(actionEvent -> solarMap.writeToInfluxDBDataBase(GetDataBase.influxDB(), 125000));

        panel.add(writeToFile);
        panel.add(writeToInfluxDB);

        return panel;
    }
}
