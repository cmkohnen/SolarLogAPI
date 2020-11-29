package Handling;

import FileInteraction.ReadFiles.GetFromTar;
import FileInteraction.ReadFiles.ReadFileObject;
import FileInteraction.Tools.FileObject;
import TransformUtilities.DataConversion.Entries;
import TransformUtilities.DataConversion.GetData;
import TransformUtilities.DataConversion.GetGraphData;
import TransformUtilities.DataConversion.GetStartOf;

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

public class SolarMap implements Serializable {
    private Map<Date, List<Integer>> data = new HashMap<>();

    public SolarMap(Map<Date, List<Integer>> Map) {
        data = Map;
    }

    public SolarMap() {

    }

    public Map<Date, List<Integer>> getAsMap() {
        return data;
    }

    public void setFromMap(Map<Date, List<Integer>> map) {
        data = map;
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
        for (File file : files) {
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
        for (File file : files) {
            addFromDataFile(file);
        }
    }






    public List<Integer> getValuesFromDate(Date date) {
        return data.get(date);
    }

    public Integer getValueFromDate(Date date, Integer value) {
        return getValuesFromDate(date).get(value);
    }

    public void setOnDate(Date date, List<Integer> values) {
        data.put(date, values);
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
        for (Date date : Entries.entriespermonth(yearMonth)) {
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
