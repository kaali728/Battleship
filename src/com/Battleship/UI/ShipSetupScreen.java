package com.Battleship.UI;

import com.Battleship.Model.Board;
import com.Battleship.Player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShipSetupScreen extends Panel {
    GamePanel mainPanel;
    JButton back;


    ShipSetupScreen(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        initvar();
        initlayout();
    }

    public void initvar() {
        back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.changeScreen("main");
            }
        });
    }

    public void initlayout() {
        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            int sizefield = this.mainPanel.getSingleplayer().getFieldsize();
            hbox.add(new Board(sizefield));
            hbox.add(Box.createHorizontalStrut(10));
        }
        add(hbox);
        add(back);
        hbox.setAlignmentY(Component.CENTER_ALIGNMENT);
    }
}
