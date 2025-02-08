/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simpledbapp;

import java.awt.BorderLayout;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class LoginSystem {
    static boolean loginSuccess = false;
    static boolean usernameFound = false;
    static boolean wrongPassword = false;
    //PLEASE CHANGE TO FILE PATH OF AccountsDB IN YOUR DEVICE, THANK YOU <3
    String directPathToDb = "C:\\Users\\jyroa\\ICS2609_MP1\\SimpleDBApp\\AccountsDB";
    SimpleDBApp jdbc = new SimpleDBApp("root", "root", directPathToDb);
    
    private static int attempts = 0;
    private JFrame frame;

    public void openLogin() {
        frame = new JFrame("Login System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 180);
        frame.setLocationRelativeTo(null); 

        JPanel panel = createLoginPanel();
        frame.add(panel);

        frame.setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel usernamePanel = new JPanel(new BorderLayout());
        JLabel userLabel = new JLabel("Username: ");
        JTextField userText = new JTextField(20);
        usernamePanel.add(userLabel, BorderLayout.WEST);
        usernamePanel.add(userText, BorderLayout.CENTER);

        JPanel passwordPanel = new JPanel(new BorderLayout());
        JLabel passwordLabel = new JLabel("Password: ");
        JPasswordField passwordText = new JPasswordField(20);
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordText, BorderLayout.CENTER);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> validateLogin(userText.getText(), new String(passwordText.getPassword())));

        panel.add(usernamePanel);
        panel.add(Box.createVerticalStrut(10)); 
        panel.add(passwordPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(loginButton);

        return panel;
    }

    private void validateLogin(String username, String password) {
        loginSuccess = false; 
        usernameFound = false;
        wrongPassword = false;
        String userRole = null; 

        try {
            ResultSet rs = jdbc.getAll();

            while (rs.next()) {
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
                
                if (dbUsername.equals(username)) {
                    usernameFound = true;
                    if (dbPassword.equals(password)) {
                        loginSuccess = true;
                        userRole = rs.getString("user_role");
                        break;
                    }
                    else {
                        wrongPassword = true;
                    }
                }
            }

            if (loginSuccess) {
                frame.dispose();
                attempts = 0;

                if ("admin".equals(userRole)) {
                    adminLogin al = new adminLogin(userList());
                    al.setVisible(true);
                } else if ("user".equals(userRole)) {
                    userLogin ul = new userLogin();
                    ul.setVisible(true);
                }
            } else {
                attempts++; 
                if (attempts >= 3) {
                    forceClose(); 
                } else if (!usernameFound) {
                    JOptionPane.showMessageDialog(frame, "Login Failed! Incorrect username.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (wrongPassword) {
                   JOptionPane.showMessageDialog(frame, "Login Failed! Incorrect password.", "Error", JOptionPane.ERROR_MESSAGE); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "An error occurred while validating login.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void forceClose() {
        JOptionPane.showMessageDialog(frame, "Too many failed attempts. The application will now close.", "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
    
    public List userList() {
        List<String> users = new ArrayList<>();

        try {
            ResultSet rs = jdbc.getAll();
            
            while(rs.next()) {
                if (rs.getString("user_role").equals("user")) {
                    users.add(rs.getString("username"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "An error occurred while validating login.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return users;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginSystem().openLogin());
    }
    
    

}
