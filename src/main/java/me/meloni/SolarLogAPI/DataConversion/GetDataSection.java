package me.meloni.SolarLogAPI.DataConversion;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class includes functions to only use lines in files which contain the wanted information.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class GetDataSection {

    static int StartMinuteSection = 0;
    static int EndMinuteSection = 0;

    static int InfoRowPosition = 0;
    static int FileVersionPosition = 1;

    public static String InfoRow(List<String> data) {
        return data.get(InfoRowPosition);
    }

    public static String FileVersion(List<String> data) {
        return data.get(FileVersionPosition);
    }


    public static List<String> MinuteData(List<String> data) {

        for (String s : data) {
            if(s.startsWith("#MIN")) {
                StartMinuteSection = data.indexOf(s) + 1;
            }
            if(s.startsWith("#Day")) {
                EndMinuteSection = data.indexOf(s) - 1;
            }
        }

        List<String> minuteData = new ArrayList<>();
        if(!(StartMinuteSection == 0 || EndMinuteSection == 0)) {
            for(int i = StartMinuteSection; i < EndMinuteSection; i++) {
                minuteData.add(data.get(i));
            }
        }
        return minuteData;
    }
}
