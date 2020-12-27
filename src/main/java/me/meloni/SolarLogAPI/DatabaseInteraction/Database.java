package me.meloni.SolarLogAPI.DatabaseInteraction;

import org.influxdb.InfluxDB;

public class Database {
    private final InfluxDB influxDB;
    private final String database;
    public Database(InfluxDB influxDB, String database) {
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
