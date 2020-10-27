package TransformUtilities.DataConversion;

import Handling.Logger;

import java.util.ArrayList;
import java.util.List;

public class MergeData {
    public static List<String> mergedMinuteDataSection(List<List<String>> lists) {
        List<String> data = new ArrayList<>();
        Logger.log("Merging Data, this may take a while...");
        lists.forEach(list-> list.forEach(s -> {
            if(!data.contains(s)){
                data.add(s);
            }
        }));
        Logger.log("Merging done!");
        return data;
    }
}
