/*
Copyright 2020 - 2021 Christoph Kohnen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.DatabaseInteraction.InfluxDatabase;
import me.meloni.SolarLogAPI.DatabaseInteraction.SQLDatabase;

import javax.swing.*;

/**
 * This class provides an interactive way to get an InfluxDB or SQL database.
 * @author Christoph Kohnen
 * @since 3.0.5
 */
public class GetDatabase {
    /**
     * Get an {@link InfluxDatabase} from GUI
     * @return A customized {@link InfluxDatabase}
     */
    public static InfluxDatabase influxDatabase() {
        String server = JOptionPane.showInputDialog("Server?");
        String token = JOptionPane.showInputDialog("Token?");
        String bucket = JOptionPane.showInputDialog("Bucket?");
        String org = JOptionPane.showInputDialog("Org?");
        return new InfluxDatabase(server,token,bucket,org);
    }

    /**
     * Get an {@link SQLDatabase} from GUI
     * @return A customized {@link SQLDatabase}
     */
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
