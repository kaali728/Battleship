package com.Battleship.UI;

import com.Battleship.Network.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientScreen extends JPanel {
    private static String address;
    private static Integer port;
    private static JButton button;

    ClientScreen(String address, Integer port) {
        ClientScreen.address = address;
        ClientScreen.port = port;
        initVar();
        initLayout();
    }

    public void initVar() {
        button = new JButton("Client");
        Client.button = button;
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(
                // Wenn der Knopf gedrückt wird,
                // erfolgt eine Kontrollausgabe auf System.out.
                // Anschließend wird der Knopf deaktiviert
                // und eine beliebige Nachricht an die andere "Seite" geschickt,
                // damit diese ihren Knopf aktivieren kann.
                (e) -> {
                    System.out.println("Client clicked the button.");
                    button.setEnabled(false);
                    try {
                        Client.out.write(String.format("%s%n", "message"));
                        Client.out.flush();
                    } catch (IOException ex) {
                        System.out.println("write to socket failed");
                    }
                }
        );

        Client.create(address, port);
    }

    public void initLayout() {
        setBackground(Color.white);
        add(button);
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(100));
        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(vbox);
    }
}
