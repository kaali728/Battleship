package com.Battleship.Player;

import com.Battleship.Model.Board;
import com.Battleship.Model.Field;
import com.Battleship.Model.Ship;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class AIPlayer {
    private int fieldsize;
    private ArrayList<Ship> fleet = new ArrayList<>();
    private  Map<Integer, int[]> nextHit = new HashMap<>();
    private Map<Integer, int[]> usedCord = new HashMap<>();


    private Board enemyBoard;
    private Board playerBoard;

    public AIPlayer() {
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

    public void setEnemyShip() {
        Random random = new Random();
        for (Ship s : fleet) {
            while (true) {
                int row = random.nextInt(fieldsize - 1);
                int column = random.nextInt(fieldsize - 1);
                Ship shipret = enemyBoard.setShip(row, column);
                if (shipret != null) {
                    break;
                }
            }
        }
    }

    public void Enemyshoot(Board player) {
        this.playerBoard = player;
        if(nextHit.size() == 0){
            Random random = new Random();
            while (true) {
                int row = random.nextInt(fieldsize);
                int column = random.nextInt(fieldsize);
                if ((row + column) % 2 == 0) {
                    if (!isUsedCord(row, column)) {
                        addTousedCord(row, column);
                        boolean isHit = player.shoot(row, column);
                        if (isHit) {
                            hitShipBehind(row, column);
                        }
                        break;
                    }
                }
            }
        }else{
            Integer key = null;
            boolean isHit = false;
            for (Map.Entry<Integer, int[]> entry : nextHit.entrySet()) {
                key = entry.getKey();
                int[] value = entry.getValue();
                int nextRow = value[0];
                int nextColumn = value[1];
                if(!isUsedCord(nextRow,nextColumn)){
                    isHit = player.shoot(nextRow, nextColumn);
                    addTousedCord(nextRow,nextColumn);
                    if(isHit){
                        hitShipBehind(nextRow,nextColumn);
                    }
                    break;
                }
            }
            nextHit.remove(key);
        }
    }


    public void addTousedCord(int row, int column) {
        int[] entry = {row, column};

        usedCord.put(hashCode(row, column), entry);
    }


    public boolean isUsedCord(int row, int column) {
        for (Map.Entry<Integer, int[]> entry : usedCord.entrySet()) {
            Integer key = entry.getKey();
            int[] value = entry.getValue();
            if (key == hashCode(row, column) && value[0] == row && value[1] == column) {
                return true;
            }
        }
        return false;
    }

    public int hashCode(int x, int y) {
        return x * 31 + y;
    }

    public void hitShipBehind(int row, int column) {
        boolean hitH= false;
        boolean hitV= true;

        if (row - 1 >= 0 && hitV) {
            int[] cord = {row -1, column};
            if(usedCord.get(hashCode(row - 1,column)) == null) {
                nextHit.put(hashCode(row - 1, column), cord);
            }
            hitH = hori(row, column, true);
            hitV = vert(row, column, false);
        }

        if (row + 1 <= fieldsize-1 && hitV) {
            int[] cord = {row+1, column};
            if(usedCord.get(hashCode(row + 1, column)) == null) {
                nextHit.put(hashCode(row + 1, column), cord);
            }
            hitH = hori(row, column, true);
        }

        if (column + 1 <= fieldsize-1 && hitH) {
            int[] cord = {row, column +1};
            if(usedCord.get(hashCode(row, column + 1)) == null) {
                nextHit.put(hashCode(row, column + 1), cord);
            }
        }
        if (column - 1 >= 0 && hitH) {
            int[] cord = {row, column-1};
            if(usedCord.get(hashCode(row,column-1)) == null) {
                nextHit.put(hashCode(row, column - 1), cord);
            }
        }

    }

    public boolean hori (int row, int column, boolean hori){
        if(hori){
            nextHit.remove(hashCode(row + 1, column));
            nextHit.remove(hashCode(row - 1,column));
            return true;
        }
        return false;
    }
    public boolean vert (int row, int column, boolean verti){
        if(verti){
            nextHit.remove(hashCode(row, column + 1));
            nextHit.remove(hashCode(row,column - 1));
            return true;
        }
        return false;
    }

    //schauen ob in diese coordinaten was drin ist oder nicht
    public boolean checkCoordPostion(int row, int column, boolean hori, int length) {
        // usedPosition = {(row,column), (row,column), (row,column), (row,column)}
        HashMap<Integer, Integer> shipPos = new HashMap<>();
        boolean hasLeftColumn = false;
        boolean hasRightColumn = false;
        boolean hasUpRow = false;
        boolean hasDownRow = false;

        int leftColumn = 0;
        int rightColumn = 0;
        int upRow = 0;
        int downRow = 0;

        if (column - 1 >= 0) {
            hasLeftColumn = true;
            leftColumn = column - 1;
        }
        if (column + 1 <= fieldsize - 1) {
            hasRightColumn = true;
            rightColumn = column + 1;
        }
        if (row - 1 >= 0) {
            hasUpRow = true;
            upRow = row - 1;
        }
        if (row + 1 <= fieldsize - 1) {
            hasDownRow = true;
            downRow = row + 1;
        }

        if (hori) {
            for (int i = 0; i < length; i++) {
                if (i == 0) {
                    if (hasLeftColumn) {
                        shipPos.put(row, leftColumn);
                    }
                    if (hasLeftColumn && hasUpRow) {
                        shipPos.put(upRow, leftColumn);
                    }
                    if (hasLeftColumn && hasDownRow) {
                        shipPos.put(downRow, leftColumn);
                    }
                }
                // System.out.println(row + " col " + column);
                shipPos.put(row, column + i);
                if (hasUpRow) {
                    shipPos.put(upRow, column + i);
                }
                if (hasDownRow) {
                    shipPos.put(downRow, column + i);
                }
                if (i == length - 1) {
                    if ((column + i) + 1 <= fieldsize - 1) {
                        shipPos.put(row, (column + i) + 1);
                    }
                    if ((column + i) + 1 <= fieldsize - 1) {
                        shipPos.put(upRow, column + i + 1);
                    }
                    if ((column + i) + 1 <= fieldsize - 1) {
                        shipPos.put(downRow, (column + i) + 1);
                    }
                }
            }
        } else {
            for (int i = 0; i < length; i++) {
                if (i == 0) {
                    if (hasUpRow) {
                        shipPos.put(upRow, column);
                    }
                    if (hasLeftColumn && hasUpRow) {
                        shipPos.put(upRow, leftColumn);
                    }
                    if (hasLeftColumn && hasDownRow) {
                        shipPos.put(downRow, leftColumn);
                    }
                    if (hasRightColumn && hasUpRow) {
                        shipPos.put(upRow, rightColumn);
                    }
                    if (hasRightColumn && hasDownRow) {
                        shipPos.put(downRow, rightColumn);
                    }
                }
                //System.out.println(row + " col " + column);
                shipPos.put(row + i, column);
                if (hasLeftColumn) {
                    shipPos.put(row + i, leftColumn);
                }
                if (hasRightColumn) {
                    shipPos.put(row + i, rightColumn);
                }
                if (i == length - 1) {
                    if ((row + i) + 1 <= fieldsize - 1) {
                        shipPos.put(row + i + 1, column);
                    }
                    if ((row + i) + 1 <= fieldsize - 1) {
                        shipPos.put(row + i + 1, leftColumn);
                    }
                    if ((row + i) + 1 <= fieldsize - 1) {
                        shipPos.put(row + i + 1, rightColumn);
                    }
                }
            }
        }

//        for (int i = 0; i < usedPosition.size(); i++) {
//            if (usedPosition.equals(shipPos)) {
//                return false;
//            }
//        }
//        usedPosition.putAll(shipPos);
        //System.out.println(usedPosition);
        return true;
    }

    public boolean checkBoardPostion(int row, int column, int length, boolean horizontal) {
        int diffRow = fieldsize - (row + 1);
        int diffCol = fieldsize - (column + 1);

        if (!horizontal) {
            if (length - 1 > diffRow) {
                return false;
            }
        } else {
            if (length - 1 > diffCol) {
                return false;
            }
        }
        //schaut ob es richtige postion ist
        return true;
    }


    public void isShotet() {
        //Enemyshoot(playerBoard);
    }

    public void endGame() {
        usedCord.clear();
    }
}


//            wenn in der end los schleife geht soll er eine combination auswählen aus der usedcord int[][] die es noch gar nicht gibt
//            boolean usedColoumn = false;
//            for(Map.Entry<Integer, int[]> entry : usedCord.entrySet()) {
//                Integer key = entry.getKey();
//                int[] value = entry.getValue();
//                if(value[1] == column){
//                    usedColoumn = true;
//                }
//            }

//            if(!isUsedCord(row,column)){
//                addTousedCord(row,column);
//                boolean lastHit = false;
//                if (nextHit.size() != 0) {
//                    for (Map.Entry<Integer, Integer> entry : nextHit.entrySet()) {
//                        Integer key = entry.getKey();
//                        Integer value = entry.getValue();
//                        //System.out.println(key+ " "+value);
//                        lastHit = player.shoot(key, value);
//                        if (lastHit == true){
//                            hitShipBehind(key,column, this.hitShip);
//                        }
//                        nextHit.remove(key);
//                        break;
//                    }
//                } else {
//                    lastHit = player.shoot(row, column);
//                }
//System.out.println(lastHit);
//lastHit = player.shoot(row, column);
//                if (lastHit) {
//                    hitShipBehind(row, column, this.hitShip);
//                }
//break;