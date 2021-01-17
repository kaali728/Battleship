package com.Battleship.UI;

import com.Battleship.Model.Board;
import com.Battleship.Model.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The type Ship setup screen.
 */
public class ShipSetupScreen extends Panel {
    /**
     * The Main panel.
     */
    GamePanel mainPanel;
    /**
     * The Back.
     */
    JButton back;
    /**
     * The Vertical.
     */
    JButton vertical;
    /**
     * The Postion board.
     */
    Board postionBoard;
    /**
     * The Play.
     */
    JButton play;


    /**
     * Instantiates a new Ship setup screen.
     *
     * @param mainPanel the main panel
     */
    ShipSetupScreen(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        initvar();
        initlayout();
    }

    /**
     * Initvar.
     */
    public void initvar() {
        back = new JButton("Back");
        vertical = new JButton("vertical");
        play = new JButton("Play");

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.getSingleplayer().setFleet(postionBoard.getFleet());
                boolean isok = false;
                for (Ship s: mainPanel.getSingleplayer().getFleet()) {
                    if(s.getRow() != -1 && s.getColumn() != -1){
                        isok = true;
                    }else{
                        isok = false;
                        break;
                    }
                }
                if(isok){
                    mainPanel.setGameState("battle");
                    mainPanel.changeScreen("battle");
                }else{
                    postionBoard.makealarm();
                }

            }
        });


        vertical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(postionBoard != null) {
                    if (postionBoard.isHorizontal()) {
                        vertical.setText("horizontal");
                        postionBoard.setHorizontal(!postionBoard.isHorizontal());
                    } else {
                        vertical.setText("vertical");
                        postionBoard.setHorizontal(!postionBoard.isHorizontal());
                    }
                }
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setGameState("start");
                mainPanel.changeScreen("singleplayer");
            }
        });
    }

    /**
     * Initlayout.
     */
    public void initlayout() {
        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            int sizefield = this.mainPanel.getSingleplayer().getFieldsize();
            ArrayList<Ship> fleet = this.mainPanel.getSingleplayer().getFleet();
            postionBoard = new Board(sizefield, fleet,this.mainPanel.getGameState());
            hbox.add(postionBoard);
            hbox.add(Box.createHorizontalStrut(10));
        }
        add(hbox);
        add(vertical);
        add(back);
        add(play);
        hbox.setAlignmentY(Component.CENTER_ALIGNMENT);
    }
}
