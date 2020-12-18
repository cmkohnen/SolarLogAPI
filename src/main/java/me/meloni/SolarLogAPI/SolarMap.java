package me.meloni.SolarLogAPI;

import me.meloni.SolarLogAPI.DataConversion.Entries;
import me.meloni.SolarLogAPI.DataConversion.GetData;
import me.meloni.SolarLogAPI.DataConversion.GetGraphData;
import me.meloni.SolarLogAPI.DataConversion.GetStartOf;
import me.meloni.SolarLogAPI.DatabaseInteraction.InfluxDbInteraction;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetFromEML;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetFromTar;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.ReadFileObject;
import me.meloni.SolarLogAPI.FileInteraction.Tools.FileObject;
import me.meloni.SolarLogAPI.FileInteraction.WriteFiles.WriteFileObject;
import me.meloni.SolarLogAPI.Handling.Logger;
import org.influxdb.InfluxDB;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

/**
 * This Class provides a universal way to handle the data and implements useful functions to convert data. THIS SHOULD NOT BE USED TO STORE DATA PERMANENTLY. For that use please refer to {@link FileObject}.
 * @author ChaosMelone9
 * @since 2.0.0
 */
public class SolarMap implements Serializable {
    private String defaultDataBase = "solar";
    private Map<Date, List<Integer>> data = new HashMap<>();
    private Date createdOn = Calendar.getInstance().getTime();

    /**
     * Instantiates using the {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format
     * @author ChaosMelone9
     */
    public SolarMap(Map<Date, List<Integer>> Map) { data = Map; }

    /**
     * Instantiates using a {@link FileObject}
     * @author ChaosMelone9
     */
    public SolarMap(FileObject fileObject) {
        data = fileObject.getData();
        Date created = (Date) fileObject.getInformation("creation");
        if(created != null) {
            this.createdOn = created;
        }
    }

    /**
     * Instantiates using a SolarLog Data file
     * @author ChaosMelone9
     * @throws  IOException Unusable file
     * @throws ClassNotFoundException Unusable file
     */
    public SolarMap(File DataFile) throws IOException, ClassNotFoundException { addFromDataFile(DataFile);}

    /**
     * Instantiates blank
     * @author ChaosMelone9
     */
    public SolarMap() { }



    
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
            addFromMap(GetData.getMinuetDataMap(file));
        }
    }

    /**
     * Add imported data from a .dat-files
     * @author ChaosMelone9
     * @throws IOException Bad file
     * @throws ParseException Bad date
     */
    public void addImportFromFiles(List<File> files) throws IOException, ParseException {
        int i1 = files.size();
        int i2 = 0;
        for (File file : files) {
            i2++;
            Logger.log("Importing from file " + file.getName() + "  (" + i2 + " of " + i1 + ").");
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
        addFromMap(fileObject.getData());
    }

    /**
     * Add all data from a SolarLog file
     * @author ChaosMelone9
     * @throws IOException Bad file
     * @throws ClassNotFoundException Bad file
     */
    public void addFromDataFile(File file) throws IOException, ClassNotFoundException {
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
    public void addFromInfluxDB(InfluxDB influxDB) {
        InfluxDbInteraction influxDbInteraction = new InfluxDbInteraction(influxDB);
        influxDbInteraction.setDatabase(defaultDataBase);
        addFromMap(influxDbInteraction.read());
        influxDbInteraction.close();
    }

    public void addFromInfluxDB(InfluxDB influxDB, String database) {
        InfluxDbInteraction influxDbInteraction = new InfluxDbInteraction(influxDB);
        influxDbInteraction.setDatabase(database);
        addFromMap(influxDbInteraction.read());
        influxDbInteraction.close();
    }

    public void addFromEMLFile(File emlFile) throws Exception {
        addFromTar(Objects.requireNonNull(GetFromEML.getTarFromEML(emlFile)));
    }

    public void addFromEMLFiles(List<File> emlFiles) throws Exception {
        addFromTars(GetFromEML.getTarsFromEMLS(emlFiles));
    }




    /**
     * Write to a {@link File}
     * @author ChaosMelone9
     * @throws IOException Bad file
     */
    public void writeToDataFile(File file) throws IOException {
        WriteFileObject.write(file, this.getFileObject());
    }

    /**
     * Write to an {@link InfluxDB}
     * @author ChaosMelone9
     */
    public void writeToInfluxDBDataBase(String server, String username, String password, String database, int batchPointLimit) {
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
    public void writeToInfluxDBDataBase(InfluxDB influxDB, int batchPointLimit) {
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
     * Return a {@link FileObject} for storage use
     * @author ChaosMelone9
     */
    public FileObject getFileObject() {
        FileObject fileObject = new FileObject(this.getAsMap());
        fileObject.putInformation("created", createdOn);
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
     * Sets the default database name
     * @author ChaosMelone9
     */
    public void setDefaultDataBase(String defaultDataBase) {
        this.defaultDataBase = defaultDataBase;
    }

    /**
     * returns the time this {@link SolarMap} was created
     * @author ChaosMelone9
     */
    public Date getCreationTime() {
        return createdOn;
    }

    /**
     * clears the {@link SolarMap}
     * @author ChaosMelone9
     */
    public void clear() {
        data.clear();
    }
}