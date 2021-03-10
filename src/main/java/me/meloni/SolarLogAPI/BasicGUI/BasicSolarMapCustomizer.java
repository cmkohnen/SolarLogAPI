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

import me.meloni.SolarLogAPI.DatabaseInteraction.InfluxDatabase;
import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class includes a function to call a GUI capable of customizing a SolarMap to the users likes.
 * @author Christoph Kohnen
 * @since 1.0.0
 */
public class BasicSolarMapCustomizer {

    /**
     * Main panel to which every component gets added
     */
    private static final JPanel mainPanel = new JPanel();
    /**
     * Panel in which the upcoming result will be displayed
     */
    private final JPanel files = new JPanel();
    /**
     * The main {@link SolarMap} to which all data is being added
     */
    private final SolarMap map = new SolarMap();
    /**
     * Temporal boolean to wait until the user returns
     */
    private boolean done = false;
    /**
     * A list of all .dat files of which all data should be extracted and added into the main {@link SolarMap}
     */
    private final List<File> datFiles = new ArrayList<>();
    /**
     * A list of all tar archives of which all data should be extracted and added into the main {@link SolarMap}
     */
    private final List<File> tarArchives = new ArrayList<>();
    /**
     * A list of all SolarLog files of which all data should be extracted and added into the main {@link SolarMap}
     */
    private final List<File> solarLogFiles = new ArrayList<>();
    /**
     * A list of all .eml files of which all data should be extracted and added into the main {@link SolarMap}
     */
    private final List<File> emlFiles = new ArrayList<>();
    /**
     * A list of all Influx databases of which all data should be extracted and added into the main {@link SolarMap}
     */
    private final List<InfluxDatabase> influxDatabases = new ArrayList<>();
    /**
     * A list of all .js files of which all data should be extracted and added into the main {@link SolarMap}
     */
    private final List<File> jsFiles = new ArrayList<>();

    /**
     * main function to call a GUI capable of customizing a SolarMap to the users likes.
     * @return A customized {@link SolarMap}
     */
    public static SolarMap solarMap() {
        JFrame f = new JFrame();
        f.setSize(300,500);
        f.setTitle("Customization");
        BasicSolarMapCustomizer basicSolarMapCustomizer = new BasicSolarMapCustomizer();
        f.add(mainPanel);
        f.setVisible(true);
        while (!basicSolarMapCustomizer.done) {
            System.getSecurityManager();
        }
        f.setVisible(false);
        f.removeAll();
        return basicSolarMapCustomizer.map;
    }

    /**
     * Constructor to setup all components
     */
    public BasicSolarMapCustomizer() {
        mainPanel.setLayout(new BorderLayout());

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

        JButton addDatFile = new JButton("Add .dat-file");
        addDatFile.addActionListener(e -> {
            File f = GetChosenFile.chosenDatFile();
            if(!(f == null) && f.exists()) {
                datFiles.add(f);
                repaintList();
            }
        });

        JButton addDatFiles = new JButton("Add .dat-files");
        addDatFiles.addActionListener(e -> {
            List<File> files = GetChosenFile.chosenDatFilesInDirectory();
            if(!(files == null)) {
                datFiles.addAll(files);
                repaintList();
            }
        });

        JButton addTarArchive = new JButton("Add tar archive");
        addTarArchive.addActionListener(e -> {
            File f = GetChosenFile.chosenTarArchive();
            if(!(f == null) && f.exists()) {
                tarArchives.add(f);
                repaintList();
            }
        });

        JButton addTarArchives = new JButton("Add tar archives");
        addTarArchives.addActionListener(e -> {
            try {
                List<File> files = GetChosenFile.chosenTarArchivesInDirectory();
                if(!(files == null)) {
                    tarArchives.addAll(files);
                    repaintList();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        JButton addSolarLogFile = new JButton("Add data file");
        addSolarLogFile.addActionListener(e -> {
            File f = GetChosenFile.chosenSolarLogFile();
            if(!(f == null) && f.exists()) {
                solarLogFiles.add(f);
                repaintList();
            }
        });

        JButton addEMLFile = new JButton("Add .eml-file");
        addEMLFile.addActionListener(e -> {
            File f = GetChosenFile.chosenEMLFile();
            if(!(f == null) && f.exists()) {
                emlFiles.add(f);
                repaintList();
            }
        });

        JButton addEMLFiles = new JButton("Add .eml-files");
        addEMLFiles.addActionListener(e -> {
            try {
                List<File> files = GetChosenFile.chosenEMLFilesInDirectory();
                if(!(files == null)) {
                    emlFiles.addAll(files);
                    repaintList();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        JButton addInfluxDB = new JButton("Add InfluxDB");
        addInfluxDB.addActionListener(e -> {
            try {
                influxDatabases.add(GetDatabase.influxDatabase());
                repaintList();
            } catch (NullPointerException ignored) {

            }
        });

        JButton addJSFile = new JButton("Add .js-file");
        addJSFile.addActionListener(e -> {
            File f = GetChosenFile.chosenJSFile();
            if(!(f == null) && f.exists()) {
                jsFiles.add(f);
                repaintList();
            }
        });

        JButton addJSFiles = new JButton("Add .js-files");
        addJSFiles.addActionListener(e -> {
            try {
                List<File> files = GetChosenFile.chosenJSFilesInDirectory();
                if(!(files == null)) {
                    jsFiles.addAll(files);
                    repaintList();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttons.add(addDatFile);
        buttons.add(addDatFiles);
        buttons.add(addTarArchive);
        buttons.add(addTarArchives);
        buttons.add(addSolarLogFile);
        buttons.add(addEMLFile);
        buttons.add(addEMLFiles);
        buttons.add(addInfluxDB);
        buttons.add(addJSFile);
        buttons.add(addJSFiles);

        mainPanel.setLayout(new BorderLayout());

        files.setLayout(new BoxLayout(files, BoxLayout.Y_AXIS));

        mainPanel.add(files, BorderLayout.CENTER);

        mainPanel.add(buttons, BorderLayout.PAGE_START);

        JButton retrain = new JButton("Return");
        mainPanel.add(retrain, BorderLayout.PAGE_END);
        retrain.addActionListener(e -> {
            try {
                if(datFiles.size() > 0) {
                    map.addFromDatFiles(datFiles);
                }
                if(tarArchives.size() > 0) {
                    map.addFromTarArchives(tarArchives);
                }
                if(solarLogFiles.size() > 0) {
                    map.addFromSolarLogFiles(solarLogFiles);
                }
                if(emlFiles.size() > 0) {
                    map.addFromEMLFiles(emlFiles);
                }
                if(influxDatabases.size() > 0) {
                    for (InfluxDatabase influxDatabase : influxDatabases) {
                        //map.addFromInfluxDB(influxDatabase.getInfluxDB(), influxDatabase.getDatabase());
                    }
                }
                if(jsFiles.size() > 0) {
                    map.addFromJSFiles(jsFiles);
                }
                done = true;
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        });
    }

    /**
     * Repaint the list displaying from where data should be added
     */
    private void repaintList() {
        files.removeAll();
        files.add(new JLabel("Importing from: "));
        if(datFiles.size() > 0) {
            files.add(new JLabel(".dat-files: "));
            for (File importFile : datFiles) {
                files.add(new JLabel(importFile.getName()));
            }
        }
        if(tarArchives.size() > 0) {
            files.add(new JLabel("tar archives: "));
            for (File importTar : tarArchives) {
                files.add(new JLabel(importTar.getName()));
            }
        }
        if(solarLogFiles.size() > 0) {
            files.add(new JLabel("SolarLog files: "));
            for (File dataFile : solarLogFiles) {
                files.add(new JLabel(dataFile.getName()));
            }
        }
        if(emlFiles.size() > 0) {
            files.add(new JLabel(".eml-files: "));
            for (File emlFile : emlFiles) {
                files.add(new JLabel(emlFile.getName()));
            }
        }
        if(influxDatabases.size() > 0) {
            files.add(new JLabel("InfluxDBs: "));
            for (InfluxDatabase influxDatabase : influxDatabases) {
                files.add(new JLabel(influxDatabase.bucket));
            }
        }
        if(jsFiles.size() > 0) {
            files.add(new JLabel(".js-files: "));
            for (File jsFile : jsFiles) {
                files.add(new JLabel(jsFile.getName()));
            }
        }
        files.setVisible(false);
        files.setVisible(true);
    }
}
