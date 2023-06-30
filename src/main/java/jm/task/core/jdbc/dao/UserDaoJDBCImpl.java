package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }
    Connection connection = Util.createConnection();

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS mydbtest.users3 " +
                    "(id INT8 NOT NULL AUTO_INCREMENT," +
                    " firstName VARCHAR(255)," +
                    " lastName VARCHAR(255)," +
                    " age INT2," +
                    " PRIMARY KEY (id))"
            );
        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("DROP TABLE IF EXISTS mydbtest.users3");

        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO mydbtest.users3(firstName, lastName, age)" +
                "VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            System.out.println("User с именем - " + name + " добавлен в базу данных ");

        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM mydbtest.users3 WHERE id = ?")) {

            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
        }
    }

    public List<User> getAllUsers() {
        List<User>users = new ArrayList<>();
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM mydbtest.users3");
            while (resultSet.next()) {
                User tmpUser = new User(
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
                );
                tmpUser.setId(resultSet.getLong("id"));
                users.add(tmpUser);
            }

        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.execute("TRUNCATE TABLE mydbtest.users3");

        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
        }
    }
}
