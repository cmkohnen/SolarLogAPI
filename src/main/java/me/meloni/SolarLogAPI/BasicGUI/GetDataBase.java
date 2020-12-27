package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.DatabaseInteraction.Database;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;

import javax.swing.*;

/**
 * This Class provides an interactive way to get an {@link InfluxDB} database.
 * @author ChaosMelone9
 * @since 3.0.5
 */
public class GetDataBase {
    public static Database database() {
        String server = JOptionPane.showInputDialog("Server");
        String username = JOptionPane.showInputDialog("User");
        String password = JOptionPane.showInputDialog("Passwd");
        try {
            InfluxDB db = InfluxDBFactory.connect(server, username, password);
            String database = JOptionPane.showInputDialog("Database");
            db.setDatabase(database);
            return new Database(db, database);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null,"Did not work");
            throw new NullPointerException("No such database");
        }
    }
}
