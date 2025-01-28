package com.mycompany.simpledbapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class adminLogin extends JFrame {

    public adminLogin(List<String> users) {
        setTitle("User List");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] column = {"Name"};

        // Create the table model
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);

        // Add user data to the table model
        int index = 0;
        while (index < users.size()) {
            String user = users.get(index);
            tableModel.addRow(new Object[]{user}); // Add each string as a row
            index++;
        }

        // Create the JTable and set its model
        JTable userTable = new JTable(tableModel);

        // Set some optional customizations
        userTable.setFillsViewportHeight(true);
        userTable.setAutoCreateRowSorter(true); // Enable sorting

        // Wrap the table in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(userTable);

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);
        
        javax.swing.JButton jButton1 = new javax.swing.JButton();
        jButton1.setText("Logout");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, BorderLayout.SOUTH);
        

        // Make the frame visible
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {

    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        dispose();
        LoginSystem ls = new LoginSystem();
        ls.openLogin();
    }    
}
