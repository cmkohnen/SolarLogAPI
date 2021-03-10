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
package me.meloni.SolarLogAPI;

import me.meloni.SolarLogAPI.DataConversion.*;
import me.meloni.SolarLogAPI.DatabaseInteraction.InfluxDbInteraction;
import me.meloni.SolarLogAPI.DatabaseInteraction.MySQLInteraction;
import me.meloni.SolarLogAPI.DatabaseInteraction.SQLQuery;
import me.meloni.SolarLogAPI.FTPServerInteraction.GetFromFTPServer;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetFromEML;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetFromTar;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.ReadFileObject;
import me.meloni.SolarLogAPI.FileInteraction.Tools.FileObject;
import me.meloni.SolarLogAPI.FileInteraction.WriteFiles.WriteFileObject;
import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.SolarLogInteraction.GetJsonFromSolarLog;
import org.influxdb.InfluxDB;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * This is intended to be the main interface for this api. It houses functions to do all features of this API with ease.
 * It contains enough functions to manipulate it too your likes.
 * Because it contains a {@link Map} storing the data, some functions are very similar and / or directly mirrored to it.
 * Please do not use this to store data permanently in any way. For that there is an {@link FileObject} which is intended for exactly that.
 * There is also functionality to export directly as {@link JSONObject}
 * @author Christoph Kohnen
 * @since 2.0.0
 */
@SuppressWarnings("unused")
public class SolarMap {
    /**
     * The main object to store data. This is according to the {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format
     */
    private Map<Date, List<Integer>> data = new HashMap<>();
    /**
     * This stores the time the solarMap was created on.
     */
    private Date createdOn = Calendar.getInstance().getTime();
    /**
     * This is an unique UUID assigned upon creation. This is to easily tell different maps apart and keep track of them
     */
    private UUID id = UUID.randomUUID();

    /**
     * Instantiates using the {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format
     * @author Christoph Kohnen
     * @param map The data in {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format
     */
    public SolarMap(Map<Date, List<Integer>> map) { init();this.data = map;}

    /**
     * Instantiates using a {@link FileObject}
     * @author Christoph Kohnen
     * @param fileObject The {@link FileObject} from which data should be used
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
     * Instantiates using a SolarLog data file
     * @author Christoph Kohnen
     * @param solarLogFile The SolarLog data file of which the data should be used
     * @throws IOException If provided a bad file
     * @throws ClassNotFoundException If provided a bad file
     */
    public SolarMap(File solarLogFile) throws IOException, ClassNotFoundException {
        init();
        if(solarLogFile != null) {
            addFromSolarLogFile(solarLogFile);
        } else {
            throw new NoSuchFileException("This file does not exist.");
        }
    }

    /**
     * Instantiates an empty {@link SolarMap}
     * @author Christoph Kohnen
     */
    public SolarMap() {init(); }

    /**
     * This function is called upon instantiation
     * @author Christoph Kohnen
     */
    private void init() {
        Logger.log(Logger.INFO_LEVEL_1 + String.format("Created new SolarMap with ID %s", id.toString()));
    }



    
    /**
     * Set all data in this {@link SolarMap} from a {@link Map} using the {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format
     * @author Christoph Kohnen
     * @param map The map from which this {@link SolarMap} should be set
     */
    public void setFromMap(Map<Date, List<Integer>> map) {
        data = map;
    }

    /**
     * Set values on a specific timestamp
     * @author Christoph Kohnen
     * @param timestamp The timestamp on which
     * @param values The values for that timestamp
     */
    public void setOnDate(Date timestamp, List<Integer> values) {
        data.put(timestamp, values);
    }

    /**
     * Add all data from a {@link Map} using the {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format
     * @author Cristoph Kohnen
     * @param map The map from which all data should be added
     */
    public void addFromMap(Map<Date, List<Integer>> map) {
        map.forEach((date, integers) -> data.putIfAbsent(date, integers));
    }

    /**
     * Add all data from another {@link SolarMap}
     * @author Cristoph Kohnen
     * @param solarMap The {@link SolarMap} from which all data should be added
     */
    public void addFromSolarMap(SolarMap solarMap) {
        Logger.log(Logger.INFO_LEVEL_2 + String.format("Adding to %s from SolarMap with ID %s", id.toString(), solarMap.getId().toString()));
        addFromMap(solarMap.getAsMap());
    }

    /**
     * Same as addFromSolarMap() for consistency with {@link Map}
     * @author Cristoph Kohnen
     * @param solarMap The {@link SolarMap} which should be merged into this
     */
    public void merge(SolarMap solarMap) {
        addFromSolarMap(solarMap);
    }

    /**
     * Add imported data from a .dat file
     * @author Cristoph Kohnen
     * @param datFile The .dat file from which all data should be extracted and added
     * @throws IOException If provided a bad file
     */
    public void addFromDatFile(File datFile) throws IOException {
        if(datFile.exists()) {
            Logger.log(Logger.INFO_LEVEL_2 + String.format("Adding to %s from .dat file %s", id.toString(), datFile.getName()));
            addFromMap(GetFromDat.getAsMapFromDatFile(datFile));
        }
    }

    /**
     * Add imported data from .dat files
     * @author Cristoph Kohnen
     * @param files A list of all .dat files from which all data should be extracted and added
     */
    public void addFromDatFiles(List<File> files) {
        Logger.log(Logger.INFO_LEVEL_2 + String.format("Adding to %s from multiple files", id.toString()));
        int i1 = files.size();
        int i2 = 0;
        for (File file : files) {
            i2++;
            Logger.log(Logger.INFO_LEVEL_3 + String.format("Importing from file %s (%s of %s)", file.getName(), i2, i1));
            try {
                addFromDatFile(file);
            } catch (IOException e) {
                Logger.warn(String.format("Unable to import data from file %s", file.getName()));
            }
        }
    }

    /**
     * Add imported data from a .tar.gz-archive
     * @author Cristoph Kohnen
     * @param tarArchive The GZipped tarball from which all data should be extracted and added
     * @throws Exception If provided a bad file
     */
    public void addFromTarArchive(File tarArchive) throws Exception {
        if(tarArchive.exists()) {
            Logger.log(Logger.INFO_LEVEL_2 + String.format("Adding to %s from Tar archive %s", id.toString(), tarArchive.getName()));
            addFromDatFiles(GetFromTar.getValidDatFilesFromTarArchive(tarArchive));
        }
    }

    /**
     * Add imported data from .tar.gz archives
     * @author Cristoph Kohnen
     * @param tarArchives A list of GZipped tarballs from which all data should be extracted and added
     * @throws Exception If provided a bad file
     */
    public void addFromTarArchives(List<File> tarArchives) throws Exception {
        addFromDatFiles(GetFromTar.getValidDatFilesFromTarArchives(tarArchives));
    }

    /**
     * Add all data from a {@link FileObject}
     * @author Cristoph Kohnen
     * @param fileObject The {@link FileObject} from which all data should be added
     */
    public void addFromFileObject(FileObject fileObject) {
        Logger.log(Logger.INFO_LEVEL_2 + String.format("Adding to %s from FileObject", id.toString()));
        addFromMap(fileObject.getData());
    }

    /**
     * Add all data from a SolarLog file
     * @author Cristoph Kohnen
     * @param solarLogFile The SolarLog file from which all data should be added
     * @throws IOException If provided a bad file
     */
    public void addFromSolarLogFile(File solarLogFile) throws IOException {
        Logger.log(Logger.INFO_LEVEL_2 + String.format("Adding to %s from SolarLog file %s", id.toString(), solarLogFile.getName()));
        addFromFileObject(ReadFileObject.getFileObjectFromSolarLogFile(solarLogFile));
    }

    /**
     * Add all data from SolarLog files
     * @author Cristoph Kohnen
     * @param solarLogFiles A list of files from which all data should be added
     */
    public void addFromSolarLogFiles(List<File> solarLogFiles) {
        int i1 = solarLogFiles.size();
        int i2 = 0;
        for (File file : solarLogFiles) {
            i2++;
            Logger.log(Logger.INFO_LEVEL_2 + String.format("Adding from SolarLog file %s (%s of %s)", file.getName(), i2, i1));
            try {
                addFromSolarLogFile(file);
            } catch (IOException e) {
                Logger.warn(e.getMessage());
            }
        }
    }

    /**
     * Add from an {@link InfluxDB} using host, credentials and database
     * @author Cristoph Kohnen
     * @param server The host of the {@link InfluxDB}
     * @param username Your username
     * @param password Your password
     * @param database The desired database
     */
    public void addFromInfluxDB(String server, String username, String password, String database) {
        InfluxDbInteraction influxDbInteraction = new InfluxDbInteraction(server,username,password);
        influxDbInteraction.setDatabase(database);
        addFromMap(influxDbInteraction.read());
        influxDbInteraction.close();
    }

    /**
     * Add from an {@link InfluxDB} using a connected instance
     * @author Cristoph Kohnen
     * @param influxDB The instance which is connected to
     * @param database The desired database
     */
    public void addFromInfluxDB(InfluxDB influxDB, String database) {
        InfluxDbInteraction influxDbInteraction = new InfluxDbInteraction(influxDB);
        influxDbInteraction.setDatabase(database);
        addFromMap(influxDbInteraction.read());
        influxDbInteraction.close();
    }

    /**
     * Add from an .eml file
     * @author Cristoph Kohnen
     * @param emlFile The file from which all data should be added
     * @throws Exception If provided a bad file
     */
    public void addFromEMLFile(File emlFile) throws Exception {
        Logger.log(Logger.INFO_LEVEL_2 + String.format("Adding to %s from EML File %s", id.toString(), emlFile.getName()));
        addFromTarArchive(Objects.requireNonNull(GetFromEML.getTarFromEML(emlFile)));
    }

    /**
     * Add from EML files
     * @author ChaosMelone9
     * @param emlFiles A list of files from which you want to all data should be added
     * @throws Exception If provided a bad file
     */
    public void addFromEMLFiles(List<File> emlFiles) throws Exception {
        addFromTarArchives(GetFromEML.getTarsFromEMLS(emlFiles));
    }

    /**
     * Add from a SolarLog JSON interface
     * @author Cristoph Kohnen
     * @param solarLogURL This is the url of your solarlog. This should end with "/getjp".
     * @throws IOException If provided a bad URL
     * @throws org.json.simple.parser.ParseException If something went wrong with the request
     * @throws ParseException If something went wrong with the request
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void addFromSolarLog(URL solarLogURL) throws IOException, org.json.simple.parser.ParseException, ParseException {
        Logger.log(Logger.INFO_LEVEL_2 + String.format("Adding to %s from SolarLog at %s", id.toString(), solarLogURL.toString()));
        addFromMap(GetValuesFromJson.getAsMap(GetJsonFromSolarLog.getFromSolarLogInterface(solarLogURL)));
    }

    /**
     * Add from a JS file found on the FTP server
     * @author Cristoph Kohnen
     * @param jsFile The file of which all data should be extracted and added
     * @throws IOException if provided a bad file
     */
    public void addFromJSFile(File jsFile) throws IOException {
        addFromMap(GetFromDat.getAsMapFromJSFile(jsFile));
    }

    /**
     * Add from a .js files found on the FTP server
     * @author Cristoph Kohnen
     * @param jsFiles  A list of files of which all data should be extracted and added
     */
    public void addFromJSFiles(List<File> jsFiles) {
        for (File jsFile : jsFiles) {
            try {
                addFromJSFile(jsFile);
            } catch (IOException e) {
                Logger.warn(String.format("Unable to import data from file %s", jsFile.getName()));
            }
        }
    }

    /**
     * Add all data from an FTP-Server. This downloads all .js files and extracts data off of them
     * @author Cristoph Kohnen
     * @param host The host of the FTP server
     * @param user Your username
     * @param password Your password
     * @throws IOException If something goes wrong with the connection
     */
    public void addFromFTPServer(String host, String user, String password) throws IOException {
        Logger.log(Logger.INFO_LEVEL_1 + String.format("Adding to %s from FTP Server %s@%s", id, user, host));
        addFromJSFiles(GetFromFTPServer.getJSFilesFromFTPServer(host, user, password));
    }




    /**
     * Write to a {@link File}
     * @author Cristoph Kohnen
     * @param solarLogFile The location, where the data should be saved to
     * @throws IOException If provided a bad location
     */
    public void writeToSolarLogFile(File solarLogFile) throws IOException {
        Logger.log(Logger.INFO_LEVEL_2 + String.format("Writing %s to SolarLog file %s", id.toString(), solarLogFile.getName()));
        WriteFileObject.write(solarLogFile, getAsFileObject());
    }

    /**
     * Write to an {@link InfluxDB}
     * @author Cristoph Kohnen
     * @param server The url of the {@link InfluxDB}. This should include "http://" and port number
     * @param username Your username
     * @param password Your password
     * @param database The desired database
     * @param batchPointLimit The limit of {@link org.influxdb.dto.BatchPoints} that can be written to the {@link InfluxDB} in parallel
     * @throws NullPointerException If something goes wrong with the connection to the database
     */
    public void writeToInfluxDBDataBase(String server, String username, String password, String database, int batchPointLimit) throws NullPointerException {
        Logger.log(Logger.INFO_LEVEL_2 + String.format("Writing %s to InfluxDB %s", id.toString(), server));
        InfluxDbInteraction influxDbInteraction = new InfluxDbInteraction(server, username, password);
        influxDbInteraction.setBatchPointLimit(batchPointLimit);
        influxDbInteraction.setDatabase(database);
        influxDbInteraction.write(this);
        influxDbInteraction.close();
    }

    /**
     * Write to an {@link InfluxDB} database
     * @author Cristoph Kohnen
     * @param influxDB The {@link InfluxDB} to which should be written. This should have the database already set
     * @param batchPointLimit The limit of {@link org.influxdb.dto.BatchPoints} that can be written to the {@link InfluxDB} in parallel
     * @throws NullPointerException If something goes wrong with the connection to the database
     **/
    public void writeToInfluxDBDataBase(InfluxDB influxDB, int batchPointLimit) throws NullPointerException {
        Logger.log(Logger.INFO_LEVEL_2 + String.format("Writing %s to InfluxDB", id.toString()));
        InfluxDbInteraction influxDbInteraction = new InfluxDbInteraction(influxDB);
        influxDbInteraction.setBatchPointLimit(batchPointLimit);
        influxDbInteraction.write(this);
        influxDbInteraction.close();
    }

    /**
     * Write SQL query to file
     * @author Cristoph Kohnen
     * @param database The database to which should be written
     * @param table The table to which should be written
     * @param key The key which should be used
     * @param value1 The name of the first value
     * @param value2 The name of the second value
     * @param value3 The name of the third value
     * @param value4 The name of the fourth file
     * @param value5 The name of the fifth file
     * @return The file the query has been written to
     * @throws IOException If the process is not permitted to write into the working directory
     **/
    public File writeSQLQueryToFile(String database, String table, String key, String value1, String value2, String value3, String value4, String value5) throws IOException {
        Logger.log(Logger.INFO_LEVEL_2 + String.format("Writing SQL query to file for %s", id.toString()));
        return SQLQuery.getWriteQuery(database,table,key,value1,value2,value3,value4,value5,this);
    }

    /**
     * Write to an MySQL database
     * @author Cristoph Kohnen
     * @param host The host of the SQL database
     * @param user Your username
     * @param password Your password
     * @param database The database to which should be written
     * @param table The table to which should be written
     * @throws SQLException If some operation results in an error on the server side
     **/
    public void writeToMySQLDatabase(String host, String user, String password, String database, String table) throws SQLException {
        Logger.log(Logger.INFO_LEVEL_2 + String.format("Writing %s to MySQL database %S", id.toString(), host));
        MySQLInteraction.write(host,user,password,database,table,this);
    }




    /**
     * Get the {@link SolarMap} in {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format
     * @author Cristoph Kohnen
     * @return All data in the {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format
     */
    public Map<Date, List<Integer>> getAsMap() {
        return data;
    }

    /**
     * Get values from a specific timestamp
     * @author Cristoph Kohnen
     * @param timestamp The timestamp of which data should be retrieved
     * @return The values for the specified timestamp
     */
    public List<Integer> getValuesOnDate(Date timestamp) {
        return data.get(timestamp);
    }

    /**
     * Get values from a specific timestamp
     * @author Cristoph Kohnen
     * @param timestamp The timestamp of which the value should be retrieved
     * @param value The index of the value that should be retrieved
     * @return The value on that timestamp
     */
    public Integer getValueOnDate(Date timestamp, Integer value) {
        return getValuesOnDate(timestamp).get(value);
    }

    /**
     * Return a {@link FileObject} for storage use
     * @author Cristoph Kohnen
     * @return All data as a {@link FileObject}
     */
    public FileObject getAsFileObject() {
        FileObject fileObject = new FileObject(this.getAsMap());
        fileObject.putInformation("created", createdOn);
        fileObject.putInformation("id", id);
        return fileObject;
    }

    /**
     * Return a {@link JSONObject} for external use
     * @author Cristoph Kohnen
     * @return All data as a {@link JSONObject}
     */
    public JSONObject getAsJSON() {
        return ConvertJson.convertMapToJson(getAsMap());
    }

    /**
     * Return a {@link JSONObject} as string for compatibility
     * @author Cristoph Kohnen
     * @return All data as a {@link JSONObject} converted to a string
     */
    public String getAsJSONString() {
        return getAsJSON().toJSONString();
    }

    /**
     * Get Only Data from a specific timeframe
     * @author Cristoph Kohnen
     * @param firstTimestamp The start of the timeframe
     * @param lastTimestamp The end of the timeframe
     * @return All data between the two specified timestamps
     */
    public SolarMap getInTimeframe(Date firstTimestamp, Date lastTimestamp) {
        Map<Date, List<Integer>> map = new HashMap<>();
        data.forEach((date, integers) -> {
            if (date.after(firstTimestamp) & date.before(lastTimestamp)) {
                map.put(date,integers);
            }
        });
        return new SolarMap(map);
    }




    /**
     * Get on specific timestamp
     * @author Cristoph Kohnen
     * @param timestamp The timestamp of which data should be retrieved
     * @return The values for the specified timestamp
     */
    public List<Integer> get(Date timestamp) {
        return data.get(timestamp);
    }

    /**
     * Get value on specific timestamp
     * @author Cristoph Kohnen
     * @param timestamp The timestamp of which the value should be retrieved
     * @param value The index of the value that should be retrieved
     * @return The value on that timestamp
     */
    public int getValue(Date timestamp, int value) {
        return get(timestamp).get(value);
    }

    /**
     * Whether or not a specific timestamp is included
     * @author Cristoph Kohnen
     * @param timestamp The timestamp that should be checked
     * @return Whether or not the specified timestamp is included
     */
    public boolean containsKey(Date timestamp) {
        return data.containsKey(timestamp);
    }

    /**
     * Whether or not a specific timestamp is included
     * @author Cristoph Kohnen
     * @param timestamp The timestamp that should be checked
     * @return Whether or not the specified timestamp is included
     */
    public boolean includes(Date timestamp) {
        return containsKey(timestamp);
    }

    /**
     * Whether or not a day is included
     * @author Cristoph Kohnen
     * @param day The day that should be checked
     * @return Whether or not the specified day is included
     */
    public boolean includesDay(Date day) {
        return includes(GetStartOf.day(day));
    }

    /**
     * Whether or not a day is included
     * @author Cristoph Kohnen
     * @param day The day that should be checked
     * @return Whether or not the specified day is included
     */
    public boolean includesDay(LocalDate day) {
        return includes(GetStartOf.day(day));
    }

    /**
     * Whether or not a month is included
     * @author Cristoph Kohnen
     * @param month The month that should be checked
     * @return Whether or not the specified month is included
     */
    public boolean includesMonth(YearMonth month) {
        for (Date date : Entries.getDaysPerMonth(month)) {
            if(includesDay(date)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Whether or not a year is included
     * @author Cristoph Kohnen
     * @param year The year that should be checked
     * @return Whether or not the specified year is included
     */
    public boolean includesYear(Year year) {
        for (YearMonth month : Entries.getMonthsPerYear(year)) {
            if (includesMonth(month)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The number of all stored timestamps
     * @author Cristoph Kohnen
     * @return The amount of all stored timestamps
     */
    public int size() {
        return data.size();
    }

    /**
     * Whether or not the map is empty
     * @author Cristoph Kohnen
     * @return Whether or not the {@link SolarMap} is empty
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Get the time this {@link SolarMap} was created
     * @author Cristoph Kohnen
     * @return The time, this {@link SolarMap} was created
     */
    public Date getCreationTime() {
        return createdOn;
    }

    /**
     * Returns the ID of this {@link SolarMap}
     * @author Cristoph Kohnen
     * @return The ID of this {@link SolarMap}
     */
    public UUID getId() {
        return id;
    }

    /**
     * Clears the {@link SolarMap}
     * @author Cristoph Kohnen
     */
    public void clear() {
        data.clear();
        Logger.log(Logger.INFO_LEVEL_1 + String.format("Cleared %s", id.toString()));
    }

    /**
     * Get values as collection
     * @author Cristoph Kohnen
     * @return All values as a {@link Collection}
     */
    public Collection<List<Integer>> values() {
        return data.values();
    }

    /**
     * Overrides toString() to something human readable
     * @author Cristoph Kohnen
     * @return Useful information to string
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
     * @author Cristoph Kohnen
     */
    public void forEach(BiConsumer<Date, List<Integer>> consumer) {
        data.forEach(consumer);
    }

    /**
     * Compares the contents of two SolarMaps
     * @author Cristoph Kohnen
     * @return Whether or not the data is the same as of another {@link SolarMap}
     */
    public boolean equals(SolarMap solarMap) {
        return data.equals(solarMap.getAsMap());
    }
}
