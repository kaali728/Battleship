package com.Battleship.UI;

import javax.swing.*;
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
        setBackground(Color.white);
        add(client);
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(100));
        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(vbox);
        add(server);
        add(back);
    }
}
