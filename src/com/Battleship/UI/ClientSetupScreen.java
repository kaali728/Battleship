package com.Battleship.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientSetupScreen extends JPanel {
    GamePanel mainPanel;
    JLabel addressLabel;
    JLabel portLabel;
    JTextField address;
    JTextField port;
    JButton connect;


    ClientSetupScreen(GamePanel mainPanel) {
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
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.changeScreen("clientScreen", address.getText(), Integer.parseInt(port.getText()));
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
