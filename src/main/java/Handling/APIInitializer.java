package Handling;

import FileInteraction.GetFile;
import FileInteraction.ReadFiles.ReadFileObject;
import Interface.DatePicker;
import Interface.Graph.DayView;
import Interface.Graph.Graph;
import Interface.SimpleFrame;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This Class is called when the jar package is executed.
 * @author ChaosMelone9
 * @since 0.0.0
 */
public class APIInitializer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Logger.log("API Initiated via Runtime with args " + Arrays.toString(args));

        Object o = ReadFileObject.fileObject(GetFile.ChosenReadLocation());

        DatePicker picker = new DatePicker();
        picker.addDateChangeListener(dateChangeEvent -> {
            Date date = Date.from(dateChangeEvent.getNewDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            try {
                DayView graph = Graph.dayView(date, (Map<Date, List<Integer>>) o);
                graph.setMouseGUIVisible(false);
                new SimpleFrame(graph);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        new SimpleFrame(picker);


        System.out.println(Math.ceil(2.2));
    }

}
