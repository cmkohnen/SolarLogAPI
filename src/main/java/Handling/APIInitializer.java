package Handling;

import FileInteraction.ReadFiles.ReadFileObject;
import FileInteraction.WirteFiles.WriteFileObject;
import TransformUtilities.DataConversion.EntriesPerDay;
import TransformUtilities.DataConversion.GetData;
import TransformUtilities.DataConversion.GetDataSection;
import FileInteraction.GetFile;
import FileInteraction.ReadFiles.GetFileContent;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class APIInitializer {
    public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException {
        //Do Stuff

        String path = "/home/christoph/Schreibtisch/SolarLog/backup_data_19.07.20.dat";

        //CustomLogger.log(GetFileContent.FileContentAsList(path).get(1));

        final String OLD_FORMAT = "dd.MM.yy HH:mm:ss";

        DateFormat formatter = new SimpleDateFormat(OLD_FORMAT);
        Date d = formatter.parse("18.06.20 04:25:00");

        Map<Date, List<Integer>> data = GetData.MinuteDataMap(GetDataSection.MinuteData(GetFileContent.FileContentAsList(GetFile.Path(GetFile.File(path)))));

        //WriteFileObject.write(GetFile.File("/home/christoph/Schreibtisch/SolarLog/testfile"),data);

        Map<Date, List<Integer>> datafromFile = (Map<Date, List<Integer>>) ReadFileObject.fileObject(GetFile.File("/home/christoph/Schreibtisch/SolarLog/testfile"));

        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter2.format(d);

        System.out.println(datafromFile);

    }
}
