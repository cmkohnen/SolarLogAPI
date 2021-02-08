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
package me.meloni.SolarLogAPI.BasicGUI.Components.Graph;

import me.meloni.SolarLogAPI.SolarMap;

import java.time.Year;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class includes functions to get the content of a file.
 * @author Christoph Kohnen
 * @since 0.0.1
 * @deprecated
 */
public class Graph {
    /**
     * Get a {@link DayView} graph
     * @param day The day that should be visualized
     * @param data The data that should be used in {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format
     * @return A {@link DayView} graph
     * @deprecated
     */
    public static DayView dayView(Date day, Map<Date,List<Integer>> data) {
        return new DayView(new SolarMap(data), day);
    }

    /**
     * Get a {@link MonthView} graph
     * @param month The month the should be visualized
     * @param data The data that should be used in {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format
     * @return A {@link MonthView} graph
     * @deprecated
     */
    public static MonthView monthView(YearMonth month, Map<Date,List<Integer>> data) {
        return new MonthView(new SolarMap(data), month);
    }

    /**
     * Get a {@link YearView} graph
     * @param year The year that should be visualized
     * @param data The data that should be used in {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format
     * @return A {@link YearView}
     * @deprecated
     */
    public static YearView yearView(Year year, Map<Date,List<Integer>> data) {
        return new YearView(new SolarMap(data), year);
    }
}
