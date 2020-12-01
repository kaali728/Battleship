package com.Battleship.Player;

import com.Battleship.Model.Board;
import com.Battleship.Model.Ship;

import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AIPlayer {
    private int fieldsize;
    private ArrayList<Ship> fleet = new ArrayList<>();
    private HashMap<Integer,Integer> usedPosition= new HashMap<>();
    private Board enemyBoard;

    public AIPlayer(){
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

    public Board getEnemyBoard() {
        return enemyBoard;
    }

    public void setEnemyBoard(Board enemyBoard) {
        this.enemyBoard = enemyBoard;
    }

    public void setEnemyShip(){
        Random random= new Random();
        for (Ship s: fleet) {
            while (true){
                int row = random.nextInt(fieldsize - 1);
                int column = random.nextInt(fieldsize - 1);
                Ship shipret = enemyBoard.setShip(row, column);
                if(shipret != null){
                    break;
                }
            }

        }
    }

    public void Enemyshoot(Board player){
        Random random= new Random();
        int row = random.nextInt(fieldsize - 1);
        int column = random.nextInt(fieldsize - 1);
        player.shoot(row,column);
    }

    //schauen ob in diese coordinaten was drin ist oder nicht
    public boolean checkCoordPostion(int row, int column, boolean hori, int length){
        // usedPosition = {(row,column), (row,column), (row,column), (row,column)}
        HashMap<Integer,Integer> shipPos = new HashMap<>();
        boolean hasLeftColumn = false;
        boolean hasRightColumn = false;
        boolean hasUpRow = false;
        boolean hasDownRow = false;

        int leftColumn = 0;
        int rightColumn = 0;
        int upRow = 0;
        int downRow = 0;

        if(column - 1 >= 0){
            hasLeftColumn = true;
            leftColumn = column -1;
        }
        if(column + 1 <= fieldsize - 1){
            hasRightColumn = true;
            rightColumn = column + 1;
        }
        if(row - 1 >= 0){
            hasUpRow = true;
            upRow = row -1;
        }
        if(row + 1 <= fieldsize -1){
            hasDownRow = true;
            downRow = row + 1;
        }

        if(hori){
            for (int i = 0; i < length; i++) {
                if(i == 0){
                    if(hasLeftColumn){
                        shipPos.put(row,leftColumn);
                    }
                    if(hasLeftColumn && hasUpRow){
                        shipPos.put(upRow, leftColumn);
                    }
                    if(hasLeftColumn && hasDownRow){
                        shipPos.put(downRow, leftColumn);
                    }
                }
               // System.out.println(row + " col " + column);
                shipPos.put(row, column + i);
                if(hasUpRow){
                    shipPos.put(upRow, column + i);
                }
                if(hasDownRow){
                    shipPos.put(downRow, column + i);
                }
                if(i == length - 1){
                    if((column + i)+1<=fieldsize - 1){
                        shipPos.put(row,(column + i)+1);
                    }
                    if((column + i)+1<=fieldsize - 1){
                        shipPos.put(upRow,column+i +1);
                    }
                    if((column + i)+1<=fieldsize - 1){
                        shipPos.put(downRow, (column + i)+1);
                    }
                }
            }
        }else{
            for (int i = 0; i <length ; i++) {
                if(i == 0){
                    if(hasUpRow){
                        shipPos.put(upRow,column);
                    }
                    if(hasLeftColumn && hasUpRow){
                        shipPos.put(upRow, leftColumn);
                    }
                    if(hasLeftColumn && hasDownRow){
                        shipPos.put(downRow, leftColumn);
                    }
                    if(hasRightColumn && hasUpRow){
                        shipPos.put(upRow,rightColumn);
                    }
                    if(hasRightColumn && hasDownRow){
                        shipPos.put(downRow,rightColumn);
                    }
                }
                //System.out.println(row + " col " + column);
                shipPos.put(row + i, column);
                if(hasLeftColumn){
                   shipPos.put(row + i, leftColumn);
                }
                if(hasRightColumn){
                    shipPos.put(row + i, rightColumn);
                }
                if(i == length - 1){
                    if((row + i)+1<=fieldsize - 1){
                        shipPos.put(row+i+1,column);
                    }
                    if((row + i)+1<=fieldsize - 1){
                        shipPos.put(row+i+1,leftColumn);
                    }
                    if((row + i)+1<=fieldsize - 1){
                        shipPos.put(row+i+1,rightColumn);
                    }
                }
            }
        }

        for (int i = 0; i < usedPosition.size(); i++) {
            if(usedPosition.equals(shipPos)){
                return false;
            }
        }
        usedPosition.putAll(shipPos);
        //System.out.println(usedPosition);
        return true;
    }

    public boolean checkBoardPostion(int row, int column, int length, boolean horizontal){
        int diffRow = fieldsize - (row+1);
        int diffCol = fieldsize - (column+1);

        if(!horizontal){
            if(length - 1 > diffRow){
                return false;
            }
        }else{
            if(length - 1 > diffCol){
                return false;
            }
        }
        //schaut ob es richtige postion ist
        return true;
    }


}
