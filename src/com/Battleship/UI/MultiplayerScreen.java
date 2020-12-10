package com.Battleship.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiplayerScreen extends JPanel {
    GamePanel mainPanel;
    JButton client;
    JButton server;
    JButton back;

    MultiplayerScreen(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        initVar();
        initLayout();
    }

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
