package com.Battleship.SpielstandLaden;

import com.Battleship.Model.Board;
import com.Battleship.Model.Field;
import com.Battleship.Model.SaveField;
import com.Battleship.Model.Ship;

import java.util.ArrayList;

public class GameObj {

    public SaveField playerButton[][];
    public SaveField enemyButton[][];

    public ArrayList<Ship> playerFleet;
    public ArrayList<Ship> enemyFleet;

    public int size;

    public GameObj(Board playerBoard, Board enemyBoard){
        this.size = playerBoard.getButton().length;
        playerButton = new SaveField[size][size];
        enemyButton = new SaveField[size][size];

        setSavedField(playerBoard.getButton(), 1);
        setSavedField(enemyBoard.getButton(), 2);
        //this.playerFleet = playerBoard.getFleet();
        //this.enemyFleet = enemyBoard.getFleet();
    }


    public void setSavedField(Field[][] button, int who){
            for (int i = 0; i <button.length ; i++) {
                for (int j = 0; j <button[i].length ; j++) {
                    if(who == 1){
                        playerButton[i][j] = new SaveField(button[i][j].getRow(), button[i][j].getColumn());
                        playerButton[i][j].setMark(button[i][j].isMark());
                        playerButton[i][j].setShot(button[i][j].isShot());
                    }else if(who == 2){
                        enemyButton[i][j] = new SaveField(button[i][j].getRow(), button[i][j].getColumn());
                        enemyButton[i][j].setMark(button[i][j].isMark());
                        enemyButton[i][j].setShot(button[i][j].isShot());
                    }
                }
            }
    }
}
