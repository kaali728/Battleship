package com.Battleship.UI;

import com.Battleship.Constants.Constants;
import com.Battleship.Player.Player;


import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private CardLayout cl;
    private Player Singleplayer = new Player();
    // start - singleplayer - multiplayer - setzen - battle
    private String gameState;
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
        this.gameState = "start";
        cl.show(this, "main");
    }
    public void changeScreen(String s){
        if(s == "battlefield"){
            add(new ShipSetupScreen(this), "battlefield");
        }
        cl.show(this, s );
    }

    public Player getSingleplayer() {
        return Singleplayer;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }
}
