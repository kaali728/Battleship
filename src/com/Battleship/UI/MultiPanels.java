package com.Battleship.UI;

import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiPanels extends JFrame implements ActionListener {

    JPanel panel1;
    JPanel GamePanel;
    JPanel MenuPanel;

    JButton click = new JButton("Click");
    CardLayout cardLayout;

    MultiPanels(){
        cardLayout = new CardLayout();
        panel1 = new JPanel(cardLayout);
        panel1.setBackground(Color.red);
        panel1.add(click);
        GamePanel = new JPanel();
        MenuPanel = new JPanel();
        panel1.add(GamePanel, "game");
        panel1.add(MenuPanel, "menu");


        click.addActionListener(this);

        add(panel1);
        add(click, BorderLayout.SOUTH);
        pack();
        setSize(800,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameOn();
    }
    public void gameOn(){
        cardLayout.show(panel1, "panel");
    }
}
