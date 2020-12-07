package com.Battleship.Player;

import com.Battleship.Model.Ship;

import java.util.ArrayList;

public class NetworkPlayer {
    private int fieldsize;
    private ArrayList<Ship> fleet = new ArrayList<>();

    public NetworkPlayer(){
        fieldsize = 0;
    }

    public void setFieldsize(int fieldsize) {
        this.fieldsize = fieldsize;
    }

    public int getFieldsize() {
        return fieldsize;
    }

    public ArrayList<Ship> getFleet() {
        return fleet;
    }

    public void setFleet(ArrayList<Ship> fleet) {
        this.fleet = fleet;
    }
}
