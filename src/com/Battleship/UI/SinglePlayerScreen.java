package com.Battleship.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinglePlayerScreen extends JPanel implements ActionListener {
    GamePanel mainPanel;
    JButton back;
    JSlider slider;
    JLabel size;
    SinglePlayerScreen(GamePanel mainPanel){
        this.mainPanel = mainPanel;
        initvar();
        initlayout();
    }
    public void initvar(){
        slider = new JSlider(5,30);
        back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.changeScreen("main");
            }
        });
    }
    public void initlayout(){
        setBackground(Color.BLACK);
        add(back);
        Box vbox = Box.createVerticalBox();
        {
            // paint the ticks and tarcks
            slider.setPaintTrack(true);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);

            slider.setMinorTickSpacing(5);
            slider.setMajorTickSpacing(25);

            add(slider);

        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
