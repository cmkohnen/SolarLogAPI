package me.meloni.SolarLogAPI.BasicGUI.Components.Graph;

import me.meloni.SolarLogAPI.BasicGUI.GetGraphData;

import java.text.ParseException;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This Class includes functions to get the content of a file.
 * @author ChaosMelone9
 * @since 0.0.1
 * @deprecated
 */
public class Graph {
    /**
     * @deprecated
     */
    public static DayView dayView(Date day, Map<Date,List<Integer>> data) throws ParseException {
        return new DayView(GetGraphData.getDayGraphData(day,data));
    }
    /**
     * @deprecated
     */
    public static MonthView monthView(YearMonth month, Map<Date,List<Integer>> data) throws ParseException {
        return new MonthView(GetGraphData.getMonthGraphData(month,data));
    }
}
