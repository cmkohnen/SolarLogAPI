package me.meloni.SolarLogAPI.DataConversion;

import me.meloni.SolarLogAPI.FileInteraction.GetFile;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetFileContent;
import me.meloni.SolarLogAPI.FileInteraction.Tools.FileVersion;
import me.meloni.SolarLogAPI.Handling.Logger;
import org.apache.commons.lang3.StringUtils;

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
@SuppressWarnings("DuplicatedCode")
public class GetData  {
    private static final String DATEFORMAT = "dd.MM.yy HH:mm:ss";

    public static Map<Date, List<Integer>> getAsMapFromDatFile(File file) throws ParseException, IOException {
        String fileVersion = FileVersion.getFileVersion(file);
        List<Integer> positions = FileVersion.getPositionMatrix().get(fileVersion);
        Logger.log(Logger.INFO_LEVEL_3 + "Importing data from \"" + file.getAbsolutePath() + "\" using file version v" + fileVersion);
        List<String> MinuteData = GetDataSection.getMinuteDataRows(GetFileContent.getFileContentAsList(GetFile.getPathFromFile(file)));
        Map<Date, List<Integer>> data = new HashMap<>();
        for (String item : MinuteData) {
            String[] str = item.split(";");
            List<String> values = Arrays.asList(str);

            if(values.size() == positions.get(4)) {
                DateFormat formatter = new SimpleDateFormat(DATEFORMAT);
                Date d = formatter.parse(values.get(2));

                List<Integer> valuesEach = new ArrayList<>();
                int consPac = Integer.parseInt(values.get(positions.get(0)));
                int consYieldDay = Integer.parseInt(values.get(positions.get(1)));
                int Pac = Integer.parseInt(values.get(positions.get(2)));
                int yieldDay = Integer.parseInt(values.get(positions.get(3)));
                int ownConsumption = Math.min(consPac, Pac);

                valuesEach.add(consPac);
                valuesEach.add(consYieldDay);
                valuesEach.add(Pac);
                valuesEach.add(yieldDay);
                valuesEach.add(ownConsumption);

                data.put(d, valuesEach);
            }
        }
        return data;
    }

    public static Map<Date, List<Integer>> getAsMapFromJSFile(File file) throws IOException, ParseException {
        Logger.log(Logger.INFO_LEVEL_3 + "Importing data from \"" + file.getAbsolutePath() + "\".");
        List<String> lines = GetFileContent.getFileContentAsList(GetFile.getPathFromFile(file));
        Map<Date, List<Integer>> data = new HashMap<>();
        for (String line : lines) {
            String alteredLine = StringUtils.substringBetween(line, "\"" , "\"");
            if(alteredLine != null) {
                alteredLine = alteredLine.replaceAll("\\|", ";");
                List<String> objects = Arrays.asList(alteredLine.split(";"));
                if(objects.size() >= 7) {
                    DateFormat formatter = new SimpleDateFormat(DATEFORMAT);
                    Date d = formatter.parse(objects.get(0));

                    try {
                        List<Integer> valuesEach = new ArrayList<>();
                        int consPac = Integer.parseInt(objects.get(1));
                        int consYieldDay = Integer.parseInt(objects.get(3));
                        int Pac = Integer.parseInt(objects.get(4));
                        int yieldDay = Integer.parseInt(objects.get(7));
                        int ownConsumption = Math.min(consPac, Pac);

                        valuesEach.add(consPac);
                        valuesEach.add(consYieldDay);
                        valuesEach.add(Pac);
                        valuesEach.add(yieldDay);
                        valuesEach.add(ownConsumption);

                        data.put(d, valuesEach);
                    } catch (NumberFormatException ignored) {
                        Logger.warn("Unable to convert File " + file.getName());
                    }
                }
            }
        }
        return data;
    }
}
