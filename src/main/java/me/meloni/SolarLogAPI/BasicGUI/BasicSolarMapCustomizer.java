package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.DatabaseInteraction.InfluxDatabase;
import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class includes a function to call a GUI capable of customizing a SolarMap to the users likes.
 * @author ChaosMelone9
 * @since 1.0.0
 */
public class BasicSolarMapCustomizer {

    private static final JPanel filePanel = new JPanel();
    private final JPanel files = new JPanel();
    private final SolarMap map = new SolarMap();
    private boolean done = false;
    private final List<File> datFiles = new ArrayList<>();
    private final List<File> tarArchives = new ArrayList<>();
    private final List<File> dataFiles = new ArrayList<>();
    private final List<File> emlFiles = new ArrayList<>();
    private final List<InfluxDatabase> influxDatabases = new ArrayList<>();
    private final List<File> jsFiles = new ArrayList<>();

    public BasicSolarMapCustomizer() {
        initPanel();
    }

    public static SolarMap solarMap() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(300,500);
        f.setTitle("Customization");
        BasicSolarMapCustomizer basicSolarMapCustomizer = new BasicSolarMapCustomizer();
        f.add(filePanel);
        f.setVisible(true);
        while (!basicSolarMapCustomizer.done) {
            System.getSecurityManager();
        }
        f.setVisible(false);
        f.removeAll();
        return basicSolarMapCustomizer.map;
    }

    private void initPanel() {
        filePanel.setLayout(new BorderLayout());

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
            try {
                List<File> files = GetChosenFile.chosenDatFilesInDirectory();
                if(!(files == null)) {
                    datFiles.addAll(files);
                    repaintList();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
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

        JButton addDataFile = new JButton("Add data file");
        addDataFile.addActionListener(e -> {
            File f = GetChosenFile.chosenSolarLogFile();
            if(!(f == null) && f.exists()) {
                dataFiles.add(f);
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
        buttons.add(addDataFile);
        buttons.add(addEMLFile);
        buttons.add(addEMLFiles);
        buttons.add(addInfluxDB);
        buttons.add(addJSFile);
        buttons.add(addJSFiles);

        filePanel.setLayout(new BorderLayout());

        files.setLayout(new BoxLayout(files, BoxLayout.Y_AXIS));

        filePanel.add(files, BorderLayout.CENTER);

        filePanel.add(buttons, BorderLayout.PAGE_START);

        JButton retrain = new JButton("Return");
        filePanel.add(retrain, BorderLayout.PAGE_END);
        retrain.addActionListener(e -> {
            try {
                if(datFiles.size() > 0) {
                    map.addFromDatFiles(datFiles);
                }
                if(tarArchives.size() > 0) {
                    map.addFromTarArchives(tarArchives);
                }
                if(dataFiles.size() > 0) {
                    map.addFromSolarLogFiles(dataFiles);
                }
                if(emlFiles.size() > 0) {
                    map.addFromEMLFiles(emlFiles);
                }
                if(influxDatabases.size() > 0) {
                    for (InfluxDatabase influxDatabase : influxDatabases) {
                        map.addFromInfluxDB(influxDatabase.getInfluxDB(), influxDatabase.getDatabase());
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
        if(dataFiles.size() > 0) {
            files.add(new JLabel("data files: "));
            for (File dataFile : dataFiles) {
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
                files.add(new JLabel(influxDatabase.getInfluxDB().version()));
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
