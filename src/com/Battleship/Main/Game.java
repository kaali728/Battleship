package com.Battleship.Main;


import com.Battleship.UI.GameMainFrame;
import com.Battleship.UI.GamePanel;

import javax.swing.*;
import javax.swing.plaf.multi.MultiPanelUI;
import java.awt.*;
import java.io.IOException;

/**
 * The type Game.
 */
public class Game {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(GameMainFrame::new);
    }
}