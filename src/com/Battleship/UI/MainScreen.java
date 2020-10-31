package com.Battleship.UI;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JPanel {
    JButton singleplayer;
    JButton multiplayer;

    MainScreen() {
        initVars();
        initLayout();
    }

    void initVars() {
        singleplayer = new JButton("Singleplayer");
        multiplayer = new JButton("Multiplayer");
    }

    void initLayout() {
        add(singleplayer);
        add(multiplayer);
        singleplayer.addActionListener(
                (e) -> System.out.println("Singleplayer")
        );
        multiplayer.addActionListener(
                (e) -> System.out.println("Multiplayer")
        );
    }
}
