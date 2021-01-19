package com.Battleship.AIvsAI;

import com.Battleship.Player.AINetworkPlayer;
import com.Battleship.Player.AIPlayer;
import com.Battleship.UI.GamePanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AIClientSetup extends JPanel {
    GamePanel mainPanel;
    JLabel addressLabel;
    JLabel portLabel;
    JTextField address;
    JTextField port;
    JButton connect;
    AINetworkPlayer aiPlayer;
    JButton back;



    /**
     * @param mainPanel
     */
    public AIClientSetup(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        initVar();
        initLayout();
    }

    /**
     *
     */
    public void initVar() {
        addressLabel = new JLabel("Address");
        portLabel = new JLabel("Port");
        address = new JTextField(10);
        port = new JTextField(10);
        connect = new JButton("Connect to server");
        aiPlayer = new AINetworkPlayer();
        back = new JButton("Main Menu");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setGameState("start");
                mainPanel.changeScreen("main");
            }
        });
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.aichangeScreen("aiclientscreen", address.getText(), Integer.parseInt(port.getText()), aiPlayer);
            }
        });
    }

    /**
     *
     */
    public void initLayout() {
        setBackground(Color.black);
        Font  buttonfont  = new Font(Font.SANS_SERIF,  Font.BOLD, 19);
        Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));

        connect.setBackground(Color.black);
        connect.setForeground(Color.WHITE);
        connect.setFont(buttonfont);
        connect.setFocusPainted(false);
        connect.setMargin(new Insets(0, 0, 0, 0));
        connect.setBorder(b);
        connect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                connect.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                connect.setBorder(b);
            }
        });

        back.setBackground(Color.black);
        back.setForeground(Color.WHITE);
        back.setFont(buttonfont);
        back.setFocusPainted(false);
        back.setMargin(new Insets(0, 0, 0, 0));
        back.setBorder(b);
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                back.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                back.setBorder(b);
            }
        });

        addressLabel.setBackground(Color.black);
        addressLabel.setForeground(Color.WHITE);
        portLabel.setBackground(Color.black);
        portLabel.setForeground(Color.WHITE);

        Box orgVbox = Box.createVerticalBox();
        {
            Box hbox = Box.createHorizontalBox();
            {
                hbox.add(Box.createHorizontalStrut(20));
                hbox.setAlignmentX(Component.CENTER_ALIGNMENT);
                hbox.add(addressLabel);
                hbox.add(Box.createHorizontalStrut(20));
                hbox.add(address);
                hbox.add(Box.createHorizontalStrut(30));
                hbox.add(portLabel);
                hbox.add(Box.createHorizontalStrut(20));
                hbox.add(port);
            }

            Box vbox = Box.createVerticalBox();
            {
                vbox.add(Box.createVerticalStrut(50));
                vbox.add(connect);
                vbox.add(Box.createVerticalStrut(50));
                vbox.add(back);
            }
            vbox.setAlignmentX(Component.CENTER_ALIGNMENT);
            hbox.setAlignmentX(Component.CENTER_ALIGNMENT);
            orgVbox.add(Box.createVerticalStrut(60));
            orgVbox.add(hbox);
            orgVbox.add(vbox);
        }
        add(orgVbox);
    }
}
