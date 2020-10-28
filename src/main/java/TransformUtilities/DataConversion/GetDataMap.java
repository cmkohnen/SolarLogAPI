package TransformUtilities.DataConversion;

import FileInteraction.GetFile;
import FileInteraction.ReadFiles.GetFileContent;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class GetDataMap {
    public static Map<Date, List<Integer>> DataFromFiles(List<File> paths) throws IOException, ParseException {
        Map<Date, List<Integer>> data = new HashMap<>();
        int i = 0;
        for (File path : paths) {
            i++;
            System.out.println("Getting from File " + path.toString() + " (" + i + " of " + paths.size() + ")");
            GetData.MinuteDataMap(path).forEach(data::putIfAbsent);
        }
        return data;
    }
}
