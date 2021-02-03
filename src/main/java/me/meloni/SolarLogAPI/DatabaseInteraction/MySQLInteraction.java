package me.meloni.SolarLogAPI.DatabaseInteraction;

import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.SolarMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 * This Class provides functionality to read and write data from or to an mySQL database.
 * @author ChaosMelone9
 * @since 3.10.0
 */
public class MySQLInteraction {
    private static final String BASE_WRITE_QUERY = "INSERT INTO `%s`.`%s` (`Timestamp`, `Value1`, `Value2`, `Value3`, `Value4`, `Value5`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s');";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static void write(String host, String user, String password, String database, String table, SolarMap map) throws SQLException {
        Connection conn = DriverManager.getConnection(host,user,password);
        Statement st = conn.createStatement();

        map.forEach((date, objects) -> {
            String timestamp = new SimpleDateFormat(DATE_FORMAT).format(date);
            Logger.log(String.format("Writing point %s", timestamp));
            try {
                String query = String.format(BASE_WRITE_QUERY, database, table, timestamp, objects.get(0), objects.get(1), objects.get(2), objects.get(3), objects.get(4));
                st.executeUpdate(query);
            } catch (SQLException ignored) {
                Logger.warn(String.format("Unable to write %s", timestamp));
            }
        });
        conn.close();
    }
}
