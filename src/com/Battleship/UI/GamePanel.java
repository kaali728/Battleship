package com.Battleship.UI;

import com.Battleship.Constants.Constants;
import com.Battleship.Image.Image;
import com.Battleship.Image.ImageFactory;


import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    //private CardLayout cl;
   // private static int currentCard = 1;

    // Hintergrundbild
    private ImageIcon background;

    public GamePanel() {
        initVars();
        initLayout();
    }

    // Initalisiert die Variablen.
    void initVars() {
        this.background = ImageFactory.createImage(Image.BACKGROUND);
    }

    void initLayout() {
//        cl = new CardLayout();
//        setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
//        setLayout(cl);
//       add(new StartScreen(), "1");
//        add(new ShipOrder(), "2");
//        cl.show(this, "" + (currentCard));
        setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        add(new MainScreen());
    }

    //public int getCurrentCard() {
        //return currentCard;
    //}


    //public void setCurrentCard(int currentCard) {
        //this.currentCard = currentCard;
    //}

    // Für den Hintergrund auf einem JPanel muss man diese Funktion überschreiben.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
