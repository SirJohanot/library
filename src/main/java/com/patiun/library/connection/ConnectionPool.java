package com.patiun.library.connection;

import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final int MAX_CONNECTIONS = 10;

    private static ConnectionPool INSTANCE;

    private final Queue<ProxyConnection> availableConnections;
    private final Queue<ProxyConnection> connectionsInUse;

    private static final ReentrantLock INSTANCE_LOCK = new ReentrantLock();
    private static final ReentrantLock CONNECTIONS_LOCK = new ReentrantLock();

    private static final Semaphore CONNECTIONS_SEMAPHORE = new Semaphore(MAX_CONNECTIONS);

    private final ConnectionFactory connectionFactory = new ConnectionFactory();

    private ConnectionPool() {
        availableConnections = new ArrayDeque<>();
        connectionsInUse = new ArrayDeque<>();
    }

    public static ConnectionPool getInstance() throws SQLException {
        ConnectionPool localInstance = INSTANCE;
        if (localInstance == null) {
            INSTANCE_LOCK.lock();
            try {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    localInstance = INSTANCE = new ConnectionPool();
                    localInstance.initialiseConnections();
                }
            } finally {
                INSTANCE_LOCK.unlock();
            }
        }
        return localInstance;
    }

    private void initialiseConnections() throws SQLException {
        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            ProxyConnection connection = connectionFactory.create();
            availableConnections.offer(connection);
        }
    }

    public ProxyConnection getConnection() throws InterruptedException {
        CONNECTIONS_LOCK.lock();
        try {
            CONNECTIONS_SEMAPHORE.acquire();
            ProxyConnection connection = availableConnections.remove();
            connectionsInUse.offer(connection);
            return connection;
        } finally {
            CONNECTIONS_LOCK.unlock();
        }
    }

    public void returnConnection(ProxyConnection connection) {
        CONNECTIONS_LOCK.lock();
        try {
            if (connectionsInUse.contains(connection)) {
                availableConnections.offer(connection);
                connectionsInUse.remove(connection);
            }
            CONNECTIONS_SEMAPHORE.release();
        } finally {
            CONNECTIONS_LOCK.unlock();
        }
    }

}
