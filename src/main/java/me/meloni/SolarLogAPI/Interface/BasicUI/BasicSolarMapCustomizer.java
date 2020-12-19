package me.meloni.SolarLogAPI.Interface.BasicUI;

import me.meloni.SolarLogAPI.Interface.GetDataBase;
import me.meloni.SolarLogAPI.Interface.FileChoosing.GetChosenFile;
import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.SolarMap;
import org.influxdb.InfluxDB;

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
    private final List<File> importFiles = new ArrayList<>();
    private final List<File> importTars = new ArrayList<>();
    private final List<File> dataFiles = new ArrayList<>();
    private final List<File> emlFiles = new ArrayList<>();
    private final List<InfluxDB> databases = new ArrayList<>();

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

        JButton addFile = new JButton("Add File");
        addFile.addActionListener(e -> {
            File f = GetChosenFile.validChosenDataFile();
            if(!(f == null) && f.exists()) {
                importFiles.add(f);
                repaintList();
            }
        });

        JButton addDirectory = new JButton("Add from Folder");
        addDirectory.addActionListener(e -> {
            try {
                List<File> files = GetChosenFile.chosenValidFilesInDirectory();
                if(!(files == null)) {
                    importFiles.addAll(files);
                    repaintList();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        JButton addTar = new JButton("Add from tar");
        addTar.addActionListener(e -> {
            File f = GetChosenFile.chosenTarArchive();
            if(!(f == null) && f.exists()) {
                importTars.add(f);
                repaintList();
            }
        });

        JButton addTars = new JButton("Add from tars");
        addTars.addActionListener(e -> {
            try {
                List<File> files = GetChosenFile.chosenTarsInDirectory();
                if(!(files == null)) {
                    importTars.addAll(files);
                    repaintList();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        JButton addDataFile = new JButton("Add from Data File");
        addDataFile.addActionListener(e -> {
            File f = GetChosenFile.chosenReadLocation();
            if(!(f == null) && f.exists()) {
                dataFiles.add(f);
                repaintList();
            }
        });

        JButton addEML = new JButton("Add from EML");
        addEML.addActionListener(e -> {
            File f = GetChosenFile.chosenEMLFile();
            if(!(f == null) && f.exists()) {
                emlFiles.add(f);
                repaintList();
            }
        });

        JButton addEMLs = new JButton("Add from EMLs");
        addEMLs.addActionListener(e -> {
            try {
                List<File> files = GetChosenFile.chosenEMLsInDirectory();
                if(!(files == null)) {
                    emlFiles.addAll(files);
                    repaintList();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        JButton addDatabase = new JButton("Add from Database");
        addDatabase.addActionListener(e -> {
            databases.add(GetDataBase.influxDB());
            repaintList();
        });

        buttons.add(addFile);
        buttons.add(addDirectory);
        buttons.add(addTar);
        buttons.add(addTars);
        buttons.add(addDataFile);
        buttons.add(addEML);
        buttons.add(addEMLs);
        buttons.add(addDatabase);

        filePanel.setLayout(new BorderLayout());

        files.setLayout(new BoxLayout(files, BoxLayout.Y_AXIS));

        filePanel.add(files, BorderLayout.CENTER);

        filePanel.add(buttons, BorderLayout.PAGE_START);

        JButton retrain = new JButton("Return");
        filePanel.add(retrain, BorderLayout.PAGE_END);
        retrain.addActionListener(e -> {
            try {
                if(importFiles.size() > 0) {
                    Logger.log("Importing from " + importFiles);
                    map.addImportFromFiles(importFiles);
                }
                if(importTars.size() > 0) {
                    Logger.log("Importing from " + importTars);
                    map.addFromTars(importTars);
                }
                if(dataFiles.size() > 0) {
                    Logger.log("Importing from " + dataFiles);
                    map.addFromDataFiles(dataFiles);
                }
                if(emlFiles.size() > 0) {
                    Logger.log("Importing from " + emlFiles);
                    map.addFromEMLFiles(emlFiles);
                }
                if(databases.size() > 0) {
                    Logger.log("Importing from " + databases);
                    for (InfluxDB database : databases) {
                        map.addFromInfluxDB(database);
                    }
                }
                done = true;
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        });


    }

    private void repaintList() {
        files.removeAll();
        files.add(new JLabel("Importing from:"));
        if(importFiles.size() > 0) {
            files.add(new JLabel("Data Files:"));
            for (File importFile : importFiles) {
                files.add(new JLabel(importFile.getName()));
            }
        }
        if(importTars.size() > 0) {
            files.add(new JLabel("Tar Archives:"));
            for (File importTar : importTars) {
                files.add(new JLabel(importTar.getName()));
            }
        }
        if(dataFiles.size() > 0) {
            files.add(new JLabel("Data Files:"));
            for (File dataFile : dataFiles) {
                files.add(new JLabel(dataFile.getName()));
            }
        }
        if(emlFiles.size() > 0) {
            files.add(new JLabel("EML Files:"));
            for (File emlFile : emlFiles) {
                files.add(new JLabel(emlFile.getName()));
            }
        }
        if(databases.size() > 0) {
            files.add(new JLabel("Databases:"));
            for (InfluxDB database : databases) {
                files.add(new JLabel(database.version()));
            }
        }
        files.setVisible(false);
        files.setVisible(true);
    }
}
