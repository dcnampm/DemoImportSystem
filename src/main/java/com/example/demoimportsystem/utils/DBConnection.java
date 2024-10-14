package com.example.demoimportsystem.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static String url = "jdbc:mysql://localhost:3304/import_system";
    public static String username = "root";
    public static String password = "123456a@";

    private static Connection conn;
    public static Connection getConnection() {
        try {
            //Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Connect to db
            conn = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}
