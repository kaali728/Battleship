package com.Battleship.UI;

import com.Battleship.Image.Image;
import com.Battleship.Image.ImageFactory;

import javax.swing.*;
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
        this.background = ImageFactory.createImage(Image.BACKGROUND);

    }

    void initLayout() {
        Box vbox = Box.createVerticalBox();
        {
            singleplayer.setMaximumSize(new Dimension(150,50));
            vbox.add(Box.createGlue());
            multiplayer.setMaximumSize(new Dimension(150,50));
            vbox.add(Box.createGlue());
            spielstandLaden.setMaximumSize(new Dimension(150,50));

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
            vbox.add(singleplayer);
            vbox.add(multiplayer);
            vbox.add(spielstandLaden);
        }
        vbox.add(Box.createVerticalStrut(100));
        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(vbox);
    }
    // Für den Hintergrund auf einem JPanel muss man diese Funktion überschreiben.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
