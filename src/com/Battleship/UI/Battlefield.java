package com.Battleship.UI;

import com.Battleship.Model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Battlefield extends Panel {
    GamePanel mainPanel;
    Battlefield(GamePanel mainPanel){
        this.mainPanel = mainPanel;
        initvar();
        initlayout();
    }
    public void initvar(){}
    public void initlayout(){
        Box hbox =  Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            hbox.add(new Board(5));
            hbox.add(Box.createHorizontalStrut(10));
        }
        add(hbox);
        hbox.setAlignmentY(Component.CENTER_ALIGNMENT);
    }


}
