package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class is a basic utility tool for the information system to connect with a database
 * @author Yuxuan Zhang
 */
public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/SportactivityDB";
    private static final String USER = "root";
    private static final String PASSWORD = "@Chenxi040802";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
