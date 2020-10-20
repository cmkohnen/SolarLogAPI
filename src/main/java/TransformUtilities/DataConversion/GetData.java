package TransformUtilities.DataConversion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * This Class includes functions to extract data from lines found in the backup files.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class GetData  {
    /*
    Positions of values in List<String> format
        Verbrauch W: 6
        Verbrauch kWh: 10
        Leistung W: 17
        Ertrag KWh: 23
        Eigenverbrauch W: 44

     */
     static final String DATEFORMAT = "dd.MM.yy HH:mm:ss";

    public static Map<Date, List<Integer>> MinuteDataMap(List<String> MinuteData) throws ParseException {
        Map<Date, List<Integer>> Data = new HashMap<>();

            //MinuteData.forEach(item->{
        for (String item : MinuteData) {

                String[] str = item.split(";");
                List<String> values = Arrays.asList(str);

                //intitialize variables
                int verbrauchw;
                int verbrauchkwh;
                int leistungw;
                int ertragkwh;
                int energieverbrauchw;

                List<Integer> valueseach = new ArrayList<>();

                //String timestamp = DateConverter.Timestamp(values.get(2));
                DateFormat formatter = new SimpleDateFormat(DATEFORMAT);
                Date d = formatter.parse(values.get(2));


                //getting values
                verbrauchw = Integer.parseInt(values.get(6));
                verbrauchkwh = Integer.parseInt(values.get(10));
                leistungw = Integer.parseInt(values.get(17));
                ertragkwh = Integer.parseInt(values.get(23));
                energieverbrauchw = Integer.parseInt(values.get(44));


                //writing values to List
                valueseach.add(verbrauchw);
                valueseach.add(verbrauchkwh);
                valueseach.add(leistungw);
                valueseach.add(ertragkwh);
                valueseach.add(energieverbrauchw);


                //Writing List to Map
                Data.put(d, valueseach);
            }//);
        return Data;
    }
}
