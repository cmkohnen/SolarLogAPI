package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.BasicGUI.Components.Graph.DayView;
import me.meloni.SolarLogAPI.BasicGUI.Components.Graph.MonthView;
import me.meloni.SolarLogAPI.BasicGUI.Components.Graph.YearView;
import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.text.ParseException;
import java.time.Year;
import java.time.YearMonth;
import java.util.Date;

public class Visualizer {
    public static void visualizeDay(SolarMap data, Date day) throws ParseException {
        JFrame f = new JFrame(day.toString());
        DayView component = new DayView(data, day);
        f.add(component);
        f.setSize(1000,600);
        f.setLocationRelativeTo(null);
        f.pack();
        f.setVisible(true);
    }
    public static void visualizeMonth(SolarMap data, YearMonth month) throws ParseException {
        JFrame f = new JFrame(month.toString());
        MonthView component = new MonthView(data, month);
        f.add(component);
        f.setSize(1000,600);
        f.setLocationRelativeTo(null);
        f.pack();
        f.setVisible(true);
    }
    public static void visualizeYear(SolarMap data, Year year) throws ParseException {
        JFrame f = new JFrame(year.toString());
        YearView component = new YearView(data, year);
        f.add(component);
        f.setSize(1000,600);
        f.setLocationRelativeTo(null);
        f.pack();
        f.setVisible(true);
    }
}
