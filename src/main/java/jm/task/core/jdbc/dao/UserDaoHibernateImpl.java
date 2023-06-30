package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.createHibernateConnection();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS mydbtest.users3(" +
                    "id INT8 NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR (50) NOT NULL," +
                    "lastName VARCHAR (50) NOT NULL," +
                    "age TINYINT NOT NULL)").executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            System.err.println("Wrong with transaction");
            if(transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();){
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS mydbtest.users4").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Wrong with method dropUsersTable");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.createHibernateConnection().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            try {
                session.save(user);
                transaction.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
