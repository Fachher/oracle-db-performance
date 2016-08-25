package com.kn.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class LoadGenerator {

    private List<Session> sessionList;
    private Properties properties;

    private LoadGenerator(){}

    public LoadGenerator(Properties properties) {

        this.properties = properties;
        this.sessionList = new ArrayList<Session>();
    }

    public void createSessions(int numberOfSessions) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

            String number_of_session = properties.getProperty("number_of_session");

            if(number_of_session == null || number_of_session.length() == 0){
                System.err.print("Number of session needs to be set!");
            }

            for (int i=0;i<numberOfSessions;i++){
                Session session = sessionFactory.openSession();
                session.createSQLQuery("select 1 from dual").list();
                sessionList.add(session);
            }

            Thread.sleep(20000);

            for (Session session : sessionList) {
                session.close();
            }

            if ( sessionFactory != null ) {
                sessionFactory.close();
            }

        }
        catch (RuntimeException e) {
            StandardServiceRegistryBuilder.destroy( registry );
        }
        catch (InterruptedException e){
            System.out.println("Interrupt Exception occured");
        }
    }
}
