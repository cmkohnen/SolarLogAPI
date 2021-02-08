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

import me.meloni.SolarLogAPI.BasicGUI.Components.Graph.DayView;
import me.meloni.SolarLogAPI.BasicGUI.Components.Graph.MonthView;
import me.meloni.SolarLogAPI.BasicGUI.Components.Graph.YearView;
import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.time.Year;
import java.time.YearMonth;
import java.util.Date;

/**
 * This class includes functions to visualize one day/month/year
 * @author Christoph Kohnen
 * @since 1.0.0
 */
public class Visualizer {
    /**
     * Visualize one day
     * @param data A {@link SolarMap} containing all data
     * @param day The day that should be visualized
     */
    public static void visualizeDay(SolarMap data, Date day) {
        JFrame f = new JFrame(day.toString());
        DayView component = new DayView(data, day);
        f.add(component);
        f.setSize(1000,600);
        f.setLocationRelativeTo(null);
        f.pack();
        f.setVisible(true);
    }

    /**
     * Visualize one month
     * @param data A {@link SolarMap} containing all data
     * @param month The month that should be visualized
     */
    public static void visualizeMonth(SolarMap data, YearMonth month) {
        JFrame f = new JFrame(month.toString());
        MonthView component = new MonthView(data, month);
        f.add(component);
        f.setSize(1000,600);
        f.setLocationRelativeTo(null);
        f.pack();
        f.setVisible(true);
    }

    /**
     * Visualize one year
     * @param data A {@link SolarMap} containing all data
     * @param year The year that should be visualized
     */
    public static void visualizeYear(SolarMap data, Year year) {
        JFrame f = new JFrame(year.toString());
        YearView component = new YearView(data, year);
        f.add(component);
        f.setSize(1000,600);
        f.setLocationRelativeTo(null);
        f.pack();
        f.setVisible(true);
    }
}
