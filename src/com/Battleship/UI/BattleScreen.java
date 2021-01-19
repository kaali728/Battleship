package com.Battleship.UI;

import com.Battleship.Model.Board;
import com.Battleship.Sound.SoundFactory;
import com.Battleship.SpielstandLaden.GameObj;
import com.Battleship.SpielstandLaden.SpeichernUnterClass;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Battle screen.
 */
public class BattleScreen extends Panel implements ActionListener {
    /**
     * The Main panel.
     */
    GamePanel mainPanel;
    /**
     * The End game.
     */
    JButton endGame;
    /**
     * The Save game.
     */
    JButton saveGame;
    /**
     * The Player board.
     */
    Board playerBoard;
    /**
     * The Enemy board.
     */
    Board enemyBoard;
    /**
     * The Enemy label.
     */
    JLabel enemyLabel;
    /**
     * The Player label.
     */
    JLabel playerLabel;
    /**
     * The Sound button.
     */
    JButton soundButton;
    private SoundFactory sound;
    /**
     * The Start sound.
     */
    boolean startSound = true;
    /**
     * The Speicher.
     */
    SpeichernUnterClass speicher;
    private GameObj save;

    /**
     * Instantiates a new Battle screen.
     *
     * @param mainPanel the main panel
     */
    BattleScreen(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        initvar();
        initlayout();
    }

    /**
     * Create a buttons for Sound to take it off
     * Create a button to End the game
     * Create a button to save the game
     */
    public void initvar() {
        sound = mainPanel.getSound();
        soundButton = new JButton("Sound");
        endGame = new JButton("End Game");
        saveGame = new JButton("Save Game");
        enemyLabel = new JLabel("Enemy");
        playerLabel = new JLabel("Player");


        playerBoard = new Board(this.mainPanel.getSingleplayer().getFieldsize(), this.mainPanel.getSingleplayer().getFleet(), this.mainPanel.getGameState(), true);

        enemyBoard = new Board(this.mainPanel.getEnemyPlayer().getFieldsize(), this.mainPanel.getEnemyPlayer().getFleet(), this.mainPanel.getGameState(), false, this.mainPanel.getEnemyPlayer(), playerBoard);
        if(this.mainPanel.isGameload()){
            playerBoard.setMyShip(this.mainPanel.getLoadedPlayerButton());
            enemyBoard.setMyShip(this.mainPanel.getLoadedEnemyButton());
            playerBoard.setAllHealthEnemy(this.mainPanel.getLoadedEnemyHealth());
            playerBoard.setAllHealthPlayer(this.mainPanel.getLoadedPlayerHealth());
            enemyBoard.setAllHealthEnemy(this.mainPanel.getLoadedEnemyHealth());
            enemyBoard.setAllHealthPlayer(this.mainPanel.getLoadedPlayerHealth());
            this.mainPanel.getEnemyPlayer().setEnemyBoard(enemyBoard);
        }else{
            playerBoard.setMyShip();
            this.mainPanel.getEnemyPlayer().setEnemyBoard(enemyBoard);
            this.mainPanel.getEnemyPlayer().setEnemyShip();
        }
        endGame.addActionListener(this);
        saveGame.addActionListener(this);

    }

    /**
     * Set layout.
     */
    public void initlayout() {
        setBackground(Color.black);
        Font  buttonfont  = new Font(Font.SANS_SERIF,  Font.BOLD, 19);
        Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));

        soundButton.addActionListener(this);

        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            Box vhob1 = Box.createVerticalBox();
            {
                vhob1.add(enemyLabel);
                enemyLabel.setBackground(Color.black);
                enemyLabel.setForeground(Color.WHITE);
                vhob1.add(enemyBoard);
            }
            hbox.add(vhob1);
            hbox.add(Box.createHorizontalStrut(100));
            Box vhob2 = Box.createVerticalBox();
            {
                vhob2.add(playerLabel);
                playerLabel.setBackground(Color.black);
                playerLabel.setForeground(Color.WHITE);
                vhob2.add(playerBoard);
            }
            hbox.add(vhob2);
            hbox.add(Box.createHorizontalStrut(10));

        }

        Box buttons = Box.createHorizontalBox();
        {
            endGame.setBackground(Color.black);
            endGame.setForeground(Color.WHITE);
            endGame.setFont(buttonfont);
            endGame.setFocusPainted(false);
            endGame.setMargin(new Insets(0, 0, 0, 0));
            endGame.setBorder(b);
            endGame.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                    endGame.setBorder(b);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                    endGame.setBorder(b);
                }
            });

            saveGame.setBackground(Color.black);
            saveGame.setForeground(Color.WHITE);
            saveGame.setFont(buttonfont);
            saveGame.setFocusPainted(false);
            saveGame.setMargin(new Insets(0, 0, 0, 0));
            saveGame.setBorder(b);
            saveGame.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                    saveGame.setBorder(b);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                    saveGame.setBorder(b);
                }
            });


            soundButton.setBackground(Color.black);
            soundButton.setForeground(Color.WHITE);
            soundButton.setFont(buttonfont);
            soundButton.setFocusPainted(false);
            soundButton.setMargin(new Insets(0, 0, 0, 0));
            soundButton.setBorder(b);
            soundButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                    soundButton.setBorder(b);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                    soundButton.setBorder(b);
                }
            });

            buttons.add(endGame);
            buttons.add(Box.createHorizontalStrut(15));
            buttons.add(saveGame);
            buttons.add(Box.createHorizontalStrut(15));
            buttons.add(soundButton);
        }

        Box vbox = Box.createVerticalBox();
        {
            vbox.add(buttons);
            vbox.add(hbox);
        }

        add(vbox);
    }

    /**
     * so that the buttons do what is expected of them
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == soundButton){
            if(startSound){
                sound.stop();
                soundButton.setText("Sound OFF");
                startSound = false;
            }else{
                sound.play(SoundFactory.sound);
                soundButton.setText("Sound");
                startSound = true;
            }
        }
        if(e.getSource() == saveGame){
            playerBoard.setFleet(this.mainPanel.getSingleplayer().getFleet());
            enemyBoard.setFleet(this.mainPanel.getEnemyPlayer().getFleet());
            speicher = new SpeichernUnterClass(playerBoard, enemyBoard);
            speicher.saveAs(null);
        }
        if(e.getSource() == endGame){
            mainPanel.getEnemyPlayer().endGame();
            this.mainPanel.setGameload(false);
            mainPanel.getSound().stop();
            mainPanel.setGameState("start");
            mainPanel.changeScreen("main");
        }
    }
}
