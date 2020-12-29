package me.meloni.SolarLogAPI.DataConversion;

import java.text.ParseException;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This Class includes functions to get values to display in graph.
 * @author ChaosMelone9
 * @since 0.1.0
 */
public class GetGraphData {
    public static List<List<Double>> getDayGraphData(Date day, Map<Date, List<Integer>> data) throws ParseException {
        List<Date> timestamps = Entries.getEntriesPerDay(day);

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

    public static List<List<Double>> getMonthGraphData(YearMonth month, Map<Date, List<Integer>> data) throws ParseException {
        List<Date> timestamps = Entries.getEntriesPerMonth(month);

        List<List<Double>> values = new ArrayList<>();

        for (Date timestamp : timestamps) {
                List<Double> currentData = new ArrayList<>();
                int Erzeugungkwh = 0;
                int Verbrauchkwh = 0;
                int Eigenverbrauchkwh = 0;

                List<Date> days = Entries.getEntriesPerDay(timestamp);
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

    public static List<List<Double>> getYearGraphData(Year year, Map<Date, List<Integer>> data) throws ParseException {
        List<List<Double>> values = new ArrayList<>();

        List<YearMonth> yearMonths = Entries.getEntriesPerYear(year);
        for (YearMonth yearMonth : yearMonths) {
            List<List<Double>> monthData = getMonthGraphData(yearMonth, data);
            double Erzeugungkwh = 0;
            double Verbrauchkwh = 0;
            double Eigenverbrauchkwh = 0;
            for (List<Double> monthDatum : monthData) {
                Erzeugungkwh = Erzeugungkwh + monthDatum.get(0);
                Verbrauchkwh = Verbrauchkwh + monthDatum.get(1);
                Eigenverbrauchkwh = Eigenverbrauchkwh + monthDatum.get(2);
            }
            List<Double> currentData = new ArrayList<>();
            currentData.add(Erzeugungkwh);
            currentData.add(Verbrauchkwh);
            currentData.add(Eigenverbrauchkwh);

            values.add(currentData);
        }
        return values;
    }
}
