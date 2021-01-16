package com.Battleship.UI;

import com.Battleship.Model.Ship;
import com.Battleship.Player.AIPlayer;
import com.Battleship.Player.Player;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
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
    AIPlayer enemyPlayer;
    private int battleshipCount;
    private int destroyerCount;
    private int submarineCount;
    int carrierCount;
    Box vbox;

    private int submarineCounterMax;
    private int destroyerCounterMax;
    private int battleshipCounterMax;

    SinglePlayerScreen(GamePanel mainPanel){
        this.mainPanel = mainPanel;
        initvar();
        singplayer = mainPanel.getSingleplayer();
        enemyPlayer = mainPanel.getEnemyPlayer();
        initlayout();
    }
    public void initvar(){
        fieldslider = new JSlider(5,30);
        carrierSlider = new JSlider(0,0);
        carrierLabel = new JLabel();
        battleshipSlider = new JSlider(0,0);
        battleshipLabel = new JLabel();
        destroyerslider = new JSlider(0,0);
        destroyerLabel = new JLabel();
        submarineslider = new JSlider(0,0);
        submarineLabel = new JLabel();
        back = new JButton("BACK");
        play = new JButton("SET FLEET >");
        size = new JLabel();
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sizefield != 0){
                    ArrayList<Ship> fleet = new ArrayList<>();
                    ArrayList<Ship> enemyfleet = new ArrayList<>();
                    for (int i=0; i<carrierCount; i++){
                        fleet.add(new Ship("carrier"));
                        enemyfleet.add(new Ship("carrier"));
                    }
                    for (int i=0; i<battleshipCount; i++){
                        fleet.add(new Ship("battleship"));
                        enemyfleet.add(new Ship("battleship"));
                    }
                    for (int i=0; i<submarineCount; i++){
                        fleet.add(new Ship("submarine"));
                        enemyfleet.add(new Ship("submarine"));
                    }
                    for (int i=0; i<destroyerCount; i++){
                        fleet.add(new Ship("destroyer"));
                        enemyfleet.add(new Ship("destroyer"));
                    }
                    singplayer.setFleet(fleet);
                    singplayer.setFieldsize(fieldslider.getValue());
                    enemyPlayer.setFleet(enemyfleet);
                    enemyPlayer.setFieldsize(fieldslider.getValue());
                    mainPanel.setGameState("setzen");
                    mainPanel.changeScreen("battlefield");
                }else{
                    JOptionPane.showMessageDialog(mainPanel, "Set your Field size!", "Warning", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setGameState("start");
                mainPanel.changeScreen("main");
            }
        });
    }
    public void initlayout(){
        setBackground(Color.black);
        size.setBackground(Color.black);
        size.setForeground(Color.WHITE);
        Font  buttonfont  = new Font(Font.SANS_SERIF,  Font.BOLD, 19);
        Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));

        play.setBackground(Color.black);
        play.setForeground(Color.WHITE);
        play.setFont(buttonfont);
        play.setFocusPainted(false);
        play.setMargin(new Insets(0, 0, 0, 0));
        play.setBorder(b);
        play.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                play.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                play.setBorder(b);
            }
        });

        back.setBackground(Color.black);
        back.setForeground(Color.WHITE);
        back.setFont(buttonfont);
        back.setFocusPainted(false);
        back.setMargin(new Insets(0, 0, 0, 0));
        back.setBorder(b);
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                back.setBorder(b);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                back.setBorder(b);
            }
        });

        vbox = Box.createVerticalBox();
        {
            // paint the ticks and tarcks
            fieldslider.setPaintTrack(true);
            fieldslider.setPaintTicks(true);
            fieldslider.setPaintLabels(true);
            fieldslider.addChangeListener(this::stateChanged);
            fieldslider.setMinorTickSpacing(5);
            fieldslider.setMajorTickSpacing(25);
            fieldslider.setBackground(Color.black);
            fieldslider.setForeground(Color.white);
            size.setText("size=" + fieldslider.getValue());


            carrierSlider.setPaintTrack(true);
            carrierSlider.setPaintTicks(true);
            carrierSlider.setPaintLabels(true);
            carrierSlider.addChangeListener(this::stateChanged);
            carrierSlider.setBackground(Color.black);
            carrierSlider.setForeground(Color.white);
            carrierSlider.setMinorTickSpacing(1);
            carrierSlider.setMajorTickSpacing(1);
            carrierLabel.setText("Count of Carrier length 5= " + carrierSlider.getValue());

            battleshipSlider.setPaintTrack(true);
            battleshipSlider.setPaintTicks(true);
            battleshipSlider.setPaintLabels(true);
            battleshipSlider.addChangeListener(this::stateChanged);
            battleshipSlider.setBackground(Color.black);
            battleshipSlider.setForeground(Color.white);
            battleshipSlider.setMinorTickSpacing(1);
            battleshipSlider.setMajorTickSpacing(1);
            battleshipLabel.setText("Count of Battleship length 4= " + battleshipSlider.getValue());

            destroyerslider.setPaintTrack(true);
            destroyerslider.setPaintTicks(true);
            destroyerslider.setPaintLabels(true);
            destroyerslider.addChangeListener(this::stateChanged);
            destroyerslider.setBackground(Color.black);
            destroyerslider.setForeground(Color.white);
            destroyerslider.setMinorTickSpacing(1);
            destroyerslider.setMajorTickSpacing(1);
            destroyerLabel.setText("Count of Destroyer length 2= " + destroyerslider.getValue());


            submarineslider.setPaintTrack(true);
            submarineslider.setPaintTicks(true);
            submarineslider.setPaintLabels(true);
            submarineslider.addChangeListener(this::stateChanged);
            submarineslider.setBackground(Color.black);
            submarineslider.setForeground(Color.white);
            submarineslider.setMinorTickSpacing(1);
            submarineslider.setMajorTickSpacing(1);
            submarineLabel.setText("Count of Submarine length 3= " + submarineslider.getValue());

            vbox.add(size);
            vbox.add(fieldslider);
            Box hbox = Box.createHorizontalBox();
            {
                hbox.add(back);
                hbox.add(Box.createHorizontalStrut(100));
                hbox.add(play);
            }
            vbox.add(Box.createVerticalStrut(20));
            vbox.add(hbox);

        }
        vbox.add(Box.createVerticalStrut(100));
        vbox.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        vbox.setAlignmentY(JPanel.CENTER_ALIGNMENT);
        add(vbox);

    }

    @Override
    public void stateChanged(ChangeEvent e) {
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
        if(e.getSource().equals(fieldslider)){
            size.setText("size= " + fieldslider.getValue());
            this.sizefield = fieldslider.getValue();
//            this.carrierCounterMax = fieldslider.getValue()/ 6 == 0 ? 1 : fieldslider.getValue()/ 6;
//            this.submarineCounterMax = fieldslider.getValue()/ 2;
//            this.destroyerCounterMax = fieldslider.getValue()/ 1;
//            this.battleshipCounterMax = fieldslider.getValue()/ 3;

            this.carrierCounterMax = fieldslider.getValue() * fieldslider.getValue()/ 13 == 0 ? 2 : fieldslider.getValue()*fieldslider.getValue()/ 13;
            this.submarineCounterMax = fieldslider.getValue()/ 2;
            this.destroyerCounterMax = fieldslider.getValue()/ 1;
            this.battleshipCounterMax = carrierCounterMax*carrierCounterMax/2;

            this.carrierSlider.setMaximum(carrierCounterMax);
            this.submarineslider.setMaximum(submarineCounterMax);
            this.destroyerslider.setMaximum(destroyerCounterMax);
            this.battleshipSlider.setMaximum(battleshipCounterMax);

            carrierLabel.setBackground(Color.black);
            carrierLabel.setForeground(Color.WHITE);
            submarineLabel.setBackground(Color.black);
            submarineLabel.setForeground(Color.WHITE);
            battleshipLabel.setBackground(Color.black);
            battleshipLabel.setForeground(Color.WHITE);
            destroyerLabel.setBackground(Color.black);
            destroyerLabel.setForeground(Color.WHITE);

            vbox.add(carrierLabel);
            vbox.add(carrierSlider);


            vbox.add(battleshipLabel);
            vbox.add(battleshipSlider);


            vbox.add(submarineLabel);
            vbox.add(submarineslider);

            vbox.add(destroyerLabel);
            vbox.add(destroyerslider);

        }
    }
}
