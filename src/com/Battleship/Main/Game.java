package com.Battleship.Main;

//import com.Battleship.UI.CLayout;
import com.Battleship.UI.GameMainFrame;
import com.Battleship.UI.GamePanel;

import javax.swing.*;
import javax.swing.plaf.multi.MultiPanelUI;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        EventQueue.invokeLater(GameMainFrame::new);
    }
}