package jm.task.core.jdbc.util;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    // JDBC
    private static final String urlAddress = "jdbc:mysql://localhost:3306/MySql"; //?useSSL=false&serverTimezone=Europe/Moscow";
    private static final String userName = "PreProjectUser";
    private static final String password = "1234";
    private static Connection connection = null;
    private static SessionFactory sessionFactory = null;

    public static Connection createConnection() {

        try {
            connection = DriverManager.getConnection(urlAddress, userName, password);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
        }
        return connection;
    }
    public static void closeConnectionJDBC() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Wrong with closeConnectionJDBC " + e.getErrorCode());
        }
    }

    // Hibernate
    public static SessionFactory createHibernateConnection() {

        try {
            Properties properties = new Properties();
            properties.put(Environment.URL, "jdbc:mysql://localhost:3306/MySql");//?useSSL=false&serverTimezone=Europe/Moscow");
            properties.put(Environment.USER, "PreProjectUser");
            properties.put(Environment.PASS, "1234");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            properties.put(Environment.SHOW_SQL, "true");
            //properties.put(Environment.ISOLATION, "SERIALIZABLE");
            //properties.put(Environment.AUTOCOMMIT, "true");
            // способ 1
           // Configuration configuration = new Configuration();
           // configuration.setProperties(properties);
           // configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);
           // StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
           // sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            //способ 2
             sessionFactory = new Configuration()
                     .addProperties(properties)
                     .addAnnotatedClass(jm.task.core.jdbc.model.User.class)
                     .buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Wrong with method createHibernateConnection()");
        }
        return sessionFactory;
    }
    public static void closeSessionFactoryHibernate() {
        sessionFactory.close();
    }
}
