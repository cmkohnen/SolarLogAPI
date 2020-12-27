package me.meloni.SolarLogAPI.SolarLogInteraction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetJsonFromSolarLog {
    public static String getFromSolarLogInterface(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setDoOutput(true);

        String jsonInputString = "{\"801\":{\"170\":null}}";
        OutputStream outputStream = httpURLConnection.getOutputStream();
        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
        outputStream.write(input, 0, input.length);

        BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
        httpURLConnection.disconnect();
        return response.toString();
    }
}
