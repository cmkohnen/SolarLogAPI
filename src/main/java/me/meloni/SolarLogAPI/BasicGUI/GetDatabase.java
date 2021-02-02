package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.DatabaseInteraction.InfluxDatabase;
import me.meloni.SolarLogAPI.DatabaseInteraction.SQLDatabase;
import me.meloni.SolarLogAPI.Handling.Translation;
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
        String server = JOptionPane.showInputDialog(Translation.get("gui_database_server"));
        String username = JOptionPane.showInputDialog(Translation.get("gui_database_user"));
        String password = JOptionPane.showInputDialog(Translation.get("gui_database_password"));
        try {
            InfluxDB db = InfluxDBFactory.connect(server, username, password);
            String database = JOptionPane.showInputDialog(Translation.get("gui_database_database"));
            db.setDatabase(database);
            return new InfluxDatabase(db, database);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, Translation.get("influx_error"));
            throw new NullPointerException("No such database");
        }
    }

    public static SQLDatabase SQLDatabase() {
        String host = JOptionPane.showInputDialog(Translation.get("gui_database_server"));
        String user = JOptionPane.showInputDialog(Translation.get("gui_database_user"));
        String password = JOptionPane.showInputDialog(Translation.get("gui_database_password"));
        String database = JOptionPane.showInputDialog(Translation.get("gui_database_database"));
        String table = JOptionPane.showInputDialog(Translation.get("gui_database_table"));
        try {
            return new SQLDatabase(host,user,password,database,table);
        } catch (NullPointerException ignored) {
            return null;
        }
    }
}
