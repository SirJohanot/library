package com.epam.library.connection;

import com.epam.library.exception.ConnectionFactoryInitialisationException;

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

    private final String databaseConnectionUrl;
    private final String databaseUsername;
    private final String databasePassword;

    public ConnectionFactory() {
        Properties properties = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(CONNECTION_PROPERTIES_FILE_NAME);
        try {
            properties.load(inputStream);
            String databaseDriverClass = properties.getProperty(DRIVER_CLASS);
            databaseConnectionUrl = properties.getProperty(CONNECTION_URL);
            databaseUsername = properties.getProperty(USERNAME);
            databasePassword = properties.getProperty(PASSWORD);
            Class.forName(databaseDriverClass);
        } catch (IOException | ClassNotFoundException e) {
            throw new ConnectionFactoryInitialisationException(e);
        }
    }

    public ProxyConnection create() throws SQLException {
        Connection connection = DriverManager.getConnection(databaseConnectionUrl, databaseUsername, databasePassword);
        return new ProxyConnection(connection, ConnectionPool.getInstance());
    }

}
