package Handling;

import FileInteraction.GetFile;
import FileInteraction.ReadFiles.ReadFileObject;
import Interface.DatePicker;
import Interface.Graph.DayView;
import Interface.Graph.Graph;
import Interface.SimpleFrame;

import java.io.IOException;
import java.text.ParseException;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This Class is called when the jar package is executed.
 * @author ChaosMelone9
 * @since 0.0.0
 */
public class APIInitializer {
    public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException {
        Logger.log("API Initiated via Runtime with args " + Arrays.toString(args));

        YearMonth yearMonth = YearMonth.of(2020,8);
        Map<Date, List<Integer>> data = (Map<Date, List<Integer>>) ReadFileObject.fileObject(GetFile.ChosenReadLocation());
        //MonthView graph = Graph.monthView(yearMonth,data);

        //new SimpleFrame(graph);
        //GetGraphData.monthView(yearMonth,data);


        DatePicker picker = new DatePicker(data);
        picker.addDateChangeListener(dateChangeEvent -> {
            Date date = Date.from(dateChangeEvent.getNewDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            try {
                DayView graph = Graph.dayView(date, data);
                new SimpleFrame(graph);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        new SimpleFrame(picker);





    }

}
