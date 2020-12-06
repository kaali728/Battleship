package com.Battleship.UI;

import com.Battleship.Constants.Constants;
import com.Battleship.Player.AIPlayer;
import com.Battleship.Player.Player;
import com.Battleship.Sound.SoundFactory;


import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private CardLayout cl;
    private Player Singleplayer = new Player();
    private AIPlayer EnemyPlayer = new AIPlayer();
    private SoundFactory sound;

    // start - singleplayer - multiplayer - setzen - battle
    private String gameState;
    // Hintergrundbild
    public GamePanel() {
        initLayout();
    }

    void initLayout() {
        cl = new CardLayout();
        setLayout(cl);
        this.gameState = "start";
        setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        add(new MainScreen(this), "main");
        add(new SinglePlayerScreen(this), "singleplayer");
        cl.show(this, "main");
    }
    public void changeScreen(String s){
        if(s == "battlefield"){
            add(new ShipSetupScreen(this), "battlefield");
        }
        if(gameState.equals("battle")){
            add(new BattleScreen(this), "battle");
        }
        cl.show(this, s );
    }

    public Player getSingleplayer() {
        return Singleplayer;
    }

    public AIPlayer getEnemyPlayer(){ return EnemyPlayer; }


    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public void setSound(SoundFactory sound){
        this.sound = sound;
    }

    public SoundFactory getSound() {
        return sound;
    }
}
