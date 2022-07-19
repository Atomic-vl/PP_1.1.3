package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    private static Util connectionStatus;
    private Connection connect;
    private static final String URL = "jdbc:mysql://localhost:3306/kata_jdbc_pp113";
    private static final String USER = "root";
    private static final String PASSWORD = "zxtcnysq";

    private Util() {
        try {
            connect = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Неудачная попытка подключения к БД!");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connect;
    }

    public static Util getConnectionToBD() {
        try {
            if (connectionStatus == null || connectionStatus.getConnection().isClosed()) {
                connectionStatus = new Util();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connectionStatus;
    }
//        System.out.println("Connection to Store DB succesfull!");
//        Statement statement = connect.createStatement();
//        statement.execute("CREATE SCHEMA testFromidea;");
//        connect.close();
}
