package com.Battleship.UI;

import com.Battleship.Constants.Constants;
import com.Battleship.Image.Image;
import com.Battleship.Image.ImageFactory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainScreen extends JPanel {
    JButton singleplayer;
    JButton multiplayer;
    JButton spielstandLaden;
    GamePanel mainPanel;
    private ImageIcon background;


    MainScreen(GamePanel gamePanel) {
        mainPanel = gamePanel;
        initVars();
        initLayout();
    }

    void initVars() {
        singleplayer = new JButton("Singleplayer");
        multiplayer = new JButton("Multiplayer");
        spielstandLaden = new JButton("Load Game");
        //this.background = ImageFactory.createImage(Image.BACKGROUND);

    }

    void initLayout() {
        Font  titlefont  = new Font(Font.SANS_SERIF,  Font.BOLD, 100);
        Font  buttonfont  = new Font(Font.SANS_SERIF,  Font.BOLD, 25);

        JPanel titlePanel = new JPanel();
        //titlePanel.setBounds(100,100,Constants.WIDTH, Constants.HEIGHT);
        titlePanel.setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT-500));
        titlePanel.setBackground(Color.black);
        JLabel titleLable = new JLabel("Battleship");
        titleLable.setForeground(new Color	(43,209,252));
        titleLable.setFont(titlefont);
        titlePanel.add(titleLable);
        titleLable.setVerticalAlignment(SwingConstants.CENTER);
        titleLable.setHorizontalAlignment(SwingConstants.CENTER);


        singleplayer.setMaximumSize(new Dimension(150,50));
        multiplayer.setMaximumSize(new Dimension(150,50));
        spielstandLaden.setMaximumSize(new Dimension(150,50));

        singleplayer.setBackground(Color.black);
        singleplayer.setForeground(Color.WHITE);
        singleplayer.setFont(buttonfont);
        singleplayer.setFocusPainted(false);
        singleplayer.setBorder(null);

        multiplayer.setBackground(Color.black);
        multiplayer.setForeground(Color.WHITE);
        multiplayer.setFont(buttonfont);
        multiplayer.setFocusPainted(false);
        multiplayer.setBorder(null);



        spielstandLaden.setBackground(Color.black);
        spielstandLaden.setForeground(Color.WHITE);
        spielstandLaden.setFont(buttonfont);
        spielstandLaden.setFocusPainted(false);
        spielstandLaden.setBorder(null);



        multiplayer.addActionListener(
                (e) -> {
                    mainPanel.changeScreen("main");
                }
        );
        singleplayer.addActionListener(
                (e) -> {
                    mainPanel.setGameState("singleplayer");
                    mainPanel.changeScreen("singleplayer");
                }
        );

        JPanel buttonPanel1 =  new JPanel();
        buttonPanel1.setBackground(Color.black);
        buttonPanel1.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        buttonPanel1.add(singleplayer);

        JPanel buttonPanel2 =  new JPanel();
        buttonPanel1.setBackground(Color.black);
        buttonPanel1.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        buttonPanel1.add(multiplayer);


        JPanel buttonPanel3 =  new JPanel();
        buttonPanel1.setBackground(Color.black);
        buttonPanel1.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        buttonPanel1.add(spielstandLaden);



        add(titlePanel);
        add(buttonPanel1);
        add(buttonPanel2);
        add(buttonPanel3);
    }
    // Für den Hintergrund auf einem JPanel muss man diese Funktion überschreiben.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
