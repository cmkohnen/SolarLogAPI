package me.meloni.SolarLogAPI.DatabaseInteraction;

import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.Handling.Translation;
import me.meloni.SolarLogAPI.SolarMap;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This Class provides functionality to read and write data from or to an {@link InfluxDB} database.
 * @author ChaosMelone9
 * @since 3.0.1
 */
public class InfluxDbInteraction {
    int limit = 125000;
    String database;
    BatchPoints batchPoints;
    final InfluxDB db;
    public InfluxDbInteraction(String server, String username, String password) {
        this.db = InfluxDBFactory.connect(server, username, password);
    }

    public InfluxDbInteraction(InfluxDB dataBase) {
        this.db = dataBase;
    }

    public void setDatabase(String database) {
        this.database = database;
        db.setDatabase(database);
    }

    public void write(SolarMap solarMap) throws NullPointerException {
        this.batchPoints = batchPoints();
        solarMap.getAsMap().forEach((date, values) -> {
               Point point = Point.measurement("solar")
                       .time(date.getTime(), TimeUnit.MILLISECONDS)
                       .addField("value1",values.get(0))
                       .addField("value2",values.get(1))
                       .addField("value3",values.get(2))
                       .addField("value4",values.get(3))
                       .addField("value5",values.get(4))
                       .build();
               this.batchPoints.point(point);
               if(batchPoints.getPoints().size() >= limit) {
                   Logger.log(Logger.INFO_LEVEL_3 + String.format(Translation.get("influx_write_batchpoints"), limit));
                   db.write(batchPoints);
                   this.batchPoints = batchPoints();
               }
               });
        Logger.log(Logger.INFO_LEVEL_3 + String.format(Translation.get("influx_write_batchpoints"), batchPoints.getPoints().size()));
        db.write(batchPoints);
        Logger.log(Logger.INFO_LEVEL_3 + Translation.get("done"));
    }

    public Map<Date, List<Integer>> read() {
        Logger.log(Logger.INFO_LEVEL_2 + String.format(Translation.get("influx_query"), database));
        QueryResult queryResult = db.query(new Query("SELECT value1,value2,value3,value4,value5 FROM " + database));
        Logger.log(Logger.INFO_LEVEL_3 + Translation.get("done"));

        Logger.log(Logger.INFO_LEVEL_3 + Translation.get("influx_mapping"));
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        List<DataPoint> dataPointList = resultMapper.toPOJO(queryResult, DataPoint.class);
        Logger.log(Logger.INFO_LEVEL_3 + Translation.get("done"));

        Map<Date, List<Integer>> data = new HashMap<>();

        for (DataPoint dataPoint : dataPointList) {
            List<Integer> values = new ArrayList<>();
            values.add(dataPoint.value1);
            values.add(dataPoint.value2);
            values.add(dataPoint.value3);
            values.add(dataPoint.value4);
            values.add(dataPoint.value5);

            Date date = Date.from(dataPoint.time);

            data.putIfAbsent(date, values);
        }
        return data;
    }

    private BatchPoints batchPoints() {
        return BatchPoints
                .database(this.database)
                .tag("async","true")
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
    }

    public void setBatchPointLimit(int limit) {
        this.limit = limit;
    }

    public void close() {
        db.close();
    }
}
