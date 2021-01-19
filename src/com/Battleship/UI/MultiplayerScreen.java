package com.Battleship.UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Multiplayer screen.
 */
public class MultiplayerScreen extends JPanel {
    /**
     * The Main panel.
     */
    GamePanel mainPanel;
    /**
     * The Client.
     */
    JButton client;
    /**
     * The Server.
     */
    JButton server;
    /**
     * The Back.
     */
    JButton back;

    /**
     * Instantiates a new Multiplayer screen.
     *
     * @param mainPanel the main panel
     */
    MultiplayerScreen(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        initVar();
        initLayout();
    }

    /**
     * Init var.
     */
    public void initVar() {
        client = new JButton("Connect to a server");
        server = new JButton("Host game");
        back = new JButton("Back");

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.changeScreen("main");
            }
        });

        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.changeScreen("clientSetupScreen");
            }
        });
        server.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.changeScreen("serverSetupScreen");
            }
        });
    }

    /**
     * Init layout.
     */
    public void initLayout() {
        setBackground(Color.black);
        Font  buttonfont  = new Font(Font.SANS_SERIF,  Font.BOLD, 19);
        Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));

        client.setBackground(Color.black);
        client.setForeground(Color.WHITE);
        client.setFont(buttonfont);
        client.setFocusPainted(false);
        client.setMargin(new Insets(0, 0, 0, 0));
        client.setBorder(b);
        client.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                client.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                client.setBorder(b);
            }
        });
        server.setBackground(Color.black);
        server.setForeground(Color.WHITE);
        server.setFont(buttonfont);
        server.setFocusPainted(false);
        server.setMargin(new Insets(0, 0, 0, 0));
        server.setBorder(b);
        server.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                server.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                server.setBorder(b);
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

        Box vbox = Box.createVerticalBox();
        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        {
            vbox.add(Box.createVerticalStrut(50));
            vbox.add(client);
            vbox.add(Box.createVerticalStrut(20));
            vbox.add(server);
            vbox.add(Box.createVerticalStrut(20));
            vbox.add(back);
            vbox.add(Box.createVerticalStrut(20));
            client.setAlignmentX(Component.CENTER_ALIGNMENT);
            server.setAlignmentX(Component.CENTER_ALIGNMENT);
            back.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        add(vbox);
    }
}
