package me.meloni.SolarLogAPI.DatabaseInteraction;

public class SQLDatabase {
    private final String host;
    private final String user;
    private final String password;
    private final String database;
    private final String table;

    public SQLDatabase(String host, String user, String password, String database, String table) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.database = database;
        this.table = table;
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public String getTable() {
        return table;
    }
}
