/*
Copyright 2020 - 2021 Christoph Kohnen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.DataConversion.Entries;

import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class includes functions to get values to display in graph.
 * @author Christoph Kohnen
 * @since 0.1.0
 */
public class GetGraphData {
    /**
     * Get the data for an graph to visualize data on daily base
     * @param day The day for which data should be gotten for
     * @param data A map containing all data
     * @return The data for the specified day
     */
    public static List<List<Double>> getDayGraphData(Date day, Map<Date, List<Integer>> data) {
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

    /**
     * Get the data for an graph to visualize data on monthly base
     * @param month The month for which data should be gotten for
     * @param data A map containing all data
     * @return The data for the specified month
     */
    public static List<List<Double>> getMonthGraphData(YearMonth month, Map<Date, List<Integer>> data) {
        List<Date> timestamps = Entries.getDaysPerMonth(month);

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

    /**
     * Get the data for an graph to visualize data on yearly base
     * @param year The year for which data should be gotten for
     * @param data A map containing all data
     * @return The data for the specified year
     */
    public static List<List<Double>> getYearGraphData(Year year, Map<Date, List<Integer>> data) {
        List<List<Double>> values = new ArrayList<>();

        List<YearMonth> yearMonths = Entries.getMonthsPerYear(year);
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
