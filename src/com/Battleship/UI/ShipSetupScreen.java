package com.Battleship.UI;

import com.Battleship.Model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShipSetupScreen extends Panel {
    GamePanel mainPanel;
    private javax.swing.JTable jTable1;

    ShipSetupScreen(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        initvar();
        initlayout();
    }

    public void initvar() {
    }

    public void initlayout() {
//        Box hbox = Box.createHorizontalBox();
//        {
//            hbox.add(Box.createHorizontalStrut(10));
//            hbox.add(new Board(10));
//            hbox.add(Box.createHorizontalStrut(10));
//        }
//        add(hbox);
//        hbox.setAlignmentY(Component.CENTER_ALIGNMENT);

//        jTable1 = new javax.swing.JTable();
//
//        jTable1.setModel(new javax.swing.table.DefaultTableModel(
//                new Object [][] {
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                        {null, null, null, null, null},
//                },
//                new String [] {
//                        "", "", "", "", ""
//                }
//        ));
//
//        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
//
//        jTable1.setAutoscrolls(false);
//
//        jTable1.setCellSelectionEnabled(true);
//
//        jTable1.setFillsViewportHeight(true);
//
//        jTable1.setMinimumSize(new java.awt.Dimension(200, 300));
//
//        jTable1.setName(""); // NOI18N
//
//        jTable1.setRowHeight(50);
//        jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
//        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//
//        add(jTable1);

    }


}
