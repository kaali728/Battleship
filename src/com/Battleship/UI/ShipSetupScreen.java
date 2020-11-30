package com.Battleship.UI;

import com.Battleship.Model.Board;
import com.Battleship.Model.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShipSetupScreen extends Panel {
    GamePanel mainPanel;
    JButton back;
    JButton vertical;
    Board postionBoard;
    JButton play;


    ShipSetupScreen(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        initvar();
        initlayout();
    }

    public void initvar() {
        back = new JButton("Back");
        vertical = new JButton("vertical");
        play = new JButton("Play");

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.getSingleplayer().setFleet(postionBoard.getFleet());
                mainPanel.setGameState("battle");
                mainPanel.changeScreen("battle");
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
