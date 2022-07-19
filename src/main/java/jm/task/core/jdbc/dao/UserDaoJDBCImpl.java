package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.util.*;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnectionToBD().getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement createTable = connection.createStatement()) {
            connection.setAutoCommit(false);
            createTable.execute("CREATE TABLE IF NOT EXISTS Users (" +
                    "id BIGINT not null AUTO_INCREMENT," +
                    "name VARCHAR(32) not null," +
                    "lastName VARCHAR(64) not null," +
                    "age SMALLINT not null, PRIMARY KEY(id))");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        try (Statement dropTable = connection.createStatement()) {
            connection.setAutoCommit(false);
            dropTable.executeUpdate("DROP TABLE IF EXISTS Users");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement saveUser = connection.prepareStatement("INSERT INTO Users(name, lastName, age) VALUES (?,?,?)")) {
            connection.setAutoCommit(false);
            saveUser.setString(1, name);
            saveUser.setString(2, lastName);
            saveUser.setByte(3, age);
            saveUser.execute();
            connection.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        try {
            PreparedStatement removeUser = connection.prepareStatement("DELETE FROM Users WHERE ID = ?");
            connection.setAutoCommit(false);
            removeUser.setLong(1, id);
            removeUser.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            Statement getUsers = connection.createStatement();
            ResultSet users = getUsers.executeQuery("SELECT * FROM Users");
            while (users.next()) {
                User user = new User();
                user.setId(users.getLong("id"));
                user.setName(users.getString("name"));
                user.setLastName(users.getString("lastName"));
                user.setAge(users.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try {
            Statement cleanTable = connection.createStatement();
            connection.setAutoCommit(false);
            cleanTable.execute("TRUNCATE TABLE Users");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

    }
}
