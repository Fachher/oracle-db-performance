package com.kn;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.kn.service.LoadGenerator;

public class App {


    public static void main( String[] args ) throws IOException {

        int numberOfSessions = 0;

        if(args.length == 0){
            showInstruction();
        }else{
            try {
                numberOfSessions = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                showInstruction();
            }
        }

        System.out.println(args[0]);

        Properties properties = new Properties();

        InputStream resourceAsStream = App.class.getClassLoader().getResourceAsStream("conf.properties");
        properties.load(resourceAsStream);

        LoadGenerator generator = new LoadGenerator(properties);
        generator.createSessions(numberOfSessions);

    }

    private static void showInstruction() {
        System.out.println("Please provider the number of sessions !!!");
        System.out.println("java -jar ");
    }
}
