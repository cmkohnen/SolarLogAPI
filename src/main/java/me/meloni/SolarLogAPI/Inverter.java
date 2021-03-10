package me.meloni.SolarLogAPI;

public class Inverter {
    public Integer strings;
    public String identifier;
    public Integer type;

    public Inverter(Integer strings, Integer type, String identifier) {
        this.strings = strings;
        this.identifier = identifier;
        this.type = type;
    }
}
