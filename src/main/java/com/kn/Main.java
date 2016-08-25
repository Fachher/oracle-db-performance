package com.kn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.kn.service.DbConnectionRequestRunner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int numberOfConnections = 20;

        ExecutorService executor = Executors.newFixedThreadPool(
                numberOfConnections, new ThreadFactory() {

                    public Thread newThread(Runnable r) {
                        Thread t = Executors.defaultThreadFactory().newThread(r);
                        t.setDaemon(false);
                        return t;
                    }
                });

        for (int i = 0; i < numberOfConnections; i++) {
            executor.execute(new DbConnectionRequestRunner());
        }

        while (!executor.isTerminated()) {

        }
    }
}
