package TransformUtilities.DataConversion;

import Graph.DayView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GetGraphData {
    public static List<List<Double>> dayView(Date day, Map<Date, List<Integer>> data) throws ParseException {
        List<Date> timestamps = EntriesPerDay.entries(day);

        List<List<Double>> values = new ArrayList<>();

        for (Date timestamp : timestamps) {
            List<Double> currentdata = new ArrayList<>();
            int verbrauchw = data.get(timestamp).get(0);
            int verbrauchkwh = data.get(timestamp).get(1);
            int leistungw = data.get(timestamp).get(2);
            int ertragkwh = data.get(timestamp).get(3);
            int energieverbrauchw = data.get(timestamp).get(4);

            currentdata.add((double) verbrauchw);
            currentdata.add((double) verbrauchkwh);
            currentdata.add((double) leistungw);
            currentdata.add((double) ertragkwh);
            currentdata.add((double) energieverbrauchw);

            values.add(currentdata);
        }
        return values;
    }
}
