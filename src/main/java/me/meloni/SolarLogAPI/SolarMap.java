package me.meloni.SolarLogAPI;

import me.meloni.SolarLogAPI.DataConversion.*;
import me.meloni.SolarLogAPI.DatabaseInteraction.InfluxDbInteraction;
import me.meloni.SolarLogAPI.FTPServerInteraction.GetFromFTPServer;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetFromEML;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetFromTar;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.ReadFileObject;
import me.meloni.SolarLogAPI.FileInteraction.Tools.FileObject;
import me.meloni.SolarLogAPI.FileInteraction.WriteFiles.WriteFileObject;
import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.Handling.Translation;
import me.meloni.SolarLogAPI.SolarLogInteraction.GetJsonFromSolarLog;
import org.influxdb.InfluxDB;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * This Class provides a universal way to handle the data and implements useful functions to convert data. THIS SHOULD NOT BE USED TO STORE DATA PERMANENTLY. For that use please refer to {@link FileObject}.
 * @author ChaosMelone9
 * @since 2.0.0
 */
public class SolarMap {
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
    public SolarMap(File dataFile) throws IOException, ClassNotFoundException { init();
        addFromSolarLogFile(dataFile);}

    /**
     * Instantiates blank
     * @author ChaosMelone9
     */
    public SolarMap() {init(); }

    private void init() {
        Logger.log(Logger.INFO_LEVEL_1 + String.format(Translation.get("solarmap_new"), id.toString()));
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
        Logger.log(Logger.INFO_LEVEL_2 + String.format(Translation.get("solarmap_add_solarmap"), id.toString(), map.getId().toString()));
        addFromMap(map.getAsMap());
    }

    /**
     * Same as addFromSolarMap() for consistency with {@link Map}
     * @author ChaosMelone9
     */
    public void merge(SolarMap map) {
        addFromSolarMap(map);
    }

    /**
     * Add imported data from a .dat-file
     * @author ChaosMelone9
     * @throws IOException Bad file
     * @throws ParseException Bad date
     */
    public void addFromDatFile(File file) throws IOException, ParseException {
        if(file.exists()) {
            Logger.log(Logger.INFO_LEVEL_2 + String.format(Translation.get("solarmap_add_file"), id.toString(), file.getName()));
            addFromMap(GetData.getAsMapFromDatFile(file));
        }
    }

    /**
     * Add imported data from a .dat-files
     * @author ChaosMelone9
     * @throws IOException Bad file
     * @throws ParseException Bad date
     */
    public void addFromDatFiles(List<File> files) throws IOException, ParseException {
        Logger.log(Logger.INFO_LEVEL_2 + String.format(Translation.get("solarmap_add_files"), id.toString()));
        int i1 = files.size();
        int i2 = 0;
        for (File file : files) {
            i2++;
            Logger.log(Logger.INFO_LEVEL_3 + String.format(Translation.get("solarmap_importing_from_file"), file.getName(), i2, i1));
            addFromDatFile(file);
        }
    }

    /**
     * Add imported data from a .tar.gz-archive
     * @author ChaosMelone9
     * @throws Exception ZipSlip attempt
     */
    public void addFromTarArchive(File file) throws Exception {
        if(file.exists()) {
            Logger.log(Logger.INFO_LEVEL_2 + String.format(Translation.get("solarmap_add_tar"), id.toString(), file.getName()));
            addFromDatFiles(GetFromTar.getValidDatFilesFromTarArchive(file));
        }
    }

    /**
     * Add imported data from .tar.gz archives
     * @author ChaosMelone9
     * @throws Exception ZipSlip attempt
     */
    public void addFromTarArchives(List<File> files) throws Exception {
        addFromDatFiles(GetFromTar.getValidDatFilesFromTarArchives(files));
    }

    /**
     * Add all data from a {@link FileObject}
     * @author ChaosMelone9
     */
    public void addFromFileObject(FileObject fileObject) {
        Logger.log(Logger.INFO_LEVEL_2 + String.format(Translation.get("solarmap_add_fileobject"), id.toString()));
        addFromMap(fileObject.getData());
    }

    /**
     * Add all data from a SolarLog file
     * @author ChaosMelone9
     * @throws IOException Bad file
     * @throws ClassNotFoundException Bad file
     */
    public void addFromSolarLogFile(File file) throws IOException, ClassNotFoundException {
        Logger.log(Logger.INFO_LEVEL_2 + String.format(Translation.get("solarmap_add_datafile"), id.toString(), file.getName()));
        addFromFileObject(ReadFileObject.getObjectFromFile(file));
    }

    /**
     * Add all data from SolarLog files
     * @author ChaosMelone9
     * @throws IOException Bad file
     * @throws ClassNotFoundException Bad file
     */
    public void addFromSolarLogFiles(List<File> files) throws IOException, ClassNotFoundException {
        int i1 = files.size();
        int i2 = 0;
        for (File file : files) {
            i2++;
            Logger.log(Logger.INFO_LEVEL_2 + String.format(Translation.get("solarmap_importing_from_file"), file.getName(), i2, i1));
            addFromSolarLogFile(file);
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
        Logger.log(Logger.INFO_LEVEL_2 + String.format(Translation.get("solarmap_add_emlfile"), id.toString(), emlFile.getName()));
        addFromTarArchive(Objects.requireNonNull(GetFromEML.getTarFromEML(emlFile)));
    }

    /**
     * Add from EML files
     * @author ChaosMelone9
     */
    public void addFromEMLFiles(List<File> emlFiles) throws Exception {
        addFromTarArchives(GetFromEML.getTarsFromEMLS(emlFiles));
    }

    /**
     * Add from a SolarLog JSON interface
     * @author ChaosMelone9
     */
    public void addFromSolarLog(URL solarLog) throws IOException, org.json.simple.parser.ParseException, ParseException {
        Logger.log(Logger.INFO_LEVEL_2 + String.format(Translation.get("solarmap_add_solarlog"), id.toString(), solarLog.toString()));
        addFromMap(GetValuesFromJson.getAsMap(GetJsonFromSolarLog.getFromSolarLogInterface(solarLog)));
    }

    /**
     * Add from a JS file found on the FTP server
     * @author ChaosMelone9
     */
    public void addFromJSFile(File jsFile) throws IOException, ParseException {
        addFromMap(GetData.getAsMapFromJSFile(jsFile));
    }

    /**
     * Add from a JS files found on the FTP server
     * @author ChaosMelone9
     */
    public void addFromJSFiles(List<File> jsFiles) throws IOException, ParseException {
        for (File jsFile : jsFiles) {
            addFromJSFile(jsFile);
        }
    }

    public void addFromFTPServer(String host, String user, String password) throws IOException, ParseException {
        Logger.log(Logger.INFO_LEVEL_1 + String.format(Translation.get("solarmap_add_ftp"), id, user, host));
        addFromJSFiles(GetFromFTPServer.getJSFilesFromFTPServer(host, user, password));
    }




    /**
     * Write to a {@link File}
     * @author ChaosMelone9
     * @throws IOException Bad file
     */
    public void writeToDataFile(File file) throws IOException {
        Logger.log(Logger.INFO_LEVEL_2 + String.format(Translation.get("solarmap_write_datafile"), id.toString(), file.getName()));
        WriteFileObject.write(file, getAsFileObject());
    }

    /**
     * Write to an {@link InfluxDB}
     * @author ChaosMelone9
     */
    public void writeToInfluxDBDataBase(String server, String username, String password, String database, int batchPointLimit) throws NullPointerException{
        Logger.log(Logger.INFO_LEVEL_2 + String.format(Translation.get("solarmap_write_influxdb"), id.toString()) + server);
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
        Logger.log(Logger.INFO_LEVEL_2 + String.format(Translation.get("solarmap_write_influxdb"), id.toString()));
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
    public List<Integer> getValuesOnDate(Date date) {
        return data.get(date);
    }

    /**
     * Get values from a specific timestamp
     * @author ChaosMelone9
     */
    public Integer getValueOnDate(Date date, Integer value) {
        return getValuesOnDate(date).get(value);
    }

    /**
     * Return a {@link FileObject} for storage use
     * @author ChaosMelone9
     */
    public FileObject getAsFileObject() {
        FileObject fileObject = new FileObject(this.getAsMap());
        fileObject.putInformation("created", createdOn);
        fileObject.putInformation("id", id);
        return fileObject;
    }

    /**
     * Return a {@link JSONObject} for external use
     * @author ChaosMelone9
     */
    public JSONObject getAsJSON() {
        return ConvertJson.convertMapToJson(getAsMap());
    }

    /**
     * Return a {@link JSONObject} as String for compatibility
     * @author ChaosMelone9
     */
    public String getAsJSONString() {
        return getAsJSON().toJSONString();
    }

    /**
     * Get Only Data from a specific timeframe
     * @author ChaosMelone9
     */
    public SolarMap getInTimeframe(Date firstDate, Date lastDate) {
        Map<Date, List<Integer>> map = new HashMap<>();
        data.forEach((date, integers) -> {
            if (date.after(firstDate) & date.before(lastDate)) {
                map.put(date,integers);
            }
        });
        return new SolarMap(map);
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
        for (Date date : Entries.getEntriesPerMonth(yearMonth)) {
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
     * weather or not the map is empty
     * @author ChaosMelone9
     */
    public boolean isEmpty() {
        return data.isEmpty();
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
        Logger.log(Logger.INFO_LEVEL_1 + String.format(Translation.get("solarmap_cleared"), id.toString()));
    }

    /**
     * Get values as collection
     * @author ChaosMelone9
     */
    public Collection<List<Integer>> values() {
        return data.values();
    }

    /**
     * Overrides toString() to something human readable
     * @author ChaosMelone9
     */
    @Override
    public String toString() {
        Map<String, String> map = new HashMap<>();
        map.put("ID", id.toString());
        map.put("CreationTime", createdOn.toString());
        return map.toString();
    }

    /**
     * Same as getAsMap().forEach()
     * @author ChaosMelone9
     */
    public void forEach(BiConsumer<? super Date, ? super List<? super Integer>> consumer) {
        data.forEach(consumer);
    }

    /**
     * Compares the contents of two SolarMaps
     * @author ChaosMelone9
     */
    public boolean equals(SolarMap solarMap) {
        return data.equals(solarMap.getAsMap());
    }
}