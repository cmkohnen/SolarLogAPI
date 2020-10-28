package Handling;

import FileInteraction.GetFile;
import FileInteraction.ReadFiles.GetFileContent;
import FileInteraction.ReadFiles.ReadFileObject;
import Interface.Graph.DayView;
import Interface.Graph.Graph;
import Interface.SimpleFrame;
import Interface.DatePicker;
import TransformUtilities.DataConversion.GetData;
import TransformUtilities.DataConversion.GetDataMap;
import TransformUtilities.DataConversion.GetDataSection;

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
    public static void main(String[] args) throws IOException, ParseException {
        Logger.log("API Initiated via Runtime with args " + Arrays.toString(args));


        Map<Date, List<Integer>> data = GetDataMap.DataFromFiles(GetFile.ChosenValidFilesInDirectory());

        DatePicker picker = new DatePicker();
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





     //System.out.println(GetData.MinuteDataMap(GetFile.ValidChosenDataFile()));
     //System.out.println(GetDataSection.MinuteData(GetFileContent.FileContentAsList(GetFile.Path(GetFile.ValidChosenDataFile()))));


    }

}
