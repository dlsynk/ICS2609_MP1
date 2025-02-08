package com.mycompany.simpledbapp;

import java.sql.*;


/**
 *
 * @author fonze
 */
public class SimpleDBApp {
    private Connection conn;

    public SimpleDBApp(String username, String password, String database) {
        String connStr = "jdbc:sqlite:" + database;
        System.out.println(connStr);
        try {
            conn = DriverManager.getConnection(connStr);
        } catch (SQLException e) {
            System.err.println("Failed to create connection");
            System.err.println(e.toString());
        }
    }

    public ResultSet getAll() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("select * from Users");
    }

}

