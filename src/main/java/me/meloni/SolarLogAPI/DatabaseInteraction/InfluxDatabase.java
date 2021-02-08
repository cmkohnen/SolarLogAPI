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

import org.influxdb.InfluxDB;

/**
 * This is an interface to store information about a connected {@link InfluxDB}
 * @author Christoph Kohnen
 * @since 3.2.0
 */
public class InfluxDatabase {
    /**
     * The {@link InfluxDB} to which should be connected
     */
    private final InfluxDB influxDB;
    /**
     * The desired database as string
     */
    private final String database;

    /**
     * Instantiate by providing an {@link InfluxDB} and database as string
     * @param influxDB The DB which should be connected to
     * @param database The desired database which should be used
     */
    public InfluxDatabase(InfluxDB influxDB, String database) {
        this.influxDB = influxDB;
        this.database = database;
    }

    /**
     * Get the desired database as String
     * @return The desired database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Get the {@link InfluxDB}
     * @return The {@link InfluxDB}
     */
    public InfluxDB getInfluxDB() {
        return influxDB;
    }
}
