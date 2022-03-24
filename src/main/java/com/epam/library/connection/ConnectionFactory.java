package com.epam.library.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final String CONNECTION_PROPERTIES_FILE_NAME = "databaseConnection.properties";
    private static final String DRIVER_CLASS = "db.driver.class";
    private static final String CONNECTION_URL = "db.conn.url";
    private static final String USERNAME = "db.username";
    private static final String PASSWORD = "db.password";

    public ProxyConnection create() throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(CONNECTION_PROPERTIES_FILE_NAME);
        properties.load(inputStream);
        String databaseDriverClass = properties.getProperty(DRIVER_CLASS);
        String databaseConnectionUrl = properties.getProperty(CONNECTION_URL);
        String databaseUsername = properties.getProperty(USERNAME);
        String databasePassword = properties.getProperty(PASSWORD);
        Class.forName(databaseDriverClass);
        Connection connection = DriverManager.getConnection(databaseConnectionUrl, databaseUsername, databasePassword);
        return new ProxyConnection(connection, ConnectionPool.getInstance());
    }

}
