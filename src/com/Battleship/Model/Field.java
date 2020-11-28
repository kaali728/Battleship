package com.Battleship.Model;

import javax.swing.*;

public class Field extends JButton {
    int row;
    int column;
    String gameState;

    public Field(int row, int column, String gameState){
        this.row = row;
        this.column = column;
        this.gameState = gameState;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
