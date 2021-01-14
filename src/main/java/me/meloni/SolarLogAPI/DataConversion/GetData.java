package me.meloni.SolarLogAPI.DataConversion;

import me.meloni.SolarLogAPI.FileInteraction.GetFile;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetFileContent;
import me.meloni.SolarLogAPI.FileInteraction.Tools.FileVersion;
import me.meloni.SolarLogAPI.Handling.Logger;

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

    public static Map<Date, List<Integer>> getDataMap(File file) throws ParseException, IOException {
        String fileVersion = FileVersion.getFileVersion(file);
        List<Integer> positions = FileVersion.getPositionMatrix().get(fileVersion);
        Logger.log(Logger.INFO_LEVEL_3 + "Importing data from \"" + file.getAbsolutePath() + "\" using file version v" + fileVersion);
        List<String> MinuteData = GetDataSection.getMinuteDataRows(GetFileContent.getFileContentAsList(GetFile.getPathFromFile(file)));
        Map<Date, List<Integer>> data = new HashMap<>();
        for (String item : MinuteData) {
            String[] str = item.split(";");
            List<String> values = Arrays.asList(str);

            DateFormat formatter = new SimpleDateFormat(DATEFORMAT);
            Date d = formatter.parse(values.get(2));

            data.put(d, values(values,positions));
        }
        return data;
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
        energieverbrauchw = Math.min(verbrauchw, leistungw);
        //writing values to List
        valuesEach.add(verbrauchw);
        valuesEach.add(verbrauchkwh);
        valuesEach.add(leistungw);
        valuesEach.add(ertragkwh);
        valuesEach.add(energieverbrauchw);
        return valuesEach;
    }
}
