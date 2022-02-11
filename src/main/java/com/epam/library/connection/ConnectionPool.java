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

    private static final ReentrantLock lock = new ReentrantLock();

    private final ConnectionFactory connectionFactory = new ConnectionFactory();

    private ConnectionPool() throws SQLException, IOException, ClassNotFoundException {
        availableConnections = new ArrayDeque<>();
        connectionsInUse = new ArrayDeque<>();
        for (int i = 0; i < MAXIMUM_SIMULTANEOUS_CONNECTIONS; i++) {
            ProxyConnection connection = connectionFactory.create(this);
            availableConnections.offer(connection);
        }
    }

    public static ConnectionPool getInstance() throws SQLException, IOException, ClassNotFoundException {
        ConnectionPool localInstance = INSTANCE;
        if (localInstance == null) {
            lock.lock();
            try {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    localInstance = INSTANCE = new ConnectionPool();
                }
            } finally {
                lock.unlock();
            }
        }
        return localInstance;
    }

    public ProxyConnection getConnection() {
        lock.lock();
        try {
            ProxyConnection connection = availableConnections.remove();
            connectionsInUse.offer(connection);
            return connection;
        } finally {
            lock.unlock();
        }
    }

    public void returnConnection(ProxyConnection connection) {
        lock.lock();
        try {
            if (connectionsInUse.contains(connection)) {
                availableConnections.offer(connection);
                connectionsInUse.remove(connection);
            }
        } finally {
            lock.unlock();
        }
    }

}
