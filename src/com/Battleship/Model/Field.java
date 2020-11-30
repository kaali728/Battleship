package com.Battleship.Model;

import javax.swing.*;

public class Field extends JButton {
    int row;
    int column;
    String gameState;
    private boolean mark = false;

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

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }
}
