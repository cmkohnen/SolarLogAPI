package Graph;

import TransformUtilities.DataConversion.GetGraphData;

import javax.swing.*;
import java.text.ParseException;
import java.util.*;

public class Graph {

    public static DayView dayView(Date day, Map<Date,List<Integer>> data) throws ParseException {
        return new DayView(GetGraphData.dayView(day,data));
    }

    public static JFrame simplewindow(JComponent jComponent) {
        JFrame f = new JFrame();
        f.add(jComponent);

        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(400,800);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        return f;
    }
}
