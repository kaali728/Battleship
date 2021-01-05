package com.Battleship.UI;

import com.Battleship.Constants.Constants;
import com.Battleship.Model.Field;
import com.Battleship.Player.AIPlayer;
import com.Battleship.Player.NetworkPlayer;
import com.Battleship.Player.Player;
import com.Battleship.Sound.SoundFactory;


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GamePanel extends JPanel {
    private CardLayout cl;
    private Player Singleplayer = new Player();
    private AIPlayer EnemyPlayer = new AIPlayer();
    private NetworkPlayer NetworkPlayer = new NetworkPlayer();
    private SoundFactory sound;
    private boolean Gameload;
    private Field loadedPlayerButton[][];
    private Field loadedEnemyButton[][];
    private int loadedPlayerHealth;
    private int loadedEnemyHealth;


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

        add(new MultiplayerScreen(this), "multiplayer");
        add(new ServerSetupScreen(this), "serverSetupScreen");
        add(new ClientSetupScreen(this), "clientSetupScreen");

        cl.show(this, "main");
    }

    public void changeScreen(String s) {
        if (s == "battlefield") {
            add(new ShipSetupScreen(this), "battlefield");
        }

        if(gameState.equals("battle")){
            add(new BattleScreen(this), "battle");
        }
      
        cl.show(this, s);
    }

    public void changeScreen(String s, int port, int fieldsize,  int carrierCount,int  battleshipCount,int  submarineCount,int destroyerCount ) {
        add(new ServerScreen(port, fieldsize, carrierCount, battleshipCount, submarineCount, destroyerCount, this), "serverScreen");
        cl.show(this, s);
    }

    public void changeScreen(String s, String address, int port) {
        add(new ClientScreen(address, port, this), "clientScreen");
        cl.show(this, s);
    }

    public Player getSingleplayer() {
        return Singleplayer;
    }

    public AIPlayer getEnemyPlayer(){ return EnemyPlayer; }

    public NetworkPlayer getNetworkPlayer() {
        return NetworkPlayer;
    }

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

    public boolean isGameload() {
        return Gameload;
    }

    public void setGameload(boolean gameload) {
        Gameload = gameload;
    }

    public Field[][] getLoadedPlayerButton() {
        return loadedPlayerButton;
    }

    public void setLoadedPlayerButton(Field[][] loadedPlayerButton) {
        this.loadedPlayerButton = loadedPlayerButton;
    }

    public Field[][] getLoadedEnemyButton() {
        return loadedEnemyButton;
    }

    public void setLoadedEnemyButton(Field[][] loadedEnemyButton) {
        this.loadedEnemyButton = loadedEnemyButton;
    }

    public int getLoadedPlayerHealth() {
        return loadedPlayerHealth;
    }

    public void setLoadedPlayerHealth(int loadedPlayerHealth) {
        this.loadedPlayerHealth = loadedPlayerHealth;
    }

    public int getLoadedEnemyHealth() {
        return loadedEnemyHealth;
    }

    public void setLoadedEnemyHealth(int loadedEnemyHealth) {
        this.loadedEnemyHealth = loadedEnemyHealth;
    }

}
