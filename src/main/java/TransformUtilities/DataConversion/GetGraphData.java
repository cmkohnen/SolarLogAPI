package TransformUtilities.DataConversion;

import Handling.Logger;

import java.text.ParseException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GetGraphData {
    public static List<List<Double>> dayView(Date day, Map<Date, List<Integer>> data) throws ParseException {
        List<Date> timestamps = Entries.entriesPerDay(day);

        List<List<Double>> values = new ArrayList<>();

        for (Date timestamp : timestamps) {
            List<Double> currentData = new ArrayList<>();
            int verbrauchw = data.get(timestamp).get(0);
            int verbrauchkwh = data.get(timestamp).get(1);
            int leistungw = data.get(timestamp).get(2);
            int ertragkwh = data.get(timestamp).get(3);
            int energieverbrauchw = data.get(timestamp).get(4);

            currentData.add((double) verbrauchw);
            currentData.add((double) verbrauchkwh);
            currentData.add((double) leistungw);
            currentData.add((double) ertragkwh);
            currentData.add((double) energieverbrauchw);

            values.add(currentData);
        }
        return values;
    }


    public static List<List<Double>> monthView(YearMonth month, Map<Date, List<Integer>> data) throws ParseException {
        List<Date> timestamps = Entries.entriesPerMonth(month);

        List<List<Double>> values = new ArrayList<>();

        for (Date timestamp : timestamps) {
                Logger.log("Importing from " + timestamp);
                List<Double> currentData = new ArrayList<>();
                int Erzeugungkwh = 0;
                int Verbrauchkwh = 0;
                int Eigenverbrauchkwh = 0;

                List<Date> days = Entries.entriesPerDay(timestamp);
                for (Date date : days) {
                    if (data.containsKey(date)) {
                        int verbrauchw = data.get(date).get(0);
                        int leistungw = data.get(date).get(2);
                        int energieverbrauchw = data.get(date).get(4);

                        Erzeugungkwh = Erzeugungkwh + leistungw;
                        Verbrauchkwh = Verbrauchkwh + verbrauchw;
                        Eigenverbrauchkwh = Eigenverbrauchkwh + energieverbrauchw;
                    }
                }

                currentData.add((double) Erzeugungkwh);
                currentData.add((double) Verbrauchkwh);
                currentData.add((double) Eigenverbrauchkwh);

                values.add(currentData);
        }
        return values;
    }



}
