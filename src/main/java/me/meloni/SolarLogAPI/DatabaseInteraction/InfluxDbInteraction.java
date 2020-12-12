package me.meloni.SolarLogAPI.DatabaseInteraction;

import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.Handling.SolarMap;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This Class provides functionality to read and write data from or to an {@link InfluxDB} database.
 * @author ChaosMelone9
 * @since 3.0.1
 */
public class InfluxDbInteraction {
    InfluxDB db;
    public InfluxDbInteraction(String server, String username, String password) {
        this.db = InfluxDBFactory.connect(server, username, password);
    }

    public InfluxDbInteraction(InfluxDB dataBase) {
        this.db = dataBase;
    }

    public void setDatabase(String database) {
        db.setDatabase(database);
    }

    public void write(SolarMap solarMap) {
        BatchPoints batchPoints = BatchPoints
                .database("solar")
                .tag("async","true")
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
        solarMap.getAsMap().forEach((date, values) -> {
               Point point = Point.measurement("solar")
                       .time(date.getTime(), TimeUnit.MILLISECONDS)
                       .addField("value1",values.get(0))
                       .addField("value2",values.get(1))
                       .addField("value3",values.get(2))
                       .addField("value4",values.get(3))
                       .addField("value5",values.get(4))
                       .build();
               batchPoints.point(point);
               });
        Logger.log("Writing ...");
        db.write(batchPoints);


    }

    public Map<Date, List<Integer>> read() throws ParseException {
        QueryResult queryResult = db.query(new Query("SELECT value1,value2,value3,value4,value5 FROM data"));
        List<List<Object>> values = queryResult.getResults().get(0).getSeries().get(0).getValues();

        String dateformat = "yyyy-MM-ddHH:mm:ss";

        Map<Date, List<Integer>> data = new HashMap<>();

        for (List<Object> actualValues : values) {
            List<Integer> valuesFor = new ArrayList<>();
            valuesFor.add(((Double) actualValues.get(1)).intValue());
            valuesFor.add(((Double) actualValues.get(2)).intValue());
            valuesFor.add(((Double) actualValues.get(3)).intValue());
            valuesFor.add(((Double) actualValues.get(4)).intValue());
            valuesFor.add(((Double) actualValues.get(5)).intValue());

            String part1 = actualValues.get(0).toString().substring(0,10);
            String part2 = actualValues.get(0).toString().substring(11,19);


            DateFormat formatter = new SimpleDateFormat(dateformat);
            Date d = formatter.parse(part1 + part2);

            data.put(d,valuesFor);
        }
        return data;
    }







    public void close() {
        db.close();
    }
}
