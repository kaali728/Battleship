package com.Battleship.UI;

import com.Battleship.Model.Board;
import com.Battleship.Network.Server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ServerScreen extends JPanel {
    int port;
    int fieldsize;
    JButton button;

    ServerScreen(int port, int fieldsize) {
        this.port = port;
        this.fieldsize = fieldsize;
        initVar();
        initLayout();
    }

    public void initVar() {
        System.out.println(port);

        Server.create(port);
        Server.fieldsize = fieldsize;

        button = new JButton("Server");
        Server.button = button;
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(
                // Wenn der Knopf gedrückt wird,
                // erfolgt eine Kontrollausgabe auf System.out.
                // Anschließend wird der Knopf deaktiviert
                // und eine beliebige Nachricht an die andere "Seite" geschickt,
                // damit diese ihren Knopf aktivieren kann.
                (e) -> {
                    System.out.println("Server clicked the button.");
                    button.setEnabled(false);
                    try {
                        Server.out.write(String.format("%s%n", "message"));
                        Server.out.flush();
                    } catch (IOException ex) {
                        System.out.println("write to socket failed");
                    }
                }
        );

        // Der Server-Knopf soll anfangs deaktiviert sein.
        button.setEnabled(false);
    }

    public void initLayout() {
        setBackground(Color.white);
        add(button);
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(100));
        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(vbox);

        // Board
        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            hbox.add(new Board(fieldsize));
            hbox.add(Box.createHorizontalStrut(10));
        }
        add(hbox);
    }
}