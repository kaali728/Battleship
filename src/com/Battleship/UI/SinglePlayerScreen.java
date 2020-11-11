package com.Battleship.UI;

import com.Battleship.Player.Player;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinglePlayerScreen extends JPanel implements ChangeListener {
    GamePanel mainPanel;
    JButton back;
    JSlider slider;
    JLabel size;
    JButton play;
    int sizefield;
    Player singplayer;
    SinglePlayerScreen(GamePanel mainPanel){
        this.mainPanel = mainPanel;
        initvar();
        singplayer = mainPanel.getSingleplayer();
        initlayout();
    }
    public void initvar(){
        slider = new JSlider(5,30);
        back = new JButton("Back");
        play = new JButton("Set fleet");
        size = new JLabel();
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                singplayer.setFieldsize(slider.getValue());
                mainPanel.changeScreen("battlefield");
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.changeScreen("main");
            }
        });
    }
    public void initlayout(){
        setBackground(Color.white);
        add(back);
        Box vbox = Box.createVerticalBox();
        {
            // paint the ticks and tarcks
            slider.setPaintTrack(true);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.addChangeListener(this::stateChanged);
            slider.setMinorTickSpacing(5);
            slider.setMajorTickSpacing(25);
            size.setText("size=" + slider.getValue());

            vbox.add(slider);
            vbox.add(size);
        }
        vbox.add(Box.createVerticalStrut(100));
        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(vbox);
        add(play);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource().equals(slider)){
            size.setText("size= " + slider.getValue());
            this.sizefield = slider.getValue();
        }
    }
}
