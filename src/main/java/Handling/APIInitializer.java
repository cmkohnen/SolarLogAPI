package Handling;

import FileInteraction.GetFile;
import FileInteraction.ReadFiles.GetFileContent;
import Graph.Graph;
import Graph.DayView;
import TransformUtilities.DataConversion.*;

import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This Class is called when the jar package is executed.
 * @author ChaosMelone9
 * @since 0.0.0
 */
public class APIInitializer {
    public static void main(String[] args) throws IOException, ParseException {
        //Do Stuff

        DateFormat formatter = new SimpleDateFormat("yy-MM-dd");
        Date day = formatter.parse("20-07-14");
        DayView dayView = Graph.dayView(day, GetData.MinuteDataMap(GetDataSection.MinuteData(GetFileContent.FileContentAsList(GetFile.Path(GetFile.ValidChosenDataFile())))));
        dayView.setRow3Visible(false);
        dayView.setBackgroundColor(Color.BLACK);
        dayView.setAxisColor(Color.WHITE);
        dayView.setLabelColor(Color.WHITE);


        Graph.simplewindow(dayView);
    }

}
