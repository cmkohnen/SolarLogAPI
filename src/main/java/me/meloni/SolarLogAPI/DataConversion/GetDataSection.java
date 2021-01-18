package me.meloni.SolarLogAPI.DataConversion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This Class includes functions to only use lines in files which contain the wanted information.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class GetDataSection {
    static final int InfoRowPosition = 0;
    static final int FileVersionPosition = 1;

    public static String getInfoRow(List<String> data) { return data.get(InfoRowPosition); }

    public static String getInfoRow(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        String info = null;
        if(scanner.hasNextLine()) {
            info = scanner.nextLine();
        }
        scanner.close();
        inputStream.close();
        return info;
    }

    /**
     * @deprecated
     */
    public static String getFileVersion(List<String> data) {
        return data.get(FileVersionPosition);
    }

    public static List<String> getMinuteDataRows(List<String> data) {
        List<String> minuteData = new ArrayList<>();
        for (String datum : data) {
            if(datum.startsWith("2;0")) {
                minuteData.add(datum);
            }
        }
        return minuteData;
    }
}
