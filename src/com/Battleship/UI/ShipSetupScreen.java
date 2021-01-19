package com.Battleship.UI;

import com.Battleship.Model.Board;
import com.Battleship.Model.Ship;

import javax.swing.*;
import javax.swing.border.Border;
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
        setBackground(Color.black);
        Font  buttonfont  = new Font(Font.SANS_SERIF,  Font.BOLD, 19);
        Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));

        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            int sizefield = this.mainPanel.getSingleplayer().getFieldsize();
            ArrayList<Ship> fleet = this.mainPanel.getSingleplayer().getFleet();
            postionBoard = new Board(sizefield, fleet,this.mainPanel.getGameState());
            postionBoard.setBackground(Color.black);
            hbox.add(postionBoard);
            hbox.add(Box.createHorizontalStrut(10));
        }
        add(hbox);

        vertical.setBackground(Color.black);
        vertical.setForeground(Color.WHITE);
        vertical.setFont(buttonfont);
        vertical.setFocusPainted(false);
        vertical.setMargin(new Insets(0, 0, 0, 0));
        vertical.setBorder(b);
        vertical.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                vertical.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                vertical.setBorder(b);
            }
        });
        back.setBackground(Color.black);
        back.setForeground(Color.WHITE);
        back.setFont(buttonfont);
        back.setFocusPainted(false);
        back.setMargin(new Insets(0, 0, 0, 0));
        back.setBorder(b);
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                back.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                back.setBorder(b);
            }
        });

        play.setBackground(Color.black);
        play.setForeground(Color.WHITE);
        play.setFont(buttonfont);
        play.setFocusPainted(false);
        play.setMargin(new Insets(0, 0, 0, 0));
        play.setBorder(b);
        play.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                play.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                play.setBorder(b);
            }
        });

        Box vbox = Box.createVerticalBox();
        {
            vbox.add(Box.createVerticalStrut(20));
            vbox.add(vertical);
            vbox.add(Box.createVerticalStrut(20));
            vbox.add(back);
            vbox.add(Box.createVerticalStrut(20));
            vbox.add(play);
            vbox.add(Box.createVerticalStrut(20));
        }
        add(vbox);
        hbox.setAlignmentY(Component.CENTER_ALIGNMENT);
    }
}
