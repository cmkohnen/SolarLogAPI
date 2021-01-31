package me.meloni.SolarLogAPI.DatabaseInteraction;

import org.influxdb.InfluxDB;

public class InfluxDatabase {
    private final InfluxDB influxDB;
    private final String database;
    public InfluxDatabase(InfluxDB influxDB, String database) {
        this.influxDB = influxDB;
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }

    public InfluxDB getInfluxDB() {
        return influxDB;
    }
}
