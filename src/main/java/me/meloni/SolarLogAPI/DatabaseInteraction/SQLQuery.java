package me.meloni.SolarLogAPI.DatabaseInteraction;

import me.meloni.SolarLogAPI.FileInteraction.WorkingDirectory;
import me.meloni.SolarLogAPI.SolarMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This Class provides functionality to write an SQL query to a file
 * @author ChaosMelone9
 * @since 3.10.1
 */
public class SQLQuery {
    private static final String BASE_WRITE_QUERY = "INSERT INTO `%s`.`%s` (`%s`, `%s`, `%s`, `%s`, `%s`, `%s`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s');";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static File getWriteQuery(String database, String table, String key, String value1, String value2, String value3, String value4, String value5, SolarMap solarMap) throws IOException {
        String filename = String.format("Query-%s.txt", solarMap.getId());
        File file = new File(WorkingDirectory.getDirectory(), filename);
        if (!file.createNewFile()) {
            throw new FileAlreadyExistsException("File already exists!");
        }
        FileWriter writer = new FileWriter(file);
        for (String writeQueryLine : writeQueryLines(database, table, key, value1, value2, value3, value4, value5, solarMap)) {
            writer.write(writeQueryLine + "\n");
        }
        writer.close();
        return file;
    }

    private static List<String> writeQueryLines(String database, String table, String key, String value1, String value2, String value3, String value4, String value5, SolarMap solarMap) {
        List<String> lines = new ArrayList<>();
        lines.add(String.format("USE %S;", database));
        solarMap.forEach((date, values) -> lines.add(String.format(BASE_WRITE_QUERY, database, table, key, value1, value2, value3, value4, value5, getDateAsString(date), values.get(0), values.get(1), values.get(2), values.get(3), values.get(4))));
        return lines;
    }

    private static String getDateAsString(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }
}
