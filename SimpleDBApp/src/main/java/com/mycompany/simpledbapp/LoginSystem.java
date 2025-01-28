/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simpledbapp;

import java.awt.BorderLayout;
import java.sql.*;
import javax.swing.*;

public class LoginSystem {
    String directPathToDb = "C:\\Users\\Niko\\ICS2609_MP1\\SimpleDBApp\\AccountsDB";
    SimpleDBApp jdbc = new SimpleDBApp("root", "root", directPathToDb);
    
    private static int attempts = 0;
    private JFrame frame;

    public void openLogin() {
        // Create the frame
        frame = new JFrame("Login System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 180);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create and add components
        JPanel panel = createLoginPanel();
        frame.add(panel);

        // Set frame visibility
        frame.setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Username
        JPanel usernamePanel = new JPanel(new BorderLayout());
        JLabel userLabel = new JLabel("Username: ");
        JTextField userText = new JTextField(20);
        usernamePanel.add(userLabel, BorderLayout.WEST);
        usernamePanel.add(userText, BorderLayout.CENTER);

        // Password
        JPanel passwordPanel = new JPanel(new BorderLayout());
        JLabel passwordLabel = new JLabel("Password: ");
        JPasswordField passwordText = new JPasswordField(20);
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordText, BorderLayout.CENTER);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> validateLogin(userText.getText(), new String(passwordText.getPassword())));

        // Add components to the main panel
        panel.add(usernamePanel);
        panel.add(Box.createVerticalStrut(10)); // Add space between components
        panel.add(passwordPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(loginButton);

        return panel;
    }

    private void validateLogin(String username, String password) {
        try {
            // Get all users from the database
            ResultSet rs = jdbc.getAll();

            // Flag to track whether the login is successful
            boolean loginSuccess = false;

            // Loop through the ResultSet to check for the username and password match
            while (rs.next()) {
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");

                // If username and password match, set loginSuccess to true
                if (dbUsername.equals(username) && dbPassword.equals(password)) {
                    loginSuccess = true;
                    break; // Stop the loop if credentials are correct
                }
            }

            // Check login result
            if (loginSuccess) {
                JOptionPane.showMessageDialog(frame, "Login Successful! Welcome!");
                attempts = 0; // Reset login attempts on success
            } else {
                attempts++;
                if (attempts >= 3) {
                    forceClose(); // Close the app after 3 failed attempts
                } else {
                    JOptionPane.showMessageDialog(frame, "Login Failed! Incorrect username or password.", "Error", JOptionPane.ERROR_MESSAGE);
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

}
