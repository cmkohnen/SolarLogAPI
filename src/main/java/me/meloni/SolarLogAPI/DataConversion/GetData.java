package me.meloni.SolarLogAPI.DataConversion;

import me.meloni.SolarLogAPI.FileInteraction.GetFile;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetFileContent;
import me.meloni.SolarLogAPI.FileInteraction.Tools.FileVersion;
import me.meloni.SolarLogAPI.Handling.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * This Class includes functions to extract data from lines found in the backup files.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class GetData  {
    static final String DATEFORMAT = "dd.MM.yy HH:mm:ss";

    /**
     * @deprecated
     */
    public static Map<Date, List<Integer>> getMinuteDataMap(File file) throws ParseException, IOException {
        Logger.log("Importing from " + file);
        String fileVersion = FileVersion.getFileVersion(file);
        List<Integer> positions = FileVersion.matrix().get(fileVersion);

        List<String> MinuteData = GetDataSection.minuteData(GetFileContent.fileContentAsList(GetFile.path(file)));
        Map<Date, List<Integer>> data = new HashMap<>();

        for (String item : MinuteData) {

            insert(positions, data, item);
        }
        return data;
    }

    public static Map<Date, List<Integer>> getDataMap(File file) throws IOException, ParseException {
        Logger.log("Importing from " + file);
        String fileVersion = FileVersion.getFileVersion(file);
        List<Integer> positions = FileVersion.matrix().get(fileVersion);

        Map<Date, List<Integer>> data = new HashMap<>();
        try (LineIterator it = FileUtils.lineIterator(file, "UTF-8")) {
            while (it.hasNext()) {
                String line = it.nextLine();
                if(line.startsWith("2;0")) {
                    insert(positions, data, line);
                }
            }
        }
        return data;
    }

    private static void insert(List<Integer> positions, Map<Date, List<Integer>> data, String line) throws ParseException {
        String[] str = line.split(";");
        List<String> values = Arrays.asList(str);

        DateFormat formatter = new SimpleDateFormat(DATEFORMAT);
        Date d = formatter.parse(values.get(2));

        data.put(d, values(values,positions));
    }

    private static List<Integer> values(List<String> strings, List<Integer> positions) {
        //initialize variables
        int verbrauchw;
        int verbrauchkwh;
        int leistungw;
        int ertragkwh;
        int energieverbrauchw;

        List<Integer> valuesEach = new ArrayList<>();

        //getting values
        verbrauchw = Integer.parseInt(strings.get(positions.get(0)));
        verbrauchkwh = Integer.parseInt(strings.get(positions.get(1)));
        leistungw = Integer.parseInt(strings.get(positions.get(2)));
        ertragkwh = Integer.parseInt(strings.get(positions.get(3)));
        energieverbrauchw = Integer.parseInt(strings.get(positions.get(4)));

        //writing values to List
        valuesEach.add(verbrauchw);
        valuesEach.add(verbrauchkwh);
        valuesEach.add(leistungw);
        valuesEach.add(ertragkwh);
        valuesEach.add(energieverbrauchw);

        return valuesEach;
    }

}
