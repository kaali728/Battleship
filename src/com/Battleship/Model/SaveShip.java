package com.Battleship.Model;

import java.awt.*;
import java.util.ArrayList;

/**
 * The type Save ship.
 * Convert Ship to SaveShip.
 * It will used in GameObj for saving in json.
 */
public class SaveShip {

    private String shipModel;
    private int shiplength;
    private int health;
    private int row = -1,column = -1;
    private Color shipColor;
    private boolean horizontal = true;
    private ArrayList<SaveField> shipBoard = new ArrayList<>();

    /**
     * Instantiates a new Save ship.
     *
     * @param s the s
     */
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


    /**
     * Gets ship model.
     *
     * @return the ship model
     */
    public String getShipModel() {
        return shipModel;
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
     * Gets row.
     *
     * @return the row
     */
    public int getRow() {
        return row;
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
     * Is horizontal boolean.
     *
     * @return the boolean
     */
    public boolean isHorizontal() {
        return horizontal;
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
     * Get ship board array list.
     *
     * @return the array list
     */
    public  ArrayList<SaveField> getShipBoard(){
        return shipBoard;
    }
}
