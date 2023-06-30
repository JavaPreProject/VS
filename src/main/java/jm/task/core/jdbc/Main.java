package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
         //реализуйте алгоритм здесь
        UserService userServiceJDBC = new UserServiceImpl();
        userServiceJDBC.createUsersTable();
        userServiceJDBC.saveUser("Ivan","Ivanov",(byte) 18);
        userServiceJDBC.saveUser("Peter","Petrov",(byte) 21);
        userServiceJDBC.saveUser("Viktor","Viktorov",(byte) 35);
        userServiceJDBC.saveUser("Nikolay", "Nikolaev",(byte) 40);

        System.out.println("\nрезультат работы метода \" toString \" класса User:");
        System.out.println(userServiceJDBC.getAllUsers().toString());
        userServiceJDBC.cleanUsersTable();
        userServiceJDBC.dropUsersTable();

        Util.closeConnectionJDBC();

        //UserService userServiceHibernate = new UserServiceImpl();
        //userServiceHibernate.createUsersTable();
        //userServiceHibernate.saveUser("Ivan","Ivanov",(byte) 18);
        //userServiceHibernate.dropUsersTable();

       // Util.closeSessionFactoryHibernate();


    }
}
