package me.meloni.SolarLogAPI;

public class Inverter {
    public String type;
    public String identifier;
    public int strings;
    public int function;
    public int functionType;
    public boolean temperature;

    public Inverter(String type, String identifier, int strings, int function, int functionType, boolean temperature) {
        this.type = type;
        this.identifier = identifier;
        this.strings = strings;
        this.function = function;
        this.functionType = functionType;
        this.temperature = temperature;
    }
}
