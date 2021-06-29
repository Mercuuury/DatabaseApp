package sample;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class DBConnection {
    private final String url;
    private final String username;
    private final String password;
    private final String driverClassName;
    private final String charset;

    private final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    public DBConnection(String url, String username, String password, String driverClassName, String charset) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
        this.charset = charset;
    }

    public Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(driverClassName);

            Properties connInfo = new Properties();
            connInfo.put("user", username);
            connInfo.put("password", password);
            connInfo.put("useUnicode", "true");
            connInfo.put("characterEncoding", charset);

            try {
                connection = DriverManager.getConnection(url, connInfo);
            }
            catch (SQLException e) {
                LOGGER.info("Error: " + e.getMessage());
            }
        }
        catch (ClassNotFoundException e) {
            LOGGER.info("Driver not found.");
        }

        return connection;
    }
}
