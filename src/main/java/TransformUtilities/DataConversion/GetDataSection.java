package TransformUtilities.DataConversion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * This Class includes functions to only use lines in files which contain the wanted information.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class GetDataSection {

    static int StartMinuteSection = 55;
    static int EndMinuteSection = 9271;


    public static List<String> MinuteData(List<String> data) {
        List<String> mindata = new ArrayList<>(Collections.singletonList(""));
        for(int i = StartMinuteSection; i < EndMinuteSection; i++) {
            mindata.add(data.get(i));
        }
        mindata.remove("");
        return mindata;
    }
}
