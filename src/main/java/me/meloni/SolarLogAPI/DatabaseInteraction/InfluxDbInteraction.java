/*
Copyright 2020 - 2021 Christoph Kohnen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package me.meloni.SolarLogAPI.DatabaseInteraction;

import me.meloni.SolarLogAPI.Handling.Logger;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

import java.util.*;

/**
 * This class provides functionality to read and write data from or to an {@link InfluxDB} database.
 * @author Christoph Kohnen
 * @since 3.0.1
 */
public class InfluxDbInteraction {
    /**
     * The limit of {@link BatchPoints} used to write in parallel to the database
     */
    int limit = 125000;
    /**
     * The database which is used
     */
    String database;
    /**
     * A set of temporal batchpoints used to write to the database
     */
    BatchPoints batchPoints;
    /**
     * The {@link InfluxDB} which is interacted with
     */
    final InfluxDB db;

    /**
     * Instantiate with host and credentials
     * @param server The hostname of the server
     * @param username Your username
     * @param password Your password
     */
    public InfluxDbInteraction(String server, String username, String password) {
        this.db = InfluxDBFactory.connect(server, username, password);
    }

    /**
     * Instantiate with an already connected to {@link InfluxDB}
     * @param dataBase The {@link InfluxDB} you are connected to
     */
    public InfluxDbInteraction(InfluxDB dataBase) {
        this.db = dataBase;
    }

    /**
     * set the desired database
     * @param database the database which should be used
     */
    public void setDatabase(String database) {
        this.database = database;
        db.setDatabase(database);
    }

    /*
     * Write a {@link SolarMap} to the used {@link InfluxDB}
     * @param solarMap the {@link SolarMap} which should be written
     * @throws NullPointerException If something goes wrong with the database connection
     *
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
                   Logger.log(Logger.INFO_LEVEL_3 + String.format("Writing set of BatchPoints (%s)", limit));
                   db.write(batchPoints);
                   this.batchPoints = batchPoints();
               }
               });
        Logger.log(Logger.INFO_LEVEL_3 + String.format("Writing set of BatchPoints (%s)", batchPoints.getPoints().size()));
        db.write(batchPoints);
        Logger.log(Logger.INFO_LEVEL_3 + "done.");
    }

     */

    /**
      * Query the set {@link InfluxDB} and convert to a {@link Map}
      * @return a Map using the {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format
     */
    public Map<Date, List<Integer>> read() {
        Logger.log(Logger.INFO_LEVEL_2 + String.format("Querying data from database %s...", database));
        QueryResult queryResult = db.query(new Query("SELECT value1,value2,value3,value4,value5 FROM " + database));
        Logger.log(Logger.INFO_LEVEL_3 + "done.");

        Logger.log(Logger.INFO_LEVEL_3 + "Mapping results...");
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        List<InfluxDataPoint> influxDataPointList = resultMapper.toPOJO(queryResult, InfluxDataPoint.class);
        Logger.log(Logger.INFO_LEVEL_3 + "done.");

        Map<Date, List<Integer>> data = new HashMap<>();

        for (InfluxDataPoint influxDataPoint : influxDataPointList) {
            List<Integer> values = new ArrayList<>();
            values.add(influxDataPoint.value1);
            values.add(influxDataPoint.value2);
            values.add(influxDataPoint.value3);
            values.add(influxDataPoint.value4);
            values.add(influxDataPoint.value5);

            Date date = Date.from(influxDataPoint.time);

            data.putIfAbsent(date, values);
        }
        return data;
    }

    /**
     * Get a set of empty configured {@link BatchPoints}
     * @return A set of {@link BatchPoints}
     */
    private BatchPoints batchPoints() {
        return BatchPoints
                .database(this.database)
                .tag("async","true")
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
    }

    /**
     * Sets the limit of {@link BatchPoints} written to a {@link InfluxDB} in parallel
     * @param limit the desired limit
     */
    public void setBatchPointLimit(int limit) {
        this.limit = limit;
    }

    /**
     * Closes the connection with the {@link InfluxDB}
     */
    public void close() {
        db.close();
    }
}
