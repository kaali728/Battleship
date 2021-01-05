package com.Battleship.Model;

import java.awt.*;
import java.util.ArrayList;

public class SaveShip {

    private String shipModel;
    private int shiplength;
    private int health;
    private int row = -1,column = -1;
    private Color shipColor;
    private boolean horizontal = true;
    private ArrayList<SaveField> shipBoard = new ArrayList<>();
    public SaveShip(Ship s){
        shipModel = s.getShipModel();
        shipColor = s.getShipColor();
        shiplength = s.getShiplength();
        this.row = s.getRow();
        this.column = s.getColumn();
        for (Field f: s.getShipBoard()) {
            shipBoard.add(new SaveField(f.getRow(), f.getColumn()));
        }
    }


    public String getShipModel() {
        return shipModel;
    }

    public int getShiplength() {
        return shiplength;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public Color getShipColor() {
        return shipColor;
    }
    public  ArrayList<SaveField> getShipBoard(){
        return shipBoard;
    }
}
