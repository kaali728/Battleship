package com.Battleship.Model;

/**
 * The type Save field. Convert Field to SaveField.
 * It will used in GameObj for saving in json.
 */
public class SaveField {
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
     * Instantiates a new Save field.
     *
     * @param row    the row
     * @param column the column
     */
    public SaveField(int row, int column){
        this.row = row;
        this.column = column;
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
