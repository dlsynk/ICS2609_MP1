package com.mycompany.simpledbapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import gui.frame;

/**
 *
 * @author fonze
 */
public class SimpleDBApp {

    private class User {
        String username;
        String password;
        String accessRole;

        public User(String username, String password, String accessRole) {
            this.username = username;
            this.password = password;
            this.accessRole = accessRole;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAccessRole() {
            return accessRole;
        }

        public void setAccessRole(String accessRole) {
            this.accessRole = accessRole;
        }
    }

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

    public User getUser(String username, String password) throws SQLException {
        Statement stmt = conn.createStatement();
        String sqlStr = "select * from AccountsDB.Users a where a.username=" +
                username + " & a.password=" + password;
        ResultSet rs = stmt.executeQuery(sqlStr);
        if(rs.next())
            return new User(rs.getString("username"), rs.getString("password"),
                rs.getString("access_role"));
        else return null;
    }

}

