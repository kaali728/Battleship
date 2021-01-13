package com.Battleship.UI;

import com.Battleship.AIvsAI.*;
import com.Battleship.Constants.Constants;
import com.Battleship.Model.Field;
import com.Battleship.Player.AINetworkPlayer;
import com.Battleship.Player.AIPlayer;
import com.Battleship.Player.NetworkPlayer;
import com.Battleship.Player.Player;
import com.Battleship.Sound.SoundFactory;


import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private CardLayout cl;
    private Player Singleplayer = new Player();
    private AIPlayer EnemyPlayer = new AIPlayer();
    private AINetworkPlayer AiOnline = new AINetworkPlayer();
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
        add(new AIScreen(this), "aimulti");
        add(new AIServerSetup(this), "aiserversetup");
        add(new AIClientSetup(this), "aiclientsetup");
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

    public void aichangeScreen(String s, int aiport, int aifieldsize,  int aicarrierCount,int  aibattleshipCount,int  aisubmarineCount,int aidestroyerCount, AINetworkPlayer aiPlayer ) {
        add(new AIServerScreen(aiport, aifieldsize, aicarrierCount, aibattleshipCount, aisubmarineCount, aidestroyerCount, this, aiPlayer), "aiserverscreen");
        cl.show(this, s);
    }

    public void aichangeScreen(String s, String aiaddress, int aiport, AINetworkPlayer aiPlayer) {
        add(new AIClientScreen(aiaddress, aiport, this, aiPlayer), "aiclientscreen");
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

    public AINetworkPlayer getAiOnline() {
        return AiOnline;
    }

    public void setAiOnline(AINetworkPlayer aiOnline) {
        AiOnline = aiOnline;
    }
}
