package com.Battleship.UI;

import com.Battleship.Constants.Constants;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    GamePanel(){
        initLayout();
    }
    void initLayout(){
        setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
    }
}
