package TransformUtilities.DataConversion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntriesPerDay {
    public static List<Date> entries(Date day) throws ParseException {
        List<Date> entries = new ArrayList<>();

        int min = 0;
        int hour = 0;

        String date = new SimpleDateFormat("yyyyMMdd").format(day);

        for(int i = 0; i <288; i++){
            min = min + 5;
            if(min == 60) {
                hour++;
                min = 0;
            }
            DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            StringBuilder timestamp = new StringBuilder();
            timestamp.append(date);
            if(String.valueOf(hour).length() == 1) {
                timestamp.append("0").append(hour);
            } else {
                timestamp.append(hour);
            }
            if(String.valueOf(min).length() == 1) {
                timestamp.append("0").append(min);
            } else {
                timestamp.append(min);
            }
            timestamp.append("00");
            Date d = formatter.parse(timestamp.toString());
            entries.add(d);
        }
        return entries;
  }
}
