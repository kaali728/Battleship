package com.Battleship.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ship implements ActionListener {
    //carrier 5 ,battleship 4,submarine 3,destroyer  2
    private String shipModel;
    private int shiplength;
    //leben von ship des player also wenn getroffen length -- werden
    private int health;
    private boolean sunken = false;
    //whosship = 0 ist unsere wenn 1 dann enemy
    private int whosship;
    //how many ship do i have? that tell us counter
    private int counter;

    private int row,column;
    private int shipPostion;
    private Color shipColor;

    private JButton[][] shipBoard;

    public Ship(String model, int counter){
        this.whosship = 0;
        switch (model){
            case "carrier":
                this.shipModel = "carrier";
                shiplength = 5;
                health = 5;
                this.shipColor = Color.red;
                this.counter = counter;
                break;
            case "battleship":
                this.shipModel = "battleship";
                shiplength = 4;
                health = 4;
                this.shipColor = Color.CYAN;
                this.counter = counter;
                break;
            case "submarine":
                this.shipModel = "submarine";
                shiplength = 3;
                health = 3;
                this.shipColor = Color.green;
                this.counter = counter;
                break;
            case "destroyer":
                this.shipModel = "destroyer";
                shiplength = 2;
                health = 3;
                this.shipColor = Color.magenta;
                this.counter = counter;
                break;
            default:
                break;
        }
    }


    public void setRowColumn(int row, int column){
        this.row = row;
        this.column = column;
    }

    public int getShipPostion() {
        return shipPostion;
    }

    public void setShipPostion(int shipPostion) {
        this.shipPostion = shipPostion;
    }

    public JButton[][] getShipBoard() {
        return shipBoard;
    }

    public void setShipBoard(JButton[][] shipBoard) {
        this.shipBoard = shipBoard;
    }

    public void shot(){
        health--;
    }
    public boolean sunken(){
        if(health == 0){
            sunken = true;
            return true;
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(shipModel + "ist geklickt");
    }
}
