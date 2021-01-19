package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.DataConversion.Entries;

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
            try {
                List<Double> currentData = new ArrayList<>();
                int consPac = data.get(timestamp).get(0);
                int consYieldDay = data.get(timestamp).get(1);
                int Pac = data.get(timestamp).get(2);
                int yieldDay = data.get(timestamp).get(3);
                int ownConsumption = data.get(timestamp).get(4);

                currentData.add((double) consPac);
                currentData.add((double) consYieldDay);
                currentData.add((double) Pac);
                currentData.add((double) yieldDay);
                currentData.add((double) ownConsumption);
                values.add(currentData);
            } catch (NullPointerException ignored) {
                List<Double> currentData = new ArrayList<>();
                currentData.add((double) 0);
                currentData.add((double) 0);
                currentData.add((double) 0);
                currentData.add((double) 0);
                currentData.add((double) 0);
                values.add(currentData);
            }
        }
        return values;
    }

    public static List<List<Double>> getMonthGraphData(YearMonth month, Map<Date, List<Integer>> data) throws ParseException {
        List<Date> timestamps = Entries.getEntriesPerMonth(month);

        List<List<Double>> values = new ArrayList<>();

        for (Date timestamp : timestamps) {
                List<Double> currentData = new ArrayList<>();
                int yieldDay = 0;
                int consYieldDay = 0;
                int ownConsumption1 = 0;

                List<Date> days = Entries.getEntriesPerDay(timestamp);
                for (Date date : days) {
                    if (data.containsKey(date)) {
                        int consPac = data.get(date).get(0);
                        int Pac = data.get(date).get(2);
                        int ownConsumption2 = data.get(date).get(4);

                        yieldDay = yieldDay + Pac;
                        consYieldDay = consYieldDay + consPac;
                        ownConsumption1 = ownConsumption1 + ownConsumption2;
                    }
                }
                currentData.add((double) yieldDay);
                currentData.add((double) consYieldDay);
                currentData.add((double) ownConsumption1);

                values.add(currentData);
        }
        return values;
    }

    public static List<List<Double>> getYearGraphData(Year year, Map<Date, List<Integer>> data) throws ParseException {
        List<List<Double>> values = new ArrayList<>();

        List<YearMonth> yearMonths = Entries.getEntriesPerYear(year);
        for (YearMonth yearMonth : yearMonths) {
            List<List<Double>> monthData = getMonthGraphData(yearMonth, data);
            double yieldMonth = 0;
            double consYieldMonth = 0;
            double ownConsumption = 0;
            for (List<Double> monthDatum : monthData) {
                yieldMonth = yieldMonth + monthDatum.get(0);
                consYieldMonth = consYieldMonth + monthDatum.get(1);
                ownConsumption = ownConsumption + monthDatum.get(2);
            }
            List<Double> currentData = new ArrayList<>();
            currentData.add(yieldMonth);
            currentData.add(consYieldMonth);
            currentData.add(ownConsumption);

            values.add(currentData);
        }
        return values;
    }
}
