package me.meloni.SolarLogAPI.DataConversion;

import me.meloni.SolarLogAPI.FileInteraction.GetFile;
import me.meloni.SolarLogAPI.FileInteraction.ReadFiles.GetFileContent;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * This Class includes functions to get information about the device of a file.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class FileInformation {

    @SuppressWarnings("SpellCheckingInspection")
    static final String DATE_FORMAT = "dd.MM.yyhh:mm:ss";

    public static Map<String,Object> getInformation(File file) throws IOException, ParseException {
        Map<String,Object> information = new HashMap<>();

        String infoRow = GetDataSection.getInfoRow(GetFileContent.getFileContentAsList(GetFile.getPathFromFile(file)));

        information.put("model", getModel(infoRow));
        information.put("date", date(infoRow));
        information.put("build", getBuild(infoRow));

        return information;
    }

    public static String getModel(String infoRow){
        return list1(infoRow).get(1);
    }

    public static Date date(String infoRow) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.parse(list1(infoRow).get(8) + list1(infoRow).get(9));
    }

    public static String getBuild(String infoRow){
        if(infoRow != null) {
            return list1(infoRow).get(10) + " " + list1(infoRow).get(11) + " " + list1(infoRow).get(12);
        } else return null;
    }

    public static String getBuildVersion(String infoRow){
        if(infoRow != null) {
            return list1(infoRow).get(10).substring(1,6);
        } else return null;
    }

    private static List<String> list1(String infoRow) {
        if(infoRow != null) {
            return Arrays.asList(infoRow.split(" "));
        } else return null;
    }
}
