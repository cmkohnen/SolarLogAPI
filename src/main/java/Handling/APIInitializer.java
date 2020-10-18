package Handling;

import TransformUtilities.DataConversion.EntriesPerDay;
import TransformUtilities.DataConversion.GetData;
import TransformUtilities.DataConversion.GetDataSection;
import TransformUtilities.ReadFiles.GetFile;
import TransformUtilities.ReadFiles.GetFileContent;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class APIInitializer {
    public static void main(String[] args) throws IOException, ParseException {
        //Do Stuff

        String path = "/home/christoph/Schreibtisch/SolarLog/backup_data_19.07.20.dat";

        //CustomLogger.log(GetFileContent.FileContentAsList(path).get(1));

        final String OLD_FORMAT = "dd.MM.yy HH:mm:ss";

        DateFormat formatter = new SimpleDateFormat(OLD_FORMAT);
        Date d = formatter.parse("18.06.20 04:25:00");

        Map<Date, List<Integer>> data = GetData.MinuteDataMap(GetDataSection.MinuteData(GetFileContent.FileContentAsList(GetFile.Path(GetFile.File(path)))));

        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter2.format(d);
        System.out.println(EntriesPerDay.entries(new SimpleDateFormat("dd.MM.yy").parse("18.06.20")));

        System.out.println(new SimpleDateFormat("dd.MM.yy").parse("18.06.20"));

    }
}
