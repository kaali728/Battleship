package com.Battleship.AIvsAI;

import com.Battleship.UI.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AIScreen extends JPanel {
    GamePanel mainPanel;
    JButton client;
    JButton server;
    JButton back;

    /**
     * @param mainPanel
     */
    public AIScreen(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        initVar();
        initLayout();
    }

    /**
     *
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
                mainPanel.changeScreen("aiclientsetup");
            }
        });
        server.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.changeScreen("aiserversetup");
            }
        });
    }

    /**
     *
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
