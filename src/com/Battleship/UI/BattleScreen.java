package com.Battleship.UI;

import com.Battleship.Model.Board;
import com.Battleship.Model.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BattleScreen extends Panel {
    GamePanel mainPanel;
    JButton endGame;
    JButton saveGame;
    Board playerBoard;
    Board enemyBoard;
    JLabel enemyLabel;
    JLabel playerLabel;


    BattleScreen(GamePanel mainPanel){
        this.mainPanel = mainPanel;
        initvar();
        initlayout();
    }

    public void initvar(){
       endGame = new JButton("End Game");
       saveGame = new JButton("Save Game");
       enemyLabel = new JLabel("Enemy");
       playerLabel = new JLabel("Player");
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
       playerBoard = new Board(this.mainPanel.getSingleplayer().getFieldsize(), this.mainPanel.getSingleplayer().getFleet(),this.mainPanel.getGameState(), true);
       playerBoard.setMyShip();
       this.mainPanel.getEnemyPlayer().setEnemyShip();
       enemyBoard = new Board(this.mainPanel.getEnemyPlayer().getFieldsize(), this.mainPanel.getEnemyPlayer().getFleet(), this.mainPanel.getGameState(), false);
       enemyBoard.setMyShip();
    }

    public void initlayout(){
       add(endGame);
       add(saveGame);
        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            Box vhob1= Box.createVerticalBox();{
            vhob1.add(enemyLabel);
            vhob1.add(enemyBoard);
            }
            hbox.add(vhob1);
            hbox.add(Box.createHorizontalStrut(100));
            Box vhob2= Box.createVerticalBox();{
            vhob2.add(playerLabel);
            vhob2.add(playerBoard);
            }
            hbox.add(vhob2);
            hbox.add(Box.createHorizontalStrut(10));

        }
        add(hbox);

    }
}
