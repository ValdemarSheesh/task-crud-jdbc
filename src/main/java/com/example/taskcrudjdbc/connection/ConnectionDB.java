package com.example.taskcrudjdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String url = "jdbc:postgresql://localhost:5432/company_db";
    private static final String user = "postgres";
    private static final String password = "";

    public static Connection createConnectionDB() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}
