package me.meloni.SolarLogAPI.DataConversion;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ConvertJson {
    @SuppressWarnings("unchecked")
    public static JSONObject convertMapToJson(Map<Date, List<Integer>> map) {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<Date, List<Integer>> entry : map.entrySet()) {
            Date date = entry.getKey();
            List<Integer> integers = entry.getValue();
            Instant timestamp = date.toInstant();
            JSONArray values = new JSONArray();
            values.addAll(integers);
            jsonObject.put(timestamp, values);
        }
        return jsonObject;
    }
}
