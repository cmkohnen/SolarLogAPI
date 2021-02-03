package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.DatabaseInteraction.InfluxDatabase;
import me.meloni.SolarLogAPI.DatabaseInteraction.SQLDatabase;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;

import javax.swing.*;

/**
 * This Class provides an interactive way to get an {@link InfluxDB} database.
 * @author ChaosMelone9
 * @since 3.0.5
 */
public class GetDatabase {
    public static InfluxDatabase influxDatabase() {
        String server = JOptionPane.showInputDialog("Server?");
        String username = JOptionPane.showInputDialog("User?");
        String password = JOptionPane.showInputDialog("Password?");
        try {
            InfluxDB db = InfluxDBFactory.connect(server, username, password);
            String database = JOptionPane.showInputDialog("Database?");
            db.setDatabase(database);
            return new InfluxDatabase(db, database);
        } catch (IllegalArgumentException e) {
            throw new NullPointerException("No such database");
        }
    }

    public static SQLDatabase SQLDatabase() {
        String host = JOptionPane.showInputDialog("Server?");
        String user = JOptionPane.showInputDialog("User?");
        String password = JOptionPane.showInputDialog("Password?");
        String database = JOptionPane.showInputDialog("Database?");
        String table = JOptionPane.showInputDialog("Table?");
        try {
            return new SQLDatabase(host,user,password,database,table);
        } catch (NullPointerException ignored) {
            return null;
        }
    }
}
