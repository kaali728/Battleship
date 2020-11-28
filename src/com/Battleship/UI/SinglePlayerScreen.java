package com.Battleship.UI;

import com.Battleship.Model.Ship;
import com.Battleship.Player.Player;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SinglePlayerScreen extends JPanel implements ChangeListener {
    GamePanel mainPanel;
    JButton back;
    JSlider fieldslider;

    JSlider carrierSlider;
    JLabel carrierLabel;
    int carrierCounterMax;
    JSlider battleshipSlider;
    JLabel battleshipLabel;
    JSlider destroyerslider;
    JLabel destroyerLabel;
    JSlider submarineslider;
    JLabel submarineLabel;

    JLabel size;
    JButton play;
    int sizefield;
    Player singplayer;
    private int battleshipCount;
    private int destroyerCount;
    private int submarineCount;
    int carrierCount;


    private int submarineCounterMax;
    private int destroyerCounterMax;
    private int battleshipCounterMax;

    SinglePlayerScreen(GamePanel mainPanel){
        this.mainPanel = mainPanel;
        initvar();
        singplayer = mainPanel.getSingleplayer();
        initlayout();
    }
    public void initvar(){
        fieldslider = new JSlider(5,30);
        carrierSlider = new JSlider(1,1);
        carrierLabel = new JLabel();
        battleshipSlider = new JSlider(1,1);
        battleshipLabel = new JLabel();
        destroyerslider = new JSlider(1,1);
        destroyerLabel = new JLabel();
        submarineslider = new JSlider(1,1);
        submarineLabel = new JLabel();

        back = new JButton("Back");
        play = new JButton("Set fleet");
        size = new JLabel();
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Ship> fleet = new ArrayList<>();
                Ship s1 = new Ship("carrier", carrierCount);
                Ship s2 = new Ship("battleship", battleshipCount);
                Ship s3 = new Ship("submarine", submarineCount);
                Ship s4 = new Ship("destroyer", destroyerCount);
                fleet.add(s1);
                fleet.add(s2);
                fleet.add(s3);
                fleet.add(s4);
                singplayer.setFieldsize(fieldslider.getValue());
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
        Box vbox = Box.createVerticalBox();
        Component vspace = Box.createVerticalStrut(100);
        {
            // paint the ticks and tarcks
            fieldslider.setPaintTrack(true);
            fieldslider.setPaintTicks(true);
            fieldslider.setPaintLabels(true);
            fieldslider.addChangeListener(this::stateChanged);
            fieldslider.setMinorTickSpacing(5);
            fieldslider.setMajorTickSpacing(25);
            size.setText("size=" + fieldslider.getValue());



            carrierSlider.setPaintTrack(true);
            carrierSlider.setPaintTicks(true);
            carrierSlider.setPaintLabels(true);
            carrierSlider.addChangeListener(this::stateChanged);
            carrierSlider.setMinorTickSpacing(1);
            carrierSlider.setMajorTickSpacing(1);
            carrierLabel.setText("Count of Carrier length 5= " + carrierSlider.getValue());

            battleshipSlider.setPaintTrack(true);
            battleshipSlider.setPaintTicks(true);
            battleshipSlider.setPaintLabels(true);
            battleshipSlider.addChangeListener(this::stateChanged);
            battleshipSlider.setMinorTickSpacing(1);
            battleshipSlider.setMajorTickSpacing(1);
            battleshipLabel.setText("Count of Battleship length 4= " + battleshipSlider.getValue());

            destroyerslider.setPaintTrack(true);
            destroyerslider.setPaintTicks(true);
            destroyerslider.setPaintLabels(true);
            destroyerslider.addChangeListener(this::stateChanged);
            destroyerslider.setMinorTickSpacing(1);
            destroyerslider.setMajorTickSpacing(1);
            destroyerLabel.setText("Count of Destroyer length 2= " + destroyerslider.getValue());


            submarineslider.setPaintTrack(true);
            submarineslider.setPaintTicks(true);
            submarineslider.setPaintLabels(true);
            submarineslider.addChangeListener(this::stateChanged);
            submarineslider.setMinorTickSpacing(1);
            submarineslider.setMajorTickSpacing(1);
            submarineLabel.setText("Count of Submarine length 3= " + submarineslider.getValue());


            vbox.add(fieldslider);
            vbox.add(size);
            vbox.add(carrierSlider);
            vbox.add(carrierLabel);

            vbox.add(battleshipSlider);
            vbox.add(battleshipLabel);

            vbox.add(submarineslider);
            vbox.add(submarineLabel);

            vbox.add(destroyerslider);
            vbox.add(destroyerLabel);

            vbox.add(back);
            vbox.add(play);

        }
        vbox.add(Box.createVerticalStrut(100));
        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(vbox);

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource().equals(fieldslider)){
            size.setText("size= " + fieldslider.getValue());
            this.sizefield = fieldslider.getValue();
            this.carrierCounterMax = fieldslider.getValue()/ 6 == 0 ? 1 : fieldslider.getValue()/ 6;
            this.submarineCounterMax = fieldslider.getValue()/ 2;
            this.destroyerCounterMax = fieldslider.getValue()/ 1;
            this.battleshipCounterMax = fieldslider.getValue()/ 3;

            this.carrierSlider.setMaximum(carrierCounterMax);
            this.submarineslider.setMaximum(submarineCounterMax);
            this.destroyerslider.setMaximum(destroyerCounterMax);
            this.battleshipSlider.setMaximum(battleshipCounterMax);

        }
        if(e.getSource().equals(carrierSlider)){
            carrierLabel.setText("Count of Carrier length 5= " + carrierSlider.getValue());
            this.carrierCount = carrierSlider.getValue();
        }
        if(e.getSource().equals(submarineslider)){
            submarineLabel.setText("Count of Submarine length 3= " + submarineslider.getValue());
            this.submarineCount = submarineslider.getValue();
        }
        if(e.getSource().equals(destroyerslider)){
            destroyerLabel.setText("Count of Destroyer length 2= " + destroyerslider.getValue());
            this.destroyerCount = destroyerslider.getValue();
        }
        if(e.getSource().equals(battleshipSlider)){
            battleshipLabel.setText("Count of Battleship length 4= " + battleshipSlider.getValue());
            this.battleshipCount = battleshipSlider.getValue();
        }
    }
}
