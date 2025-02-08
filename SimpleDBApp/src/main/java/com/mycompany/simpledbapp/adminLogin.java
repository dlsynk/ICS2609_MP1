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

        String[] column = {"Users"};

        DefaultTableModel tableModel = new DefaultTableModel(column, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        int index = 0;
        while (index < users.size()) {
            String user = users.get(index);
            tableModel.addRow(new Object[]{user});
            index++;
        }

        JTable userTable = new JTable(tableModel);

        userTable.setFillsViewportHeight(true);
        userTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(userTable);

        add(scrollPane, BorderLayout.CENTER);

        javax.swing.JButton jButton1 = new javax.swing.JButton();
        jButton1.setText("Logout");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        LoginSystem ls = new LoginSystem();
        ls.openLogin();
    }
}
