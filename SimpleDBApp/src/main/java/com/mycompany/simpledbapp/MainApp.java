/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simpledbapp;


import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Niko
 */
public class MainApp {
    public static void main(String[] args) {
        String directPathToDb = "E:\\Random Files\\ICS2609\\ICS2609_Projects\\ICS2609_MP1\\SimpleDBApp\\AccountsDB";
        SimpleDBApp jdbc = new SimpleDBApp("root", "root", directPathToDb);
        
        LoginSystem ls = new LoginSystem();
        ls.openLogin();
        
        
        
        try {
            ResultSet rs = jdbc.getAll();
            while(rs.next()) {
                System.out.println(rs.getString("username") + ", " + rs.getString("password") + ", " + rs.getString("user_role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.toString());
            System.err.println("");
        }
    }
}