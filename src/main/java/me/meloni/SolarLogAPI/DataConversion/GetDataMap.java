package me.meloni.SolarLogAPI.DataConversion;

import me.meloni.SolarLogAPI.SolarMap;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This Class includes a function to get Data from multiple files.
 * @author ChaosMelone9
 * @since 0.0.3
 * @deprecated
 */
public class GetDataMap {
    /**
     * @deprecated
     */
    public static Map<Date, List<Integer>> getDataFromFiles(List<File> paths) throws IOException, ClassNotFoundException {
        SolarMap solarMap = new SolarMap();
        solarMap.addFromDataFiles(paths);
        return solarMap.getAsMap();
    }
}
