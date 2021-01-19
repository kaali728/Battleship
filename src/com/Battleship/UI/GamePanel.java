package com.Battleship.UI;

import com.Battleship.AIvsAI.*;
import com.Battleship.Constants.Constants;
import com.Battleship.Model.Field;
import com.Battleship.Player.AINetworkPlayer;
import com.Battleship.Player.AIPlayer;
import com.Battleship.Player.NetworkPlayer;
import com.Battleship.Player.Player;
import com.Battleship.Sound.Sound;
import com.Battleship.Sound.SoundFactory;
import com.Battleship.SpielstandLaden.GameObj;


import javax.swing.*;
import java.awt.*;

/**
 * The type Game panel.
 */
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

    /**
     * Instantiates a new Game panel.
     */
    public GamePanel() {
        sound = new SoundFactory();
        sound.load(Sound.MAINSOUND);
        sound.play(SoundFactory.sound);
        initLayout();
    }

    /**
     * Set the Layout for the panel
     */
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

    /**
     * Change screen to battle.
     *
     * @param s the s
     */
    public void changeScreen(String s) {
        if (s == "battlefield") {
            add(new ShipSetupScreen(this), "battlefield");
        }

        if(gameState.equals("battle")){
            add(new BattleScreen(this), "battle");
        }
      
        cl.show(this, s);
    }

    /**
     * Change screen.
     *
     * @param s               the s
     * @param port            the port
     * @param fieldsize       the fieldsize
     * @param carrierCount    the carrier count
     * @param battleshipCount the battleship count
     * @param submarineCount  the submarine count
     * @param destroyerCount  the destroyer count
     */
    public void changeScreen(String s, int port, int fieldsize,  int carrierCount,int  battleshipCount,int  submarineCount,int destroyerCount ) {
        add(new ServerScreen(port, fieldsize, carrierCount, battleshipCount, submarineCount, destroyerCount, this), "serverScreen");
        cl.show(this, s);
    }

    /**
     * Change screen to load a Multiplayer Game
     *
     * @param s
     * @param port
     * @param filename
     * @param loadGame
     */

    public void changeScreen(String s, int port, String filename, GameObj loadGame) {
        add(new LoadMultiPlayerScreen(port,filename, loadGame, this), "loadMultiPlayer");
        cl.show(this, s);
    }

    /**
     * Change Client screen for Multiplayer.
     *
     * @param s       the s
     * @param address the address
     * @param port    the port
     */

    public void changeScreen(String s, String address, int port) {
        add(new ClientScreen(address, port, this), "clientScreen");
        cl.show(this, s);
    }

    /**
     * AI change screen for the Server.
     *
     * @param s                 the s
     * @param aiport            the aiport
     * @param aifieldsize       the aifieldsize
     * @param aicarrierCount    the aicarrier count
     * @param aibattleshipCount the aibattleship count
     * @param aisubmarineCount  the aisubmarine count
     * @param aidestroyerCount  the aidestroyer count
     * @param aiPlayer          the ai player
     */
    public void aichangeScreen(String s, int aiport, int aifieldsize,  int aicarrierCount,int  aibattleshipCount,int  aisubmarineCount,int aidestroyerCount, AINetworkPlayer aiPlayer ) {
        add(new AIServerScreen(aiport, aifieldsize, aicarrierCount, aibattleshipCount, aisubmarineCount, aidestroyerCount, this, aiPlayer), "aiserverscreen");
        cl.show(this, s);
    }

    /**
     * AI change screen for the Client.
     *
     * @param s         the s
     * @param aiaddress the aiaddress
     * @param aiport    the aiport
     * @param aiPlayer  the ai player
     */
    public void aichangeScreen(String s, String aiaddress, int aiport, AINetworkPlayer aiPlayer) {
        add(new AIClientScreen(aiaddress, aiport, this, aiPlayer), "aiclientscreen");
        cl.show(this, s);
    }

    /**
     * Gets singleplayer.
     *
     * @return the singleplayer
     */
    public Player getSingleplayer() {
        return Singleplayer;
    }

    /**
     * Get enemy player ai player.
     *
     * @return the ai player
     */
    public AIPlayer getEnemyPlayer(){ return EnemyPlayer; }

    /**
     * Gets network player.
     *
     * @return the network player
     */
    public NetworkPlayer getNetworkPlayer() {
        return NetworkPlayer;
    }

    /**
     * Gets game state.
     *
     * @return the game state
     */
    public String getGameState() {
        return gameState;
    }

    /**
     * Sets game state.
     *
     * @param gameState the game state
     */
    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    /**
     * Set sound.
     *
     * @param sound the sound
     */
    public void setSound(SoundFactory sound){
        this.sound = sound;
    }

    /**
     * Gets sound.
     *
     * @return the sound
     */
    public SoundFactory getSound() {
        return sound;
    }

    /**
     * Is gameload boolean.
     *
     * @return the boolean
     */
    public boolean isGameload() {
        return Gameload;
    }

    /**
     * Sets gameload.
     *
     * @param gameload the gameload
     */
    public void setGameload(boolean gameload) {
        Gameload = gameload;
    }

    /**
     * Get loaded player button field [ ] [ ].
     *
     * @return the field [ ] [ ]
     */
    public Field[][] getLoadedPlayerButton() {
        return loadedPlayerButton;
    }

    /**
     * Sets loaded player button.
     *
     * @param loadedPlayerButton the loaded player button
     */
    public void setLoadedPlayerButton(Field[][] loadedPlayerButton) {
        this.loadedPlayerButton = loadedPlayerButton;
    }

    /**
     * Get loaded enemy button field [ ] [ ].
     *
     * @return the field [ ] [ ]
     */
    public Field[][] getLoadedEnemyButton() {
        return loadedEnemyButton;
    }

    /**
     * Sets loaded enemy button.
     *
     * @param loadedEnemyButton the loaded enemy button
     */
    public void setLoadedEnemyButton(Field[][] loadedEnemyButton) {
        this.loadedEnemyButton = loadedEnemyButton;
    }

    /**
     * Gets loaded player health.
     *
     * @return the loaded player health
     */
    public int getLoadedPlayerHealth() {
        return loadedPlayerHealth;
    }

    /**
     * Sets loaded player health.
     *
     * @param loadedPlayerHealth the loaded player health
     */
    public void setLoadedPlayerHealth(int loadedPlayerHealth) {
        this.loadedPlayerHealth = loadedPlayerHealth;
    }

    /**
     * Gets loaded enemy health.
     *
     * @return the loaded enemy health
     */
    public int getLoadedEnemyHealth() {
        return loadedEnemyHealth;
    }

    /**
     * Sets loaded enemy health.
     *
     * @param loadedEnemyHealth the loaded enemy health
     */
    public void setLoadedEnemyHealth(int loadedEnemyHealth) {
        this.loadedEnemyHealth = loadedEnemyHealth;
    }

    /**
     * Gets ai online.
     *
     * @return the ai online
     */
    public AINetworkPlayer getAiOnline() {
        return AiOnline;
    }

}
