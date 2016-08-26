package com.kn.databaseloadtester;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.SQLTimeoutException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kn.util.PropertyReader;

public class DatabaseConnectionPoller implements Runnable {

    private static Logger logger = Logger.getLogger(DatabaseConnectionPoller.class.getName());

    public void run() {
        int connectionTimeOutWaitTime = PropertyReader.getConnectionTimeoutWaitTime();
        int connectionTime = PropertyReader.getConnectionTime();

        Connection connection = null;
        boolean interrupted = false;
        int numberOfFailedConnections = 0;
        int numberOfSuccessfullyConnections = 0;

        while(!Thread.currentThread().isInterrupted() && !interrupted){
            try {
                DriverManager.setLoginTimeout(connectionTimeOutWaitTime);
                String connectionString = "jdbc:oracle:thin:@localhost:1521:XE";
                String userName = "DGVS_LOCAL";
                String password = "DGVS_LOCAL";
                try{
                    connection = DriverManager.getConnection(connectionString, userName, password);
                    logger.log(Level.INFO, "SUCCESFUL created new connection");
                }catch (SQLTimeoutException | SQLRecoverableException ex){
                    logger.log(Level.WARNING, "ERROR connection could not be established");
                    interrupted = wait(connectionTimeOutWaitTime);
                    numberOfFailedConnections++;
                    continue;
                }
                interrupted = wait(connectionTime);
                numberOfSuccessfullyConnections++;
            } catch (SQLException e) {
                numberOfFailedConnections++;
            } finally {
                closeConnection(connection);
            }
        }

        for (Handler handler : logger.getHandlers()) {
            handler.flush();
        }

        System.out.println("Number of connections for thread " + Thread.currentThread().getName() + " successfully: " + numberOfSuccessfullyConnections + " failed: " + numberOfFailedConnections);
        closeConnection(connection);
    }

    private boolean wait(int milliseconds){
        try {
            Thread.sleep(milliseconds);
            return false;
        } catch (InterruptedException e) {
            return true;
        }
    }

    private void closeConnection(Connection connection) {
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
