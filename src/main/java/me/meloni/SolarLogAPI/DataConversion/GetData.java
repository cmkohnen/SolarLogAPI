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
    /*
    Positions of values in List<String> format
        Verbrauch W: 6
        Verbrauch kWh: 10
        Leistung W: 17
        Ertrag KWh: 23
        Eigenverbrauch W: 44

     */
     static final String DATEFORMAT = "dd.MM.yy HH:mm:ss";

    public static Map<Date, List<Integer>> getMinuetDataMap(File file) throws ParseException, IOException {
        Logger.log("Importing from " + file);
        String fileVersion = FileVersion.getFileVersion(file);
        List<Integer> positions = FileVersion.matrix().get(fileVersion);

        List<String> MinuteData = GetDataSection.MinuteData(GetFileContent.fileContentAsList(GetFile.path(file)));
        Map<Date, List<Integer>> Data = new HashMap<>();

        for (String item : MinuteData) {

            String[] str = item.split(";");
            List<String> values = Arrays.asList(str);

            //intitialize variables
            int verbrauchw;
            int verbrauchkwh;
            int leistungw;
            int ertragkwh;
            int energieverbrauchw;

            List<Integer> valuesEach = new ArrayList<>();

            DateFormat formatter = new SimpleDateFormat(DATEFORMAT);
            Date d = formatter.parse(values.get(2));

            //getting values
            verbrauchw = Integer.parseInt(values.get(positions.get(0)));
            verbrauchkwh = Integer.parseInt(values.get(positions.get(1)));
            leistungw = Integer.parseInt(values.get(positions.get(2)));
            ertragkwh = Integer.parseInt(values.get(positions.get(3)));
            energieverbrauchw = Integer.parseInt(values.get(positions.get(4)));


            //writing values to List
            valuesEach.add(verbrauchw);
            valuesEach.add(verbrauchkwh);
            valuesEach.add(leistungw);
            valuesEach.add(ertragkwh);
            valuesEach.add(energieverbrauchw);


            //Writing List to Map
            Data.put(d, valuesEach);
        }
        return Data;
    }

}
