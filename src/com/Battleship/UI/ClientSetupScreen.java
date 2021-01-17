package com.Battleship.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Client setup screen.
 */
public class ClientSetupScreen extends JPanel {
    /**
     * The Main panel.
     */
    GamePanel mainPanel;
    /**
     * The Address label.
     */
    JLabel addressLabel;
    /**
     * The Port label.
     */
    JLabel portLabel;
    /**
     * The Address.
     */
    JTextField address;
    /**
     * The Port.
     */
    JTextField port;
    /**
     * The Connect.
     */
    JButton connect;


    /**
     * Instantiates a new Client setup screen.
     *
     * @param mainPanel the main panel
     */
    ClientSetupScreen(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        initVar();
        initLayout();
    }

    /**
     * Init var.
     */
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

    /**
     * Init layout.
     */
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
