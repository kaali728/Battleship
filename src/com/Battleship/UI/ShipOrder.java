package com.Battleship.UI;

import com.Battleship.Constants.Constants;

import javax.swing.*;
import java.awt.*;

public class ShipOrder extends JPanel {
    ShipOrder(){
        initLayout();
    }
    void initLayout() {
        setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        setBackground(Color.green);
    }
}
