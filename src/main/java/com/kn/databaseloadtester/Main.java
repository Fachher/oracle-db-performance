package com.kn.databaseloadtester;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kn.util.PropertyReader;

public class Main {

    private static Logger logger = Logger.getLogger(DatabaseConnectionPoller.class.getName());

    public static void main(String[] args) throws InterruptedException {

        logger.log(Level.INFO, "Database load generator has been started. Initializing threads");

        Integer numberOfConnections = PropertyReader.getNumberOfConnections();

        final ExecutorService executor = Executors.newFixedThreadPool(numberOfConnections);

        for (int i = 0; i < numberOfConnections; i++) {
            executor.execute(new DatabaseConnectionPoller());
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {

            public void run() {
                shutdownAndAwaitTermination(executor);
            }
        });
    }

    static void shutdownAndAwaitTermination(ExecutorService pool) {

        logger.log(Level.INFO, "Shutdown has been initialized. Waiting for all threads to be shutdown !");

        try {
            pool.shutdownNow();
            if (!pool.awaitTermination(10, TimeUnit.SECONDS)) System.err.println("Pool did not terminate");
        } catch (InterruptedException ie) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
