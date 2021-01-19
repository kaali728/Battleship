package com.Battleship.AIvsAI;

import com.Battleship.Model.Board;
import com.Battleship.Model.Ship;
import com.Battleship.Player.AINetworkPlayer;
import com.Battleship.Player.AIPlayer;
import com.Battleship.Player.Player;
import com.Battleship.UI.GamePanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The type Ai server setup.
 */
public class AIServerSetup extends JPanel {
    /**
     * The Main panel.
     */
    GamePanel mainPanel;
    /**
     * The Port.
     */
    JTextField port;
    /**
     * The Port label.
     */
    JLabel portLabel;
    /**
     * The Port number.
     */
    Integer portNumber;
    /**
     * The Create server.
     */
    JButton createServer;
    /**
     * The Size label.
     */
    JLabel sizeLabel;
    /**
     * The Size.
     */
    JSlider size;
    /**
     * The Fieldsize.
     */
    int fieldsize;

    /**
     * The Back.
     */
    JButton back;


    /**
     * The Carrier slider.
     */
    JSlider carrierSlider;
    /**
     * The Carrier label.
     */
    JLabel carrierLabel;
    /**
     * The Battleship slider.
     */
    JSlider battleshipSlider;
    /**
     * The Battleship label.
     */
    JLabel battleshipLabel;
    /**
     * The Destroyerslider.
     */
    JSlider destroyerslider;
    /**
     * The Destroyer label.
     */
    JLabel destroyerLabel;
    /**
     * The Submarineslider.
     */
    JSlider submarineslider;
    /**
     * The Submarine label.
     */
    JLabel submarineLabel;

    /**
     * The Vbox.
     */
    Box vbox;
    /**
     * The Server player.
     */
    Player serverPlayer;

    /**
     * The Ai player.
     */
    AINetworkPlayer aiPlayer;

    /**
     * Rest of Board with ships in it.
     */
    private int flaeche;


    private int battleshipCount;
    private int destroyerCount;
    private int submarineCount;
    private int carrierCount;

    private int carrierCounterMax;
    private int submarineCounterMax;
    private int destroyerCounterMax;
    private int battleshipCounterMax;


    /**
     * Instantiates a new Ai server setup.
     *
     * @param mainPanel the main panel
     */
    public AIServerSetup(GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        serverPlayer = mainPanel.getSingleplayer();
        aiPlayer = mainPanel.getAiOnline();
        initVar();
        initLayout();
    }

    /**
     * Init var.
     */
    public void initVar() {
        portLabel = new JLabel("Port");
        port = new JTextField(10);
        back = new JButton("Back");

        carrierSlider = new JSlider(0,0);
        carrierLabel = new JLabel();
        battleshipSlider = new JSlider(0,0);
        battleshipLabel = new JLabel();
        destroyerslider = new JSlider(0,0);
        destroyerLabel = new JLabel();
        submarineslider = new JSlider(0,0);
        submarineLabel = new JLabel();
        sizeLabel = new JLabel("Board size");
        size = new JSlider(5, 30);
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
                int tempArea = flaeche;
                tempArea -= (carrierCount*21 + battleshipCount*18 + submarineCount*15 + destroyerCount*12);
                if(tempArea < 0 || port.getText().equals("")){
                    JOptionPane.showMessageDialog(mainPanel, "You have too many ship, not enough space!", "Warning", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    if (fieldsize != 0) {
                        ArrayList<Ship> fleet = new ArrayList<>();
                        for (int i = 0; i < carrierCount; i++) {
                            fleet.add(new Ship("carrier"));
                        }
                        for (int i = 0; i < battleshipCount; i++) {
                            fleet.add(new Ship("battleship"));
                        }
                        for (int i = 0; i < submarineCount; i++) {
                            fleet.add(new Ship("submarine"));
                        }
                        for (int i = 0; i < destroyerCount; i++) {
                            fleet.add(new Ship("destroyer"));
                        }
                        serverPlayer.setFleet(fleet);
                        serverPlayer.setFieldsize(fieldsize);
                        mainPanel.setGameState("setzen");
                        //mainPanel.changeScreen("battlefield");
                        portNumber = Integer.parseInt(port.getText());
                        mainPanel.aichangeScreen("aiserverscreen", portNumber, fieldsize, carrierCount, battleshipCount, submarineCount, destroyerCount, aiPlayer);
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Set your Field size!", "Warning", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            }
        });
    }

    /**
     * Init layout.
     */
    public void initLayout() {
        setBackground(Color.black);
        Font  buttonfont  = new Font(Font.SANS_SERIF,  Font.BOLD, 19);
        Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));


        vbox = Box.createVerticalBox();
        {
            createServer.setBackground(Color.black);
            createServer.setForeground(Color.WHITE);
            createServer.setFont(buttonfont);
            createServer.setFocusPainted(false);
            createServer.setMargin(new Insets(0, 0, 0, 0));
            createServer.setBorder(b);
            createServer.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(43,209,252));
                    createServer.setBorder(b);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    Border b = BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(0,0,0));
                    createServer.setBorder(b);
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


            size.setPaintTrack(true);
            size.setPaintTicks(true);
            size.setPaintLabels(true);
            size.addChangeListener(this::stateChanged);
            size.setMinorTickSpacing(5);
            size.setMajorTickSpacing(25);
            size.setBackground(Color.black);
            size.setForeground(Color.white);
            sizeLabel.setText("size =" + size.getValue());


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
            submarineslider.setMinorTickSpacing(1);
            submarineslider.setMajorTickSpacing(1);
            submarineslider.setBackground(Color.black);
            submarineslider.setForeground(Color.white);
            submarineLabel.setText("Count of Submarine length 3= " + submarineslider.getValue());

            portLabel.setBackground(Color.black);
            portLabel.setForeground(Color.WHITE);
            sizeLabel.setBackground(Color.black);
            sizeLabel.setForeground(Color.WHITE);
            carrierLabel.setBackground(Color.black);
            carrierLabel.setForeground(Color.WHITE);
            submarineLabel.setBackground(Color.black);
            submarineLabel.setForeground(Color.WHITE);
            battleshipLabel.setBackground(Color.black);
            battleshipLabel.setForeground(Color.WHITE);
            destroyerLabel.setBackground(Color.black);
            destroyerLabel.setForeground(Color.WHITE);

            vbox.add(portLabel);
            vbox.add(port);
            vbox.add(Box.createVerticalStrut(10));
            vbox.add(createServer);
            vbox.add(Box.createVerticalStrut(10));
            vbox.add(back);
            vbox.add(Box.createVerticalStrut(100));

            createServer.setAlignmentX(Component.LEFT_ALIGNMENT);
            back.setAlignmentX(Component.LEFT_ALIGNMENT);
            port.setAlignmentX(Component.LEFT_ALIGNMENT);

            vbox.add(size);
            vbox.add(sizeLabel);
        }
        vbox.add(Box.createVerticalStrut(100));
        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(vbox);

    }

    /**
     * State changed.
     *
     * @param e the e
     */
    public void stateChanged(ChangeEvent e) {
        if (e.getSource().equals(size)) {
            sizeLabel.setText("size= " + size.getValue());
            this.fieldsize = size.getValue();
            this.flaeche = size.getValue() * size.getValue();

            this.carrierCounterMax = flaeche / 21;
            this.carrierCount = carrierSlider.getValue();
            this.battleshipCounterMax = (flaeche) / 18;
            this.battleshipCount = battleshipSlider.getValue();
            this.submarineCounterMax = (flaeche) /15;
            this.submarineCount = submarineslider.getValue();
            this.destroyerCounterMax = flaeche / 12;

//            this.carrierCounterMax = size.getValue()/ 6 == 0 ? 1 : size.getValue()/ 6;
//            this.submarineCounterMax = size.getValue()/ 2;
//            this.destroyerCounterMax = size.getValue()/ 1;
//            this.battleshipCounterMax = size.getValue()/ 3;

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
