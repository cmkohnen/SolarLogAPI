package TransformUtilities.DataConversion;

import FileInteraction.GetFile;
import FileInteraction.ReadFiles.GetFileContent;

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

    static final String DATE_FORMAT = "dd.MM.yyhh:mm:ss";

    public static Map<String,Object> information(File file) throws IOException, ParseException {
        Map<String,Object> information = new HashMap<>();

        String inforow = GetDataSection.InfoRow(GetFileContent.FileContentAsList(GetFile.Path(file)));

        information.put("model", model(inforow));
        information.put("date", date(inforow));
        information.put("build",build(inforow));


        return information;
    }

    public static String model(String InfoRow){
        return list1(InfoRow).get(1);
    }

    public static Date date(String InfoRow) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.parse(list1(InfoRow).get(8) + list1(InfoRow).get(9));
    }

    public static String build(String InfoRow){
        return list1(InfoRow).get(10) + " " + list1(InfoRow).get(11) + " " + list1(InfoRow).get(12);
    }

    private static List<String> list1(String InfoRow) {
        return Arrays.asList(InfoRow.split(" "));
    }
}
