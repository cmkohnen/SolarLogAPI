package me.meloni.SolarLogAPI.DataConversion;

import me.meloni.SolarLogAPI.FileInteraction.GetFile;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetFileContent;
import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.Inverter;
import me.meloni.SolarLogAPI.InverterMap;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GetFromJS {
    private static final String DATEFORMAT = "dd.MM.yy HH:mm:ss";

    public static Map<Inverter, InverterMap> getAsInverterMapsFromJSFile(File baseVars, File file) throws IOException, ParseException {
        List<Inverter> inverters = getInvertersFromBaseVars(baseVars);
        return getAsInverterMapsFromJSFile(inverters, file);
    }

    public static Map<Inverter, InverterMap> getAsInverterMapsFromJSFiles(File baseVars, List<File> files) throws IOException, ParseException {
        List<Inverter> inverters = getInvertersFromBaseVars(baseVars);
        Map<Inverter, InverterMap> data = new HashMap<>();
        for (File file : files) {
            // Maybe implement in SolarMap
        }
        return data;
    }

    public static Map<Inverter, InverterMap> getAsInverterMapsFromJSFile(List<Inverter> inverters, File file) throws IOException, ParseException {
        Map<Inverter, InverterMap> inverterMaps = new HashMap<>();
        // loop for every inverter
        for (int i = 0; i < inverters.size(); i++) {
            Inverter inverter = inverters.get(i);
            InverterMap inverterMap = new InverterMap(new HashMap<>(), inverter);
            // loop over every line in one file and extract values
            for (String line : GetFileContent.getFileContentAsList(GetFile.getPathFromFile(file))) {
                // Get everything in quotation marks split by "|"
                List<String> separated = Arrays.asList(StringUtils.substringBetween(line, "\"", "\"").split("\\|"));
                // get the timestamp as Date
                Date date = new SimpleDateFormat(DATEFORMAT).parse(separated.get(0));
                Map<String, Integer> values = new HashMap<>();
                int inverterStrings = inverter.strings;
                if (inverterStrings == 0) inverterStrings = 1;
                // Get values as String for current inverter
                List<String> strings = Arrays.asList(separated.get(i + 1).split(";"));
                if (inverter.function == 0) {
                    //global pac, Index 0
                    values.put("PAC", Integer.valueOf(strings.get(0)));
                    // PDCs, Index string + 1
                    for(int j = 0; j < inverterStrings; j++) {
                        values.put(String.format("PDC%s", (j + 1)), Integer.valueOf(strings.get(j + 1)));
                    }
                    // Yield for the day, Index amount of strings + 1
                    values.put("YieldDay", Integer.parseInt(strings.get(inverterStrings + 1)));
                    // UDCs, Index amount of strings + 2 + string
                    for(int j = 0; j < inverterStrings; j++) {
                        values.put(String.format("UDC%s", (j + 1)), Integer.valueOf(strings.get(inverterStrings) + 2 + j));
                    }
                    if (inverter.temperature) {
                        // Temperature, Index amount of strings * 2 + 2
                        values.put("Temperature", Integer.valueOf(strings.get(inverterStrings*2 + 2)));
                    }
                } else if(inverter.function == 2) {
                    //global pac, Index 0
                    values.put("ConsPAC", Integer.valueOf(strings.get(0)));
                    // PDCs, Index string + 1
                    for(int j = 0; j < inverterStrings; j++) {
                        values.put(String.format("ConsPDC%s", (j + 1)), Integer.valueOf(strings.get(j + 1)));
                    }
                    // Yield for the day, Index amount of strings + 1
                    values.put("ConsYieldDay", Integer.parseInt(strings.get(inverterStrings + 1)));
                    // UDCs, Index amount of strings + 2 + string
                    for(int j = 0; j < inverterStrings; j++) {
                        values.put(String.format("ConsUDC%s", (j + 1)), Integer.valueOf(strings.get(inverterStrings) + 2 + j));
                    }
                    if (inverter.temperature) {
                        // Temperature, Index amount of strings * 2 + 2
                        values.put("Temperature", Integer.valueOf(strings.get(inverterStrings*2 + 2)));
                    }
                } else {
                    Logger.warn("Unsupported inverter function!");
                }
                inverterMap.setOnDate(date, values);
            }
            inverterMaps.put(inverter, inverterMap);
        }
        return inverterMaps;
    }

    public static List<Inverter> getInvertersFromBaseVars(File file) throws IOException {
        List<Inverter> inverters = new ArrayList<>();
        for (String line : GetFileContent.getFileContentAsList(GetFile.getPathFromFile(file))) {
            if (line.startsWith("WRInfo") && !(StringUtils.substringBetween(line, "(" , ")") == null)) {
                try {
                    List<String> values = Arrays.asList(StringUtils.substringBetween(line, "(", ")").split(","));
                    inverters.add(new Inverter(values.get(0), values.get(4), Integer.parseInt(values.get(5)), Integer.parseInt(values.get(11)), values.get(12).equals("1")));
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    //Code will get here by multiple strings ("WRInfo[1][6]=new Array("String 1","String 2")"), can be ignored
                }
            }
        }
        return inverters;
    }

}
