package com.Battleship.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CLayout {
    JFrame frame = new JFrame("CardLayout");
    JPanel panelCont = new JPanel();
    JPanel panelFirst = new JPanel();
    JPanel panelSecond = new JPanel();
    JButton buttonOne = new JButton("Switch to second Panel");
    JButton buttonSecond = new JButton("Switch to first Panel");
    CardLayout c1 = new CardLayout();

    public CLayout() {
        panelCont.setLayout(c1);

        panelFirst.add(buttonOne);
        panelSecond.add(buttonSecond);
        panelFirst.setBackground(Color.BLUE);
        panelSecond.setBackground(Color.GREEN);

        panelCont.add(panelFirst, "1");
        panelCont.add(panelSecond, "2");
        c1.show(panelCont, "1");

        buttonOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.show(panelCont, "2");
            }
        });
        buttonSecond.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.show(panelCont, "1");
            }
        });
        frame.add(panelCont);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CLayout();
            }
        });
    }
}

