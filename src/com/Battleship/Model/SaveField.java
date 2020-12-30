package com.Battleship.Model;

public class SaveField {
    int row;
    int column;
    String gameState;
    private boolean mark = false;
    private boolean isShot = false;
    public SaveField(int row, int column){
        this.row = row;
        this.column = column;
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

    public boolean isShot() {
        return isShot;
    }

    public void setShot(boolean shot) {
        isShot = shot;
    }
}
