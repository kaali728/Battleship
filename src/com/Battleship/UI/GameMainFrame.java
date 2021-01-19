package com.Battleship.UI;

import com.Battleship.Constants.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * The type Game main frame.
 */
public class GameMainFrame extends JFrame {
    /**
     * Instantiates a new Game main frame.
     */
    public GameMainFrame() {
        init();
    }

    /**
     * Standard setting e.g. set a title, closes the panel etc, make panel visible.
     */
    public void init() {
        add(new GamePanel());
        setTitle(Constants.TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Schließt das Fenster
        pack();
        //ICON setzen
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/battleshipicon.png")).getImage());
        setLocationRelativeTo(null); //Setzt das Fenster in der Mitte
        setVisible(true);
        setMinimumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
    }

}
