package com.Battleship.UI;

import com.Battleship.Constants.Constants;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private CardLayout cl;
    private static int currentCard = 1;

    public GamePanel(){initLayout(); }
    void initLayout(){
        cl = new CardLayout();
        setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        setLayout(cl);
        add(new StartScreen(), "1");
        add(new ShipOrder(), "2");
        cl.show(this,"" + (currentCard));


    }

    public int getCurrentCard() {
        return currentCard;
    }


    public void setCurrentCard(int currentCard) {
        this.currentCard = currentCard;
    }
}
