package me.meloni.SolarLogAPI.DataConversion;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class GetValuesFromJson {
    private static final String DATEFORMAT = "dd.MM.yy HH:mm:ss";

    public static Map<String, String> getMapFromJsonString(String jsonInput) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        Object object = jsonParser.parse(jsonInput);
        JSONObject jsonObject1 = (JSONObject) object;
        JSONObject jsonObject2 = (JSONObject) jsonObject1.get("801");
        JSONObject jsonObject3 = (JSONObject) jsonObject2.get("170");

        Map<String, String> data = new HashMap<>();

        data.put("lastUpdateTime", jsonObject3.get("100").toString());
        data.put("Pac", jsonObject3.get("101").toString());
        data.put("Pdc", jsonObject3.get("102").toString());
        data.put("Uac", jsonObject3.get("103").toString());
        data.put("DC_voltage", jsonObject3.get("104").toString());
        data.put("yieldDay", jsonObject3.get("105").toString());
        data.put("yieldYesterday", jsonObject3.get("106").toString());
        data.put("yieldMonth", jsonObject3.get("107").toString());
        data.put("yieldYear", jsonObject3.get("108").toString());
        data.put("yieldTotal", jsonObject3.get("109").toString());
        data.put("consPac", jsonObject3.get("110").toString());
        data.put("consYieldDay", jsonObject3.get("111").toString());
        data.put("consYieldYesterday", jsonObject3.get("112").toString());
        data.put("consYieldMonth", jsonObject3.get("113").toString());
        data.put("consYieldYear", jsonObject3.get("114").toString());
        data.put("consYieldTotal", jsonObject3.get("115").toString());
        data.put("totalPower", jsonObject3.get("116").toString());

        return data;
    }

    public static Map<Date, List<Integer>> getAsMap(String jsonInput) throws org.json.simple.parser.ParseException, java.text.ParseException {
        Map< String, String> data = getMapFromJsonString(jsonInput);

        DateFormat formatter = new SimpleDateFormat(DATEFORMAT);
        Date d = formatter.parse(data.get("lastUpdateTime"));

        List<Integer> values = new ArrayList<>();
        int Pac = Integer.parseInt(data.get("Pac"));
        int consPac = Integer.parseInt(data.get("consPac"));

        values.add(consPac);
        values.add(Integer.parseInt(data.get("consYieldDay")));
        values.add(Pac);
        values.add(Integer.parseInt(data.get("yieldDay")));
        values.add(Math.min(Pac, consPac));

        Map<Date, List<Integer>> returnValue = new HashMap<>();
        returnValue.put(d, values);
        return returnValue;
    }
}
