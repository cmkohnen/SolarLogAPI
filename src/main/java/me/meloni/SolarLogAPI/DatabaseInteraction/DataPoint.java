package me.meloni.SolarLogAPI.DatabaseInteraction;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

/**
 * This Class is used to query data from an InfluxDB.
 * @author ChaosMelone9
 * @since 3.2.0
 */
@Measurement(name ="solar")
public class DataPoint {

    @Column(name = "time")
    public Instant time;

    @Column(name = "value1")
    public Integer value1;

    @Column(name = "value2")
    public Integer value2;

    @Column(name = "value3")
    public Integer value3;

    @Column(name = "value4")
    public Integer value4;

    @Column(name = "value5")
    public Integer value5;
}
