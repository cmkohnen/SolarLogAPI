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

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.influxdb.InfluxDB;

import java.time.Instant;

/**
 * This class is used to query data from an {@link InfluxDB}. It is used to map over a query.
 * @author Christoph Kohnen
 * @since 3.2.0
 */
@Measurement(name ="solar")
public class InfluxDataPoint {

    /**
     * The key used in {@link InfluxDB}
     */
    @Column(name = "time")
    public Instant time;

    /**
     * The first value in a set
     */
    @Column(name = "value1")
    public Integer value1;

    /**
     * The second value in a set
     */
    @Column(name = "value2")
    public Integer value2;

    /**
     * The third value in a set
     */
    @Column(name = "value3")
    public Integer value3;

    /**
     * The fourth value in a set
     */
    @Column(name = "value4")
    public Integer value4;

    /**
     * The fifth value in a set
     */
    @Column(name = "value5")
    public Integer value5;
}
