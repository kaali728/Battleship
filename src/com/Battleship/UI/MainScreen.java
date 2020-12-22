package com.Battleship.UI;

import com.Battleship.Constants.Constants;
import com.Battleship.Image.Image;
import com.Battleship.Image.ImageFactory;
import com.Battleship.Main.Game;
import com.Battleship.Player.Player;
import com.Battleship.Sound.Sound;
import com.Battleship.Sound.SoundFactory;
import com.Battleship.SpielstandLaden.GameLoad;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainScreen extends JPanel {
    JButton singleplayer;
    JButton multiplayer;
    JButton spielstandLaden;
    JButton soundButton;
    GamePanel mainPanel;
    private SoundFactory sound;
    boolean startSound = true;
    private ImageIcon background;
    private ImageIcon ship;
    private GameLoad load;
    private JLabel shiplabel;

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
        load = new GameLoad();

        //this.background = ImageFactory.createImage(Image.BACKGROUND);
        this.ship = ImageFactory.createImage(Image.SHIP);
        shiplabel = new JLabel(ship);

    }

    void initLayout() {
        Font  titlefont  = null;
        try {
            titlefont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Fonts/Road_Rage.otf")).deriveFont(100f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(titlefont);
        } catch (FontFormatException e) {
            e.printStackTrace();
            titlefont  = new Font(Font.SANS_SERIF,  Font.BOLD, 100);
        } catch (IOException e) {
            e.printStackTrace();
            titlefont  = new Font(Font.SANS_SERIF,  Font.BOLD, 100);

        }



        Font  buttonfont  = new Font(Font.SANS_SERIF,  Font.BOLD, 25);

        JPanel titlePanel = new JPanel();
        //titlePanel.setBounds(100,100,Constants.WIDTH, Constants.HEIGHT);
        titlePanel.setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT-100));
        titlePanel.setBackground(Color.black);



        JLabel titleLable = new JLabel(" Battleship ");
        titleLable.setForeground(new Color	(212, 214, 207));
        titleLable.setFont(titlefont);
        titleLable.setVerticalAlignment(SwingConstants.CENTER);
        titleLable.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLable);



        singleplayer.setMaximumSize(new Dimension(150,50));
        multiplayer.setMaximumSize(new Dimension(150,50));
        spielstandLaden.setMaximumSize(new Dimension(150,50));
        soundButton.setMaximumSize(new Dimension(150,50));

        Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));



        singleplayer.setBackground(Color.black);
        singleplayer.setForeground(Color.WHITE);
        singleplayer.setFont(buttonfont);
        singleplayer.setFocusPainted(false);
        singleplayer.setMargin(new Insets(0, 0, 0, 0));
        singleplayer.setBorder(b);

        multiplayer.setBackground(Color.black);
        multiplayer.setForeground(Color.WHITE);
        multiplayer.setFont(buttonfont);
        multiplayer.setFocusPainted(false);
        multiplayer.setMargin(new Insets(0, 0, 0, 0));
        multiplayer.setBorder(b);



        spielstandLaden.setBackground(Color.black);
        spielstandLaden.setForeground(Color.WHITE);
        spielstandLaden.setFont(buttonfont);
        spielstandLaden.setFocusPainted(false);
        spielstandLaden.setMargin(new Insets(0, 0, 0, 0));
        spielstandLaden.setBorder(b);


        soundButton.setBackground(Color.black);
        soundButton.setForeground(Color.WHITE);
        soundButton.setFont(buttonfont);
        soundButton.setFocusPainted(false);
        soundButton.setMargin(new Insets(0, 0, 0, 0));
        soundButton.setBorder(b);



        multiplayer.addActionListener(
                (e) -> {
                    mainPanel.setSound(sound);
                    mainPanel.changeScreen("multiplayer");
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
                    load.readFile(null);
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
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                singleplayer.setBorder(b);
            }
        });

        multiplayer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                multiplayer.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                multiplayer.setBorder(b);
            }
        });

        spielstandLaden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                spielstandLaden.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                spielstandLaden.setBorder(b);
            }
        });

        soundButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                soundButton.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                soundButton.setBorder(b);
            }
        });




        JPanel buttonPanel1 =  new JPanel();
        buttonPanel1.setBackground(Color.black);
        buttonPanel1.setSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));





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

        JPanel shipPanel = new JPanel();
        shipPanel.setBackground(Color.black);
        shipPanel.setSize(Constants.WIDTH, Constants.HEIGHT);
        shipPanel.setAlignmentX(CENTER_ALIGNMENT);
        shipPanel.setAlignmentY(CENTER_ALIGNMENT);

        shipPanel.add(shiplabel);

        add(titlePanel);
        add(buttonPanel1);
        add(shipPanel);
    }
    // Für den Hintergrund auf einem JPanel muss man diese Funktion überschreiben.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(ship.getImage(), 0, 0, getWidth()/2, getHeight()/2, null);
    }
}
