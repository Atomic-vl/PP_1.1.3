package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.saveUser("Donald", "Trump", (byte)77);
        userDaoJDBC.saveUser("Vovka", "Pupkin", (byte)15);
        userDaoJDBC.saveUser("Jan", "Jak", (byte)33);
        userDaoJDBC.saveUser("James", "Hatfield", (byte)55);
        System.out.println(userDaoJDBC.getAllUsers());
        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();

    }
}
