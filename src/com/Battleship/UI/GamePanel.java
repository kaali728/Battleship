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

        cl.show(this, "main");
    }
    public void changeScreen(String s){
        if(s == "battlefield"){
            add(new ShipSetupScreen(this), "battlefield");
        }
        cl.show(this, s );
    }

    public void setSingleplayer(Player singleplayer) {
        Singleplayer = singleplayer;
    }

    public Player getSingleplayer() {
        return Singleplayer;
    }
}
