package com.Battleship.AIvsAI;

import com.Battleship.Model.Board;
import com.Battleship.Model.Ship;
import com.Battleship.Player.AIPlayer;
import com.Battleship.Player.Player;
import com.Battleship.UI.GamePanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class AIServerSetup extends JPanel {
    GamePanel mainPanel;
    JTextField port;
    JLabel portLabel;
    Integer portNumber;
    JButton createServer;
    JLabel sizeLabel;
    JSlider size;
    int fieldsize;

    JButton back;


    JSlider carrierSlider;
    JLabel carrierLabel;
    JSlider battleshipSlider;
    JLabel battleshipLabel;
    JSlider destroyerslider;
    JLabel destroyerLabel;
    JSlider submarineslider;
    JLabel submarineLabel;

    Box vbox;
    Player serverPlayer;

    AIPlayer aiPlayer;


    private int battleshipCount;
    private int destroyerCount;
    private int submarineCount;
    private int carrierCount;

    private int carrierCounterMax;
    private int submarineCounterMax;
    private int destroyerCounterMax;
    private int battleshipCounterMax;


    public AIServerSetup(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        serverPlayer = mainPanel.getSingleplayer();
        aiPlayer = mainPanel.getEnemyPlayer();
        initVar();
        initLayout();
    }

    public void initVar() {
        portLabel = new JLabel("Port");
        port = new JTextField(10);
        back = new JButton("Back");

        carrierSlider = new JSlider(1,1);
        carrierLabel = new JLabel();
        battleshipSlider = new JSlider(1,1);
        battleshipLabel = new JLabel();
        destroyerslider = new JSlider(1,1);
        destroyerLabel = new JLabel();
        submarineslider = new JSlider(1,1);
        submarineLabel = new JLabel();

        sizeLabel = new JLabel("Board size");
        size = new JSlider(5, 30);
        size.setPaintTrack(true);
        size.setPaintTicks(true);
        size.setPaintLabels(true);
        size.addChangeListener(this::stateChanged);
        size.setMinorTickSpacing(5);
        size.setMajorTickSpacing(25);
        sizeLabel.setText("size =" + size.getValue());
        //size.setValue(16);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setGameState("start");
                mainPanel.changeScreen("main");
            }
        });
        createServer = new JButton("Create server");
        createServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(fieldsize != 0){
                    ArrayList<Ship> fleet = new ArrayList<>();
                    for (int i=0; i<carrierCount; i++){
                        fleet.add(new Ship("carrier"));
                    }
                    for (int i=0; i<battleshipCount; i++){
                        fleet.add(new Ship("battleship"));
                    }
                    for (int i=0; i<submarineCount; i++){
                        fleet.add(new Ship("submarine"));
                    }
                    for (int i=0; i<destroyerCount; i++){
                        fleet.add(new Ship("destroyer"));
                    }
                    serverPlayer.setFleet(fleet);
                    serverPlayer.setFieldsize(fieldsize);
                    mainPanel.setGameState("setzen");
                    //mainPanel.changeScreen("battlefield");
                    portNumber = Integer.parseInt(port.getText());
                    mainPanel.aichangeScreen("aiserverscreen", portNumber, fieldsize, carrierCount, battleshipCount, submarineCount, destroyerCount, aiPlayer);
                }else{
                    JOptionPane.showMessageDialog(mainPanel, "Set your Field size!", "Warning", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
    }

    public void initLayout() {
        setBackground(Color.white);

        vbox = Box.createVerticalBox();
        {

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
            vbox.add(portLabel);
            vbox.add(port);
            vbox.add(createServer);
            vbox.add(back);
            vbox.add(Box.createVerticalStrut(100));

            vbox.add(size);
            vbox.add(sizeLabel);
        }
        vbox.add(Box.createVerticalStrut(100));
        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(vbox);

    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource().equals(size)) {
            sizeLabel.setText("size= " + size.getValue());
            this.fieldsize = size.getValue();
            this.carrierCounterMax = size.getValue()/ 6 == 0 ? 1 : size.getValue()/ 6;
            this.submarineCounterMax = size.getValue()/ 2;
            this.destroyerCounterMax = size.getValue()/ 1;
            this.battleshipCounterMax = size.getValue()/ 3;

            this.carrierSlider.setMaximum(carrierCounterMax);
            this.submarineslider.setMaximum(submarineCounterMax);
            this.destroyerslider.setMaximum(destroyerCounterMax);
            this.battleshipSlider.setMaximum(battleshipCounterMax);
            vbox.add(carrierSlider);
            vbox.add(carrierLabel);

            vbox.add(battleshipSlider);
            vbox.add(battleshipLabel);

            vbox.add(submarineslider);
            vbox.add(submarineLabel);

            vbox.add(destroyerslider);
            vbox.add(destroyerLabel);


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
