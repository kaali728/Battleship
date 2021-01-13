package com.Battleship.AIvsAI;

import com.Battleship.Player.AINetworkPlayer;
import com.Battleship.Player.AIPlayer;
import com.Battleship.UI.GamePanel;

import javax.swing.*;
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


    public AIClientSetup(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        initVar();
        initLayout();
    }

    public void initVar() {
        addressLabel = new JLabel("Address");
        portLabel = new JLabel("Port");
        address = new JTextField(10);
        port = new JTextField(10);
        connect = new JButton("Connect to server");
        aiPlayer = new AINetworkPlayer();
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.aichangeScreen("aiclientscreen", address.getText(), Integer.parseInt(port.getText()), aiPlayer);
            }
        });
    }

    public void initLayout() {
        setBackground(Color.white);
        add(addressLabel);
        add(address);
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(100));
        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(vbox);
        add(portLabel);
        add(port);
        add(connect);
    }
}
