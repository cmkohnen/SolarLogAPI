package me.meloni.SolarLogAPI.Handling;

import me.meloni.SolarLogAPI.DatabaseInteraction.InfluxDbInteraction;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetFromTar;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.ReadFileObject;
import me.meloni.SolarLogAPI.FileInteraction.Tools.FileObject;
import me.meloni.SolarLogAPI.DataConversion.Entries;
import me.meloni.SolarLogAPI.DataConversion.GetData;
import me.meloni.SolarLogAPI.DataConversion.GetGraphData;
import me.meloni.SolarLogAPI.DataConversion.GetStartOf;
import me.meloni.SolarLogAPI.FileInteraction.WriteFiles.WriteFileObject;
import org.influxdb.InfluxDB;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This Class provides a universal way to handle the data and implements useful functions to convert data. THIS SHOULD NOT BE USED TO STORE DATA PERMANENTLY. For that use please refer to {@link FileObject}.
 * @author ChaosMelone9
 * @since 2.0.0
 */
public class SolarMap implements Serializable {
    private Map<Date, List<Integer>> data = new HashMap<>();

    public SolarMap(Map<Date, List<Integer>> Map) {
        data = Map;
    }

    public SolarMap(FileObject fileObject) { data = fileObject.getData();}

    public SolarMap() { }

    public void setFromMap(Map<Date, List<Integer>> map) {
        data = map;
    }

    public void setOnDate(Date date, List<Integer> values) {
        data.put(date, values);
    }

    public void addFromMap(Map<Date, List<Integer>> map) {
        map.forEach((date, integers) -> data.putIfAbsent(date, integers));
    }

    public void addFromSolarMap(SolarMap map) {
        addFromMap(map.getAsMap());
    }

    public void addImportFromFile(File file) throws IOException, ParseException {
        if(file.exists()) {
            addFromMap(GetData.MinuteDataMap(file));
        }
    }

    public void addImportFromFiles(List<File> files) throws IOException, ParseException {
        int i1 = files.size();
        int i2 = 0;
        for (File file : files) {
            i2++;
            Logger.log("Importing from file " + file.getName() + "  (" + i2 + " of " + i1 + ").");
            addImportFromFile(file);
        }
    }

    public void addFromTar(File file) throws Exception {
        if(file.exists()) {
            addImportFromFiles(GetFromTar.getValidFilesFromTarArchive(file));
        }
    }

    public void addFromTars(List<File> files) throws Exception {
        addImportFromFiles(GetFromTar.getValidFilesFromTarArchives(files));
    }

    public void addFromFileObject(FileObject fileObject) {
        addFromMap(fileObject.getData());
    }

    public void addFromDataFile(File file) throws IOException, ClassNotFoundException {
        addFromFileObject(ReadFileObject.fileObject(file));
    }

    public void addFromDataFiles(List<File> files) throws IOException, ClassNotFoundException {
        int i1 = files.size();
        int i2 = 0;
        for (File file : files) {
            i2++;
            Logger.log("Importing from file " + file.getName() + "  (" + i2 + " of " + i1 + ").");
            addFromDataFile(file);
        }
    }

    public void addFromDataBase(String server, String username, String password) throws ParseException {
        InfluxDbInteraction influxDbInteraction = new InfluxDbInteraction(server,username,password);
        addFromMap(influxDbInteraction.read());
    }




    public void writeToDataFile(File file) throws IOException {
        WriteFileObject.write(file, this.getFileObject());
    }

    public void writeToInfluxDBDataBase(String server, String username, String password) {
        InfluxDbInteraction influxDbInteraction = new InfluxDbInteraction(server, username, password);
        influxDbInteraction.write(this);
        influxDbInteraction.close();
    }

    public void writeToInfluxDBDataBase(InfluxDB influxDB) {
        InfluxDbInteraction influxDbInteraction = new InfluxDbInteraction(influxDB);
        influxDbInteraction.write(this);
        influxDbInteraction.close();
    }





    public Map<Date, List<Integer>> getAsMap() {
        return data;
    }

    public List<Integer> getValuesFromDate(Date date) {
        return data.get(date);
    }

    public Integer getValueFromDate(Date date, Integer value) {
        return getValuesFromDate(date).get(value);
    }

    public List<List<Double>> getDayGraphData(Date date) throws ParseException {
        return GetGraphData.dayView(date, data);
    }

    public List<List<Double>> getMonthGraphData(YearMonth yearMonth) throws ParseException {
        return GetGraphData.monthView(yearMonth, data);
    }


    public List<List<Double>> getDayData(Date day) throws ParseException {
        return GetGraphData.dayView(day, data);
    }

    public List<List<Double>> getMonthData(YearMonth yearMonth) throws ParseException {
        return GetGraphData.monthView(yearMonth, data);
    }

    public FileObject getFileObject() {
        return new FileObject(this.getAsMap());
    }







    public List<Integer> get(Date date) {
        return data.get(date);
    }

    public int getValue(Date date, int value) {
        return get(date).get(value);
    }

    public boolean containsKey(Date date) {
        return data.containsKey(date);
    }

    public boolean includes(Date date) {
        return containsKey(date);
    }

    public boolean includesDay(Date date) {
        return includes(GetStartOf.day(date));
    }

    public boolean includesDay(LocalDate date) {
        return includes(GetStartOf.day(date));
    }

    public boolean includesMonth(YearMonth yearMonth) {
        for (Date date : Entries.entriesPerMonth(yearMonth)) {
            if(includesDay(date)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return data.size();
    }

    public void clear() {
        data.clear();
    }

}
