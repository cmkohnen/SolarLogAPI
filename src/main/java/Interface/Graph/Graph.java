package Interface.Graph;

import Interface.Graph.DayView;
import TransformUtilities.DataConversion.GetGraphData;

import java.text.ParseException;
import java.util.*;

public class Graph {

    public static DayView dayView(Date day, Map<Date,List<Integer>> data) throws ParseException {
        return new DayView(GetGraphData.dayView(day,data));
    }
}
