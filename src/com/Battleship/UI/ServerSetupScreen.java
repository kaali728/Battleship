package com.Battleship.UI;

import com.Battleship.Network.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerSetupScreen extends JPanel {
    GamePanel mainPanel;
    JTextField port;
    JLabel portLabel;
    Integer portNumber;
    JButton createServer;

    ServerSetupScreen(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        initVar();
        initLayout();
    }

    public void initVar() {
        port = new JTextField(10);
        portLabel = new JLabel("Port");

        createServer = new JButton("Create server");
        createServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                portNumber = Integer.parseInt(port.getText());
                mainPanel.changeScreen("serverScreen", portNumber);
            }
        });
    }

    public void initLayout() {
        setBackground(Color.white);
        add(portLabel);
        add(port);
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(100));
        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(vbox);
        add(createServer);
    }
}
