package com.Battleship.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

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

    private int row = -1,column = -1;
    private int shipPostion;
    private Color shipColor;

    private boolean horizontal = true;
    private ArrayList<Field> shipBoard;

    public Ship(String model){
        this.whosship = 0;
        this.shipBoard = new ArrayList<>();
        switch (model){
            case "carrier":
                this.shipModel = "carrier";
                shiplength = 5;
                health = 5;
                this.shipColor = Color.red;
                break;
            case "battleship":
                this.shipModel = "battleship";
                shiplength = 4;
                health = 4;
                this.shipColor = Color.CYAN;
                break;
            case "submarine":
                this.shipModel = "submarine";
                shiplength = 3;
                health = 3;
                this.shipColor = Color.green;
                break;
            case "destroyer":
                this.shipModel = "destroyer";
                shiplength = 2;
                health = 3;
                this.shipColor = Color.magenta;
                break;
            default:
                break;
        }
    }


    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
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

    public String getShipModel(){
        return this.shipModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(shipModel + "ist geklickt");
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Color getShipColor() {
        return shipColor;
    }

    public int getShiplength() {
        return shiplength;
    }

    public void setShiplength(int shiplength) {
        this.shiplength = shiplength;
    }

    public boolean getHorizontal(){
        return this.horizontal;
    }

    public ArrayList<Field> getShipBoard() {
        return shipBoard;
    }

    public void setShipBoard(ArrayList<Field> shipBoard) {
        this.shipBoard = shipBoard;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "shipModel='" + shipModel + '\'' +
                ", shiplength=" + shiplength +
                ", health=" + health +
                ", sunken=" + sunken +
                ", whosship=" + whosship +
                ", row=" + row +
                ", column=" + column +
                ", shipPostion=" + shipPostion +
                ", shipColor=" + shipColor +
                ", horizontal=" + horizontal +
                ", shipBoard=" + shipBoard +
                '}';
    }
}
