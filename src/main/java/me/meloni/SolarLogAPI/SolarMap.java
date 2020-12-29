package me.meloni.SolarLogAPI;

import me.meloni.SolarLogAPI.DataConversion.*;
import me.meloni.SolarLogAPI.DatabaseInteraction.InfluxDbInteraction;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetFromEML;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetFromTar;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.ReadFileObject;
import me.meloni.SolarLogAPI.FileInteraction.Tools.FileObject;
import me.meloni.SolarLogAPI.FileInteraction.WriteFiles.WriteFileObject;
import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.SolarLogInteraction.GetJsonFromSolarLog;
import org.influxdb.InfluxDB;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.*;

/**
 * This Class provides a universal way to handle the data and implements useful functions to convert data. THIS SHOULD NOT BE USED TO STORE DATA PERMANENTLY. For that use please refer to {@link FileObject}.
 * @author ChaosMelone9
 * @since 2.0.0
 */
public class SolarMap implements Serializable {
    private Map<Date, List<Integer>> data = new HashMap<>();
    private Date createdOn = Calendar.getInstance().getTime();
    private UUID id = UUID.randomUUID();

    /**
     * Instantiates using the {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format
     * @author ChaosMelone9
     */
    public SolarMap(Map<Date, List<Integer>> Map) { init();this.data = Map;}

    /**
     * Instantiates using a {@link FileObject}
     * @author ChaosMelone9
     */
    public SolarMap(FileObject fileObject) {
        init();
        this.data = fileObject.getData();
        Date created = (Date) fileObject.getInformation("creation");
        if(created != null) {
            this.createdOn = created;
        }
        UUID id = (UUID) fileObject.getInformation("id");
        if(id != null) {
            this.id = id;
        }
    }

    /**
     * Instantiates using a SolarLog Data file
     * @author ChaosMelone9
     * @throws  IOException Unusable file
     * @throws ClassNotFoundException Unusable file
     */
    public SolarMap(File dataFile) throws IOException, ClassNotFoundException { init();addFromDataFile(dataFile);}

    /**
     * Instantiates blank
     * @author ChaosMelone9
     */
    public SolarMap() {init(); }

    private void init() {
        Logger.log(Logger.INFO_LEVEL_1 + "Created new SolarMap with ID " + id.toString());
    }



    
    /**
     * Set all data from a {@link Map}
     * @author ChaosMelone9
     */
    public void setFromMap(Map<Date, List<Integer>> map) {
        data = map;
    }

    /**
     * Set on specific timestamp
     * @author ChaosMelone9
     */
    public void setOnDate(Date date, List<Integer> values) {
        data.put(date, values);
    }

    /**
     * Add all data from a {@link Map}
     * @author ChaosMelone9
     */
    public void addFromMap(Map<Date, List<Integer>> map) {
        map.forEach((date, integers) -> data.putIfAbsent(date, integers));
    }

    /**
     * Add all data from another {@link SolarMap}
     * @author ChaosMelone9
     */
    public void addFromSolarMap(SolarMap map) {
        Logger.log(Logger.INFO_LEVEL_2 + "Adding to " + id.toString() + " from SolarMap with ID " + map.getId().toString());
        addFromMap(map.getAsMap());
    }

    /**
     * Add imported data from a .dat-file
     * @author ChaosMelone9
     * @throws IOException Bad file
     * @throws ParseException Bad date
     */
    public void addImportFromFile(File file) throws IOException, ParseException {
        if(file.exists()) {
            Logger.log(Logger.INFO_LEVEL_2 + "Adding to " + id.toString() + " from file " + file.getName());
            addFromMap(GetData.getDataMap(file));
        }
    }

    /**
     * Add imported data from a .dat-files
     * @author ChaosMelone9
     * @throws IOException Bad file
     * @throws ParseException Bad date
     */
    public void addImportFromFiles(List<File> files) throws IOException, ParseException {
        Logger.log(Logger.INFO_LEVEL_2 + "Adding to " + id.toString() + " from multiple files");
        int i1 = files.size();
        int i2 = 0;
        for (File file : files) {
            i2++;
            Logger.log(Logger.INFO_LEVEL_3 + "Importing from file " + file.getName() + "  (" + i2 + " of " + i1 + ").");
            addImportFromFile(file);
        }
    }

    /**
     * Add imported data from a .tar.gz-archive
     * @author ChaosMelone9
     * @throws Exception ZipSlip attempt
     */
    public void addFromTar(File file) throws Exception {
        if(file.exists()) {
            Logger.log(Logger.INFO_LEVEL_2 + "Adding to " + id.toString() + " from Tar archive " + file.getName());
            addImportFromFiles(GetFromTar.getValidFilesFromTarArchive(file));
        }
    }

    /**
     * Add imported data from .tar.gz archives
     * @author ChaosMelone9
     * @throws Exception ZipSlip attempt
     */
    public void addFromTars(List<File> files) throws Exception {
        addImportFromFiles(GetFromTar.getValidFilesFromTarArchives(files));
    }

    /**
     * Add all data from a {@link FileObject}
     * @author ChaosMelone9
     */
    public void addFromFileObject(FileObject fileObject) {
        Logger.log(Logger.INFO_LEVEL_2 + "Adding to " + id.toString() + " from FileObject");
        addFromMap(fileObject.getData());
    }

    /**
     * Add all data from a SolarLog file
     * @author ChaosMelone9
     * @throws IOException Bad file
     * @throws ClassNotFoundException Bad file
     */
    public void addFromDataFile(File file) throws IOException, ClassNotFoundException {
        Logger.log(Logger.INFO_LEVEL_2 + "Adding to " + id.toString() + " from data file " + file.getName());
        addFromFileObject(ReadFileObject.fileObject(file));
    }

    /**
     * Add all data from SolarLog files
     * @author ChaosMelone9
     * @throws IOException Bad file
     * @throws ClassNotFoundException Bad file
     */
    public void addFromDataFiles(List<File> files) throws IOException, ClassNotFoundException {
        int i1 = files.size();
        int i2 = 0;
        for (File file : files) {
            i2++;
            Logger.log("Importing from file " + file.getName() + "  (" + i2 + " of " + i1 + ").");
            addFromDataFile(file);
        }
    }

    /**
     * Add from an {@link InfluxDB}
     * @author ChaosMelone9
     */
    public void addFromInfluxDB(String server, String username, String password, String database) {
        InfluxDbInteraction influxDbInteraction = new InfluxDbInteraction(server,username,password);
        influxDbInteraction.setDatabase(database);
        addFromMap(influxDbInteraction.read());
        influxDbInteraction.close();
    }

    /**
     * Add from an {@link InfluxDB}
     * @author ChaosMelone9
     */
    public void addFromInfluxDB(InfluxDB influxDB, String database) {
        InfluxDbInteraction influxDbInteraction = new InfluxDbInteraction(influxDB);
        influxDbInteraction.setDatabase(database);
        addFromMap(influxDbInteraction.read());
        influxDbInteraction.close();
    }

    /**
     * Add from an EML file
     * @author ChaosMelone9
     */
    public void addFromEMLFile(File emlFile) throws Exception {
        Logger.log(Logger.INFO_LEVEL_2 + "Adding to " + id.toString() + " from EML File " + emlFile.getName());
        addFromTar(Objects.requireNonNull(GetFromEML.getTarFromEML(emlFile)));
    }

    /**
     * Add from EML files
     * @author ChaosMelone9
     */
    public void addFromEMLFiles(List<File> emlFiles) throws Exception {
        addFromTars(GetFromEML.getTarsFromEMLS(emlFiles));
    }

    /**
     * Add from a SolarLog JSON interface
     * @author ChaosMelone9
     */
    public void addFromSolarLog(URL SolarLog) throws IOException, org.json.simple.parser.ParseException, ParseException {
        Logger.log(Logger.INFO_LEVEL_2 + "Adding to " + id.toString() + " from SolarLog at " + SolarLog.toString());
        addFromMap(GetValuesFromJson.getAsMap(GetJsonFromSolarLog.getFromSolarLogInterface(SolarLog)));
    }




    /**
     * Write to a {@link File}
     * @author ChaosMelone9
     * @throws IOException Bad file
     */
    public void writeToDataFile(File file) throws IOException {
        Logger.log(Logger.INFO_LEVEL_2 + "Writing " + id.toString() + " to data file " + file.getName());
        WriteFileObject.write(file, this.getFileObject());
    }

    /**
     * Write to an {@link InfluxDB}
     * @author ChaosMelone9
     */
    public void writeToInfluxDBDataBase(String server, String username, String password, String database, int batchPointLimit) throws NullPointerException{
        Logger.log(Logger.INFO_LEVEL_2 + "Writing " + id.toString() + " to InfluxDB at " + server);
        InfluxDbInteraction influxDbInteraction = new InfluxDbInteraction(server, username, password);
        influxDbInteraction.setBatchPointLimit(batchPointLimit);
        influxDbInteraction.setDatabase(database);
        influxDbInteraction.write(this);
        influxDbInteraction.close();
    }

    /**
     * Write to an {@link InfluxDB} database
     * @author ChaosMelone9
     * @param influxDB assumes a set database
     **/
    public void writeToInfluxDBDataBase(InfluxDB influxDB, int batchPointLimit) throws NullPointerException{
        Logger.log(Logger.INFO_LEVEL_2 + "Writing " + id.toString() + " to InfluxDB");
        InfluxDbInteraction influxDbInteraction = new InfluxDbInteraction(influxDB);
        influxDbInteraction.setBatchPointLimit(batchPointLimit);
        influxDbInteraction.write(this);
        influxDbInteraction.close();
    }




    /**
     * Get the {@link SolarMap} in {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format
     * @author ChaosMelone9
     */
    public Map<Date, List<Integer>> getAsMap() {
        return data;
    }

    /**
     * Get values from a specific timestamp
     * @author ChaosMelone9
     */
    public List<Integer> getValuesFromDate(Date date) {
        return data.get(date);
    }

    /**
     * Get values from a specific timestamp
     * @author ChaosMelone9
     */
    public Integer getValueFromDate(Date date, Integer value) {
        return getValuesFromDate(date).get(value);
    }

    /**
     * Get graph data for daily visualization
     * @author ChaosMelone9
     * @throws ParseException Bad date
     */
    public List<List<Double>> getDayGraphData(Date date) throws ParseException {
        return GetGraphData.dayView(date, data);
    }

    /**
     * Get graph data for monthly visualization
     * @author ChaosMelone9
     * @throws ParseException Bad date
     */
    public List<List<Double>> getMonthGraphData(YearMonth yearMonth) throws ParseException {
        return GetGraphData.monthView(yearMonth, data);
    }

    /**
     * Get graph data for yearly visualization
     * @author ChaosMelone9
     * @throws ParseException Bad date
     */
    public List<List<Double>> getYearGraphData(Year year) throws ParseException {
        return GetGraphData.yearView(year, data);
    }

    /**
     * Return a {@link FileObject} for storage use
     * @author ChaosMelone9
     */
    public FileObject getFileObject() {
        FileObject fileObject = new FileObject(this.getAsMap());
        fileObject.putInformation("created", createdOn);
        fileObject.putInformation("id", id);
        return fileObject;
    }




    /**
     * Get on specific timestamp
     * @author ChaosMelone9
     */
    public List<Integer> get(Date date) {
        return data.get(date);
    }

    /**
     * Get value on specific timestamp
     * @author ChaosMelone9
     */
    public int getValue(Date date, int value) {
        return get(date).get(value);
    }

    /**
     * whether or not a specific timestamp is included
     * @author ChaosMelone9
     */
    public boolean containsKey(Date date) {
        return data.containsKey(date);
    }

    /**
     * whether or not a specific timestamp is included
     * @author ChaosMelone9
     */
    public boolean includes(Date date) {
        return containsKey(date);
    }

    /**
     * whether or not a day is included
     * @author ChaosMelone9
     */
    public boolean includesDay(Date date) {
        return includes(GetStartOf.day(date));
    }

    /**
     * whether or not a day is included
     * @author ChaosMelone9
     */
    public boolean includesDay(LocalDate date) {
        return includes(GetStartOf.day(date));
    }

    /**
     * whether or not a month is included
     * @author ChaosMelone9
     */
    public boolean includesMonth(YearMonth yearMonth) {
        for (Date date : Entries.entriesPerMonth(yearMonth)) {
            if(includesDay(date)) {
                return true;
            }
        }
        return false;
    }

    /**
     * the number of all stored points
     * @author ChaosMelone9
     */
    public int size() {
        return data.size();
    }

    /**
     * returns the time this {@link SolarMap} was created
     * @author ChaosMelone9
     */
    public Date getCreationTime() {
        return createdOn;
    }

    public UUID getId() {
        return id;
    }

    /**
     * clears the {@link SolarMap}
     * @author ChaosMelone9
     */
    public void clear() {
        data.clear();
        Logger.log(Logger.INFO_LEVEL_1 + "Cleared " + id.toString());
    }
}