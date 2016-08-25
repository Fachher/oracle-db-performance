package com.kn.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionRequestRunner implements Runnable {

    public void run() {

        boolean interrupted = false;

        while(!interrupted){
            Connection connection = null;
            try {
                String connectionString = "jdbc:oracle:thin:@localhost:1521:XE";
                String userName = "DGVS_LOCAL";
                String password = "DGVS_LOCAL";
                connection = DriverManager.getConnection(connectionString, userName, password);
                Thread.sleep(10000);
                connection.close();
            } catch (SQLException | InterruptedException ie) {
                interrupted = true;
            } finally {
                if(connection != null){
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
