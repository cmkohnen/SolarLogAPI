package TransformUtilities.DataConversion;

import java.util.ArrayList;
import java.util.List;

public class MergeData {
    public static List<String> mergedMinuteDataSection(List<List<String>> lists) {
        List<String> data = new ArrayList<>();
        lists.forEach(list-> list.forEach(s -> {
            if(!data.contains(s)){
                data.add(s);
            }
        }));
        return data;
    }
}
