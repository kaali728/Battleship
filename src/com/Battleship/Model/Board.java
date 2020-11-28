package com.Battleship.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Board extends JPanel {
    int size=0;

    public Field button[][];

    JPanel buttonPanel;

    static int rows;
    static int columns;

    ArrayList<Ship> fleet;

    private int carrierCount;
    private int battleshipCount;
    private int submarineCount;
    private int destoryerCount;

    private boolean horizontal = true;

    private String gameState;

    private JLabel carrierText;
    private JLabel battleshipText;
    private JLabel submarineText;
    private JLabel destroyerText;


    public Board(int size, ArrayList<Ship> fleet, String GameState){
        this.size=size;
        this.fleet = fleet;
        this.gameState = GameState;
        this.button = new Field[size][size];
        countShip();
        initlayout();
    }
    public void initlayout(){
        setSize(new Dimension(650,650));
        setLayout(new GridBagLayout());
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(size, size));

        for (rows = 0; rows < size; rows++) {
            for (columns = 0; columns < size; columns++) {
                button[rows][columns] = new Field(rows,columns, gameState);
                button[rows][columns].setBackground(Color.GRAY); //default Gray color is easily interchangable here
                button[rows][columns].setPreferredSize(new Dimension(650/size,650/size));
                button[rows][columns].setActionCommand(button[rows][columns].getRow() + ","+ button[rows][columns].getColumn());
                //wenn wir noch carrier haben (counter != 0) dann soll der carrier gesetzt werden
                button[rows][columns].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(gameState == "setzen"){
                            setShip(e);
                            carrierText = new JLabel("Carrier" + carrierCount);
                            battleshipText = new JLabel("Battleship" + battleshipCount);
                            submarineText = new JLabel("Submarine" + submarineCount);
                            destroyerText = new JLabel("Destoryer" + destoryerCount);
                        }
                        if(gameState == "battle"){
                            boolean success = shoot(e);
                        }
                    }
                });
                buttonPanel.add(button[rows][columns]);
            }
        }


        Box vBox = Box.createVerticalBox();
        vBox.setBackground(Color.white);
        {
            carrierText = new JLabel("Carrier" + carrierCount);
            battleshipText = new JLabel("Battleship" + battleshipCount);
            submarineText = new JLabel("Submarine" + submarineCount);
            destroyerText = new JLabel("Destoryer" + destoryerCount);

            vBox.add(carrierText);
            vBox.add(battleshipText);
            vBox.add(submarineText);
            vBox.add(destroyerText);
        }
        add(buttonPanel);
        if(gameState.equals("setzen")){
            add(vBox);
        }

    }

    public boolean shoot(ActionEvent e){
        return false;
    }

    public Ship setShip(ActionEvent e){
        String[] coordinate = e.getActionCommand().split(",");
        int row =  Integer.parseInt(coordinate[0]);
        int column =  Integer.parseInt(coordinate[1]);
        if(carrierCount != 0){
            for (Ship s: fleet) {
                if(s.getShipModel() == "carrier"){
                    boolean postionCheck = checkBoardPostion(row,column,s.getShiplength(),horizontal);
                    boolean areaCheck = checkShipArea(row,column,s.getShiplength(),horizontal);
                    s.setRowColumn(row,column);
                    for (int i=0; i<s.getShiplength(); i++){
                        if(horizontal){
                            button[row + i][column].setBackground(s.getShipColor());
                        }else{
                            button[row][column + i].setBackground(s.getShipColor());
                        }
                    }
                    carrierCount--;
                    return s;
                }
            }
        }else{
            if(battleshipCount != 0){
                for (Ship s: fleet) {
                    if(s.getShipModel() == "battleship"){
                        s.setRowColumn(row,column);
                        for (int i=0; i<s.getShiplength(); i++){
                            if(horizontal){
                                button[row + i][column].setBackground(s.getShipColor());

                            }else{
                                button[row][column + i].setBackground(s.getShipColor());

                            }
                        }
                        battleshipCount--;

                        return s;
                    }
                }
            }else{
                if(submarineCount != 0){
                    for (Ship s: fleet) {
                        if(s.getShipModel() == "submarine"){
                            s.setRowColumn(row,column);
                            for (int i=0; i<s.getShiplength(); i++){
                                if(horizontal){
                                    button[row + i][column].setBackground(s.getShipColor());

                                }else{
                                    button[row][column + i].setBackground(s.getShipColor());

                                }
                            }
                            submarineCount--;

                            return s;
                        }
                    }
                }else{
                    if(destoryerCount != 0){
                        for (Ship s: fleet) {
                            if(s.getShipModel() == "destroyer"){
                                s.setRowColumn(row,column);
                                for (int i=0; i<s.getShiplength(); i++){
                                    if(horizontal){
                                        button[row + i][column].setBackground(s.getShipColor());

                                    }else{
                                        button[row][column + i].setBackground(s.getShipColor());
                                    }
                                }
                                destoryerCount--;

                                return s;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


    public boolean checkBoardPostion(int rows, int columns, int length, boolean horizontal){
        //schaut ob es richtige postion ist
        return true;
    }

    public boolean checkShipArea(int rows, int columns, int length, boolean horizontal){
        //schaut ob es der umgebung für shiff past
        return true;
    }

    public void countShip(){
        for (Ship s: fleet) {
            if(s.getShipModel().equals("carrier")){
                carrierCount++;
            }
            if(s.getShipModel().equals("battleship")){
                battleshipCount++;
            }
            if(s.getShipModel().equals("submarine")){
                submarineCount++;
            }
            if(s.getShipModel().equals("destroyer")){
                destoryerCount++;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }
}
