package TransformUtilities.DataConversion;

import FileInteraction.GetFile;
import FileInteraction.ReadFiles.GetFileContent;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class GetDataMap {
    public static Map<Date, List<Integer>> DataFromFiles(List<File> paths) throws IOException, ParseException {
        List<List<String>> lists = new ArrayList<>();
        for (File file : paths) {
            lists.add(GetDataSection.MinuteData(GetFileContent.FileContentAsList(GetFile.Path(file))));
        }
        List<String> minutedata = MergeData.mergedMinuteDataSection(lists);
        return GetData.MinuteDataMap(minutedata);
    }
}
