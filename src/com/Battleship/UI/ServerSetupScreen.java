package com.Battleship.UI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerSetupScreen extends JPanel {
    GamePanel mainPanel;
    JTextField port;
    JLabel portLabel;
    Integer portNumber;
    JButton createServer;
    JLabel sizeLabel;
    JSlider size;
    int fieldsize;

    ServerSetupScreen(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        initVar();
        initLayout();
    }

    public void initVar() {
        portLabel = new JLabel("Port");
        port = new JTextField(10);

        sizeLabel = new JLabel("Board size");
        size = new JSlider(5, 30);
        size.setPaintTrack(true);
        size.setPaintTicks(true);
        size.setPaintLabels(true);
        size.addChangeListener(this::stateChanged);
        size.setMinorTickSpacing(5);
        size.setMajorTickSpacing(25);
        sizeLabel.setText("size =" + size.getValue());

        createServer = new JButton("Create server");
        createServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                portNumber = Integer.parseInt(port.getText());
                mainPanel.changeScreen("serverScreen", portNumber, fieldsize);
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
        vbox.add(sizeLabel);
        vbox.add(size);
        add(vbox);
        add(createServer);
    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource().equals(size)) {
            sizeLabel.setText("size = " + size.getValue());
            this.fieldsize = size.getValue();
        }
    }
}
