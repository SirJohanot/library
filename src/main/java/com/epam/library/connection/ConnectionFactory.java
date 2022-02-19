package com.epam.library.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final String DATABASE_CONNECTION_PROPERTIES_FILE_NAME = "databaseConnection.properties";
    private static final String DATABASE_DRIVER_CLASS_PROPERTY = "db.driver.class";
    private static final String DATABASE_CONNECTION_URL_PROPERTY = "db.conn.url";
    private static final String DATABASE_USERNAME_PROPERTY = "db.username";
    private static final String DATABASE_PASSWORD_PROPERTY = "db.password";

    public ProxyConnection create(ConnectionPool connectionPool) throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(DATABASE_CONNECTION_PROPERTIES_FILE_NAME);
        properties.load(inputStream);
        String databaseDriverClass = properties.getProperty(DATABASE_DRIVER_CLASS_PROPERTY);
        String databaseConnectionUrl = properties.getProperty(DATABASE_CONNECTION_URL_PROPERTY);
        String databaseUsername = properties.getProperty(DATABASE_USERNAME_PROPERTY);
        String databasePassword = properties.getProperty(DATABASE_PASSWORD_PROPERTY);
        Class.forName(databaseDriverClass);
        Connection connection = DriverManager.getConnection(databaseConnectionUrl, databaseUsername, databasePassword);
        return new ProxyConnection(connection, connectionPool);
    }

}
