package com.Battleship.Model;

import javax.swing.*;

/**
 * The type Field.
 */
public class Field extends JButton {
    /**
     * The Row.
     */
    int row;
    /**
     * The Column.
     */
    int column;
    /**
     * The Game state.
     */
    String gameState;
    private boolean mark = false;
    private boolean isShot = false;

    /**
     * Instantiates a new Field.
     *
     * @param row       the row
     * @param column    the column
     * @param gameState the game state
     */
    public Field(int row, int column, String gameState){
        this.row = row;
        this.column = column;
        this.gameState = gameState;
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
     * Gets row.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Is mark boolean.
     *
     * @return the boolean
     */
    public boolean isMark() {
        return mark;
    }

    /**
     * Sets mark.
     *
     * @param mark the mark
     */
    public void setMark(boolean mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Field{" +
                "row=" + row +
                ", column=" + column +
                ", gameState='" + gameState + '\'' +
                ", mark=" + mark +
                ", isShot=" + isShot +
                '}';
    }

    /**
     * Is shot boolean.
     *
     * @return the boolean
     */
    public boolean isShot() {
        return isShot;
    }

    /**
     * Sets shot.
     *
     * @param shot the shot
     */
    public void setShot(boolean shot) {
        isShot = shot;
    }
}
