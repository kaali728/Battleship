package com.Battleship.UI;

import com.Battleship.Constants.Constants;


import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private CardLayout cl;

    // Hintergrundbild
    public GamePanel() {
        initLayout();
    }

    void initLayout() {
        cl = new CardLayout();
        setLayout(cl);
        setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        add(new MainScreen(this), "main");
        add(new SinglePlayerScreen(this), "singleplayer");
        add(new ShipSetupScreen(this), "battlefield");
        cl.show(this, "battlefield");
    }
    public void changeScreen(String s){
        cl.show(this, s );
    }
}
