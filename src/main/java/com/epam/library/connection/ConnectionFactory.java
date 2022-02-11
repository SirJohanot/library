package com.epam.library.connection;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final String DATABASE_CONNECTION_PROPERTIES_PATH = "C:\\Users\\war criminal\\Documents\\GitHub\\library\\src\\main\\resources\\databaseConnection.properties";

    public ProxyConnection create() throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties(); //TODO: figure out why the properties file cannot be located with relative path reference
        FileReader fileReader = new FileReader(DATABASE_CONNECTION_PROPERTIES_PATH);
        properties.load(fileReader);
        String databaseDriverClass = properties.getProperty("db.driver.class");
        String databaseConnectionUrl = properties.getProperty("db.conn.url");
        String databaseUsername = properties.getProperty("db.username");
        String databasePassword = properties.getProperty("db.password");
//        String databaseDriverClass = "com.mysql.jdbc.Driver";
//        String databaseConnectionUrl = "jdbc:mysql://localhost:3306/library";
//        String databaseUsername = "root";
//        String databasePassword = "";
        Class.forName(databaseDriverClass);
        Connection connection = DriverManager.getConnection(databaseConnectionUrl, databaseUsername, databasePassword);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        return new ProxyConnection(connection, connectionPool);
    }

}
