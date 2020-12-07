package com.Battleship.UI;

import SpielstandLaden.SpeichernUnterClass;
import com.Battleship.Constants.Constants;
import com.Battleship.Sound.Sound;
import com.Battleship.Sound.SoundFactory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainScreen extends JPanel {
    JButton singleplayer;
    JButton multiplayer;
    JButton spielstandLaden;
    JButton soundButton;
    GamePanel mainPanel;
    private SoundFactory sound;
    boolean startSound = true;
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
        soundButton = new JButton("Sound");
        sound = new SoundFactory();
        sound.load(Sound.MAINSOUND);
        sound.play(SoundFactory.sound);
        //this.background = ImageFactory.createImage(Image.BACKGROUND);

    }

    void initLayout() {
        Font  titlefont  = new Font(Font.SANS_SERIF,  Font.BOLD, 100);
        Font  buttonfont  = new Font(Font.SANS_SERIF,  Font.BOLD, 25);

        JPanel titlePanel = new JPanel();
        //titlePanel.setBounds(100,100,Constants.WIDTH, Constants.HEIGHT);
        titlePanel.setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT-100));
        titlePanel.setBackground(Color.black);



        JLabel titleLable = new JLabel("Battleship");
        titleLable.setForeground(new Color	(43,209,252));
        titleLable.setFont(titlefont);
        titleLable.setVerticalAlignment(SwingConstants.CENTER);
        titleLable.setHorizontalAlignment(SwingConstants.CENTER);

        titlePanel.add(titleLable);



        singleplayer.setMaximumSize(new Dimension(150,50));
        multiplayer.setMaximumSize(new Dimension(150,50));
        spielstandLaden.setMaximumSize(new Dimension(150,50));
        soundButton.setMaximumSize(new Dimension(150,50));




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


        soundButton.setBackground(Color.black);
        soundButton.setForeground(Color.WHITE);
        soundButton.setFont(buttonfont);
        soundButton.setFocusPainted(false);
        soundButton.setBorder(null);



        multiplayer.addActionListener(
                (e) -> {
                    mainPanel.setSound(sound);
                    mainPanel.changeScreen("main");
                }
        );
        singleplayer.addActionListener(
                (e) -> {
                    mainPanel.setSound(sound);
                    mainPanel.setGameState("singleplayer");
                    mainPanel.changeScreen("singleplayer");
                }
        );

        spielstandLaden.addActionListener(
                (e) -> {
                }
        );


        soundButton.addActionListener(
                (e) -> {
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
        );

        singleplayer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                singleplayer.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                singleplayer.setBorder(null);
            }
        });

        multiplayer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                multiplayer.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                multiplayer.setBorder(null);
            }
        });

        spielstandLaden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                spielstandLaden.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                spielstandLaden.setBorder(null);
            }
        });

        soundButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                soundButton.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                soundButton.setBorder(null);
            }
        });




        JPanel buttonPanel1 =  new JPanel();
        buttonPanel1.setBackground(Color.black);
        buttonPanel1.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));





        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        Box vBox = Box.createVerticalBox();
        vBox.setBackground(Color.black);
        vBox.setSize(Constants.WIDTH, Constants.HEIGHT);
        vBox.setAlignmentX(CENTER_ALIGNMENT);
        vBox.setAlignmentY(CENTER_ALIGNMENT);
        {
            vBox.add(singleplayer);
            vBox.add(Box.createVerticalStrut(20));
            vBox.add(multiplayer);
            vBox.add(Box.createVerticalStrut(20));
            vBox.add(spielstandLaden);
            vBox.add(Box.createVerticalStrut(20));
            vBox.add(soundButton);

        }
        buttonPanel1.add(vBox);
        add(titlePanel);
        add(buttonPanel1);
    }
    // Für den Hintergrund auf einem JPanel muss man diese Funktion überschreiben.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
