package com.Battleship.UI;

import com.Battleship.Constants.Constants;
import com.Battleship.Player.Player;


import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private CardLayout cl;
    private Player Singleplayer = new Player();

    // Hintergrundbild
    public GamePanel() {
        initLayout();
    }

    void initLayout() {
        cl = new CardLayout();
        setLayout(cl);
        setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        add(new MainScreen(this), "main");
        add(new SinglePlayerScreen(this), "singleplayer");
        add(new MultiplayerScreen(this), "multiplayer");
        add(new ServerSetupScreen(this), "serverSetupScreen");
        add(new ClientSetupScreen(this), "clientSetupScreen");

        cl.show(this, "main");
    }

    public void changeScreen(String s) {
        if (s == "battlefield") {
            add(new ShipSetupScreen(this), "battlefield");
        }
        cl.show(this, s);
    }

    public void changeScreen(String s, int port, int fieldsize) {
        add(new ServerScreen(port, fieldsize), "serverScreen");
        cl.show(this, s);
    }

    public void changeScreen(String s, String address, int port) {
        add(new ClientScreen(address, port), "clientScreen");
        cl.show(this, s);
    }

    public void setSingleplayer(Player singleplayer) {
        Singleplayer = singleplayer;
    }

    public Player getSingleplayer() {
        return Singleplayer;
    }
}
