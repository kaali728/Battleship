package com.Battleship.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiPanels implements ActionListener {

    JFrame window = new JFrame();
    JPanel panel1 = new JPanel();
    JPanel panel2= new JPanel();
    JButton click = new JButton("Click");
    JLabel text = new JLabel("Button clicked");

    MultiPanels(){
        panel1.setBackground(Color.red);
        panel2.setBackground(Color.green);
        panel2.add(click);
        text.setForeground(Color.DARK_GRAY);
        panel1.add(text);
        text.setVisible(false);

        click.addActionListener(this);

        window.add(panel1, BorderLayout.CENTER);
        window.add(panel2, BorderLayout.PAGE_END);
        window.setSize(800,400);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        text.setVisible(true);
        panel1.setBackground(Color.blue);
        panel2.setBackground(Color.white);
    }
}
