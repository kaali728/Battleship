package com.Battleship.UI;

import com.Battleship.Constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    public GamePanel(){initLayout(); }
    void initLayout(){setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));}
}
