package com.Battleship.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The type Ship.
 */
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

    /**
     * Instantiates a new Ship.
     *
     * @param model the model
     */
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
                health = 2;
                this.shipColor = Color.magenta;
                break;
            default:
                break;
        }
    }


    /**
     * Sets horizontal.
     *
     * @param horizontal the horizontal
     */
    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    /**
     * Set row column.
     *
     * @param row    the row
     * @param column the column
     */
    public void setRowColumn(int row, int column){
        this.row = row;
        this.column = column;
    }

    /**
     * Gets ship postion.
     *
     * @return the ship postion
     */
    public int getShipPostion() {
        return shipPostion;
    }

    /**
     * Sets ship postion.
     *
     * @param shipPostion the ship postion
     */
    public void setShipPostion(int shipPostion) {
        this.shipPostion = shipPostion;
    }


    /**
     * Shot.
     */
    public void shot(){
        health--;
    }

    /**
     * Sunken boolean.
     *
     * @return the boolean
     */
    public boolean sunken(){
        if(health == 0){
            sunken = true;
            return true;
        }
        return false;
    }

    /**
     * Get ship model string.
     *
     * @return the string
     */
    public String getShipModel(){
        return this.shipModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(shipModel + "ist geklickt");
    }

    /**
     * Gets row.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets row.
     *
     * @param row the row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Gets column.
     *
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Sets column.
     *
     * @param column the column
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Gets ship color.
     *
     * @return the ship color
     */
    public Color getShipColor() {
        return shipColor;
    }

    /**
     * Gets shiplength.
     *
     * @return the shiplength
     */
    public int getShiplength() {
        return shiplength;
    }

    /**
     * Sets shiplength.
     *
     * @param shiplength the shiplength
     */
    public void setShiplength(int shiplength) {
        this.shiplength = shiplength;
    }

    /**
     * Get horizontal boolean.
     *
     * @return the boolean
     */
    public boolean getHorizontal(){
        return this.horizontal;
    }

    /**
     * Gets ship board.
     *
     * @return the ship board
     */
    public ArrayList<Field> getShipBoard() {
        return shipBoard;
    }

    /**
     * Sets ship board.
     *
     * @param shipBoard the ship board
     */
    public void setShipBoard(ArrayList<Field> shipBoard) {
        this.shipBoard = shipBoard;
    }

    /**
     * Gets health.
     *
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets health.
     *
     * @param health the health
     */
    public void setHealth(int health) {
        this.health = health;
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
