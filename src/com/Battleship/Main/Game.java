package com.Battleship.Main;

import com.Battleship.UI.GameMainFrame;

import java.awt.*;

public class Game {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new GameMainFrame();
        });
    }
}
