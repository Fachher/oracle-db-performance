package com.kn.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    public static final String CONFIG_FILE_NAME = "./dbload.conf";

    static Properties properties;

    static {
        properties = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream(CONFIG_FILE_NAME);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getNumberOfConnections(){
        return Integer.valueOf(properties.getProperty("number_of_connections"));
    }

    public static int getConnectionTimeoutWaitTime() {
        return Integer.valueOf(properties.getProperty("connection_timeout_wait_time"));
    }

    public static int getConnectionTime() {
        return Integer.valueOf(properties.getProperty("connection_time"));
    }
}
