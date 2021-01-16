package com.Battleship.Player;

import com.Battleship.Model.Ship;

import java.util.ArrayList;

/**
 * The type Player.
 */
public class Player {
    private int fieldsize;
    private ArrayList<Ship> fleet = new ArrayList<>();

    /**
     * Instantiates a new Player.
     */
    public Player(){
        fieldsize = 0;
    }

    /**
     * Sets fieldsize.
     *
     * @param fieldsize the fieldsize
     */
    public void setFieldsize(int fieldsize) {
        this.fieldsize = fieldsize;
    }

    /**
     * Gets fieldsize.
     *
     * @return the fieldsize
     */
    public int getFieldsize() {
        return fieldsize;
    }

    /**
     * Gets fleet.
     *
     * @return the fleet
     */
    public ArrayList<Ship> getFleet() {
        return fleet;
    }

    /**
     * Sets fleet.
     *
     * @param fleet the fleet
     */
    public void setFleet(ArrayList<Ship> fleet) {
        this.fleet = fleet;
    }
}
