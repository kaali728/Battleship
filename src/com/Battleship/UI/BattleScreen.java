package com.Battleship.UI;

import com.Battleship.Model.Board;
import com.Battleship.Sound.SoundFactory;
import com.Battleship.SpielstandLaden.GameObj;
import com.Battleship.SpielstandLaden.SpeichernUnterClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BattleScreen extends Panel implements ActionListener {
    GamePanel mainPanel;
    JButton endGame;
    JButton saveGame;
    Board playerBoard;
    Board enemyBoard;
    JLabel enemyLabel;
    JLabel playerLabel;
    JButton soundButton;
    private SoundFactory sound;
    boolean startSound = true;
    SpeichernUnterClass speicher;
    private GameObj save;

    BattleScreen(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        initvar();
        initlayout();
    }

    public void initvar() {
        sound = mainPanel.getSound();
        soundButton = new JButton("Sound");
        endGame = new JButton("End Game");
        saveGame = new JButton("Save Game");
        enemyLabel = new JLabel("Enemy");
        playerLabel = new JLabel("Player");


        //if load game => brauchen wir keine neue board sonder nur playerboard = newBoard und wir geben button[][] und restliche sachen
        playerBoard = new Board(this.mainPanel.getSingleplayer().getFieldsize(), this.mainPanel.getSingleplayer().getFleet(), this.mainPanel.getGameState(), true);

        //board.setshootet(load.playerboard); if isshot true => if is mark dann rot(if ship) if not
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

    public void initlayout() {
        soundButton.addActionListener(this);

        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            Box vhob1 = Box.createVerticalBox();
            {
                vhob1.add(enemyLabel);
                vhob1.add(enemyBoard);
            }
            hbox.add(vhob1);
            hbox.add(Box.createHorizontalStrut(100));
            Box vhob2 = Box.createVerticalBox();
            {
                vhob2.add(playerLabel);
                vhob2.add(playerBoard);
            }
            hbox.add(vhob2);
            hbox.add(Box.createHorizontalStrut(10));

        }

        Box buttons = Box.createHorizontalBox();
        {
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
            mainPanel.setGameState("start");
            mainPanel.changeScreen("main");
        }
    }
}
