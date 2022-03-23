package com.epam.library.connection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final int MAXIMUM_SIMULTANEOUS_CONNECTIONS = 10;

    private static ConnectionPool INSTANCE;

    private final Queue<ProxyConnection> availableConnections;
    private final Queue<ProxyConnection> connectionsInUse;

    private static final ReentrantLock instanceLock = new ReentrantLock();
    private static final ReentrantLock connectionsLock = new ReentrantLock();

    private final ConnectionFactory connectionFactory = new ConnectionFactory();

    private ConnectionPool() {
        availableConnections = new ArrayDeque<>();
        connectionsInUse = new ArrayDeque<>();
    }

    public static ConnectionPool getInstance() throws SQLException, IOException, ClassNotFoundException {
        ConnectionPool localInstance = INSTANCE;
        if (localInstance == null) {
            instanceLock.lock();
            try {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    localInstance = INSTANCE = new ConnectionPool();
                    localInstance.initialiseConnections();
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return localInstance;
    }

    private void initialiseConnections() throws SQLException, IOException, ClassNotFoundException {
        for (int i = 0; i < MAXIMUM_SIMULTANEOUS_CONNECTIONS; i++) {
            ProxyConnection connection = connectionFactory.create();
            availableConnections.offer(connection);
        }
    }

    public ProxyConnection getConnection() {
        connectionsLock.lock();
        try {
            ProxyConnection connection = availableConnections.remove();
            connectionsInUse.offer(connection);
            return connection;
        } finally {
            connectionsLock.unlock();
        }
    }

    public void returnConnection(ProxyConnection connection) {
        connectionsLock.lock();
        try {
            if (connectionsInUse.contains(connection)) {
                availableConnections.offer(connection);
                connectionsInUse.remove(connection);
            }
        } finally {
            connectionsLock.unlock();
        }
    }

}
