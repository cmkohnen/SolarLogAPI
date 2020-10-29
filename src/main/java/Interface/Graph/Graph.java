package Interface.Graph;

import TransformUtilities.DataConversion.GetGraphData;

import java.text.ParseException;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Graph {

    public static DayView dayView(Date day, Map<Date,List<Integer>> data) throws ParseException {
        return new DayView(GetGraphData.dayView(day,data));
    }


    public static MonthView monthView(YearMonth month, Map<Date,List<Integer>> data) throws ParseException {
        return new MonthView(GetGraphData.monthView(month,data));
    }


}
