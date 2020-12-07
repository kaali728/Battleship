package com.Battleship.UI;

import com.Battleship.Model.Board;
import com.Battleship.Model.Ship;
import com.Battleship.Network.Server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ServerScreen extends JPanel {
    int port;
    int fieldsize;
    JButton button;
    int carrierCount, battleshipCount, submarineCount, destroyerCount;
    GamePanel mainPanel;
    Board postionBoard;



    ServerScreen(int port, int fieldsize, int carrierCount,int battleshipCount,int submarineCount,int destroyerCount, GamePanel mainPanel) {
        this.port = port;
        this.fieldsize = fieldsize;
        this.carrierCount = carrierCount;
        this.battleshipCount = battleshipCount;
        this.submarineCount = submarineCount;
        this.destroyerCount = destroyerCount;
        this.mainPanel = mainPanel;
        initVar();
        initLayout();
    }

    public void initVar() {
        System.out.println(port);

        Server.create(port);
        Server.fieldsize = fieldsize;

        Server.carrierCount = carrierCount;

        Server.battleshipCount = battleshipCount;

        Server.submarineCount = submarineCount;

        Server.destroyerCount = destroyerCount;


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
            ArrayList<Ship> fleet = this.mainPanel.getSingleplayer().getFleet();
            postionBoard = new Board(fieldsize, fleet,this.mainPanel.getGameState());
            hbox.add(postionBoard);
            hbox.add(Box.createHorizontalStrut(10));
        }
        add(hbox);
    }
}
