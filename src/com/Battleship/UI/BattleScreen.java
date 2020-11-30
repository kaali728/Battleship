package com.Battleship.UI;

import com.Battleship.Model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BattleScreen extends Panel {
    GamePanel mainPanel;
    JButton endGame;
    JButton saveGame;
    Board playerBoard;
    Board enemyBoard;
    GridBagLayout layout;
    GridBagConstraints gbc;


    BattleScreen(GamePanel mainPanel){
        this.mainPanel = mainPanel;
        initvar();
        initlayout();
    }

    public void initvar(){
       endGame = new JButton("End Game");
       saveGame = new JButton("Save Game");
       layout = new GridBagLayout();
       gbc = new GridBagConstraints();
       endGame.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               mainPanel.setGameState("start");
               mainPanel.changeScreen("main");
           }
       });
       saveGame.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               //System.out.println(playerBoard + " " + enemyBoard);
           }
       });
       playerBoard = new Board(this.mainPanel.getSingleplayer().getFieldsize(), this.mainPanel.getSingleplayer().getFleet(),this.mainPanel.getGameState());
       enemyBoard = new Board(this.mainPanel.getSingleplayer().getFieldsize(), this.mainPanel.getEnemyPlayer().getFleet(), this.mainPanel.getGameState());
    }

    public void initlayout(){
        setLayout(layout);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(endGame, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(saveGame, gbc);


    }
}
