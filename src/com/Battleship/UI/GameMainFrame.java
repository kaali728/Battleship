package com.Battleship.UI;

import com.Battleship.Constants.Constants;
import com.Battleship.Image.Image;
import com.Battleship.Image.ImageFactory;

import javax.swing.*;

public class GameMainFrame extends JFrame {
    public GameMainFrame() {
        init();
    }

    public void init() {
        add(new GamePanel());
        setTitle(Constants.TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Schließt das Fenster
        pack();
        //ICON setzen
        setIconImage(ImageFactory.createImage(Image.ICON).getImage());
        setLocationRelativeTo(null); //Setzt das Fenster in der Mitte
        setVisible(true);
    }

}
