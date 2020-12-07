package com.Battleship.Player;

import com.Battleship.Model.Board;
import com.Battleship.Model.Field;
import com.Battleship.Model.Ship;

import java.util.*;

public class AIPlayer {
    private int fieldsize;
    private ArrayList<Ship> fleet = new ArrayList<>();
    private  Map<Integer, int[]>  nextHit = new LinkedHashMap<>();
    private  Map<Integer, int[]>  nextHitnext = new LinkedHashMap<>();
    private Map<Integer, int[]> usedCord = new HashMap<>();

    private Board enemyBoard;
    private Board playerBoard;

    public AIPlayer() {
        fieldsize = 0;
    }

    private Field isMarked;

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
                            //hitShipBehind(row, column);
                            hitShipBehind(row, column);
                            // hitShipBehindHori(row, column);
                        }
                        break;
                    }
                }
            }
        }else{
            boolean isHit = false;
            Map.Entry<Integer,int[]> entry = nextHit.entrySet().iterator().next();
            // nextHit.entrySet().toArray()[0] up
            // nextHit.entrySet().toArray()[1] left
            // nextHit.entrySet().toArray()[2] down
            // nextHit.entrySet().toArray()[3] right
            int key= entry.getKey();
            int[] value = entry.getValue();
            int nextRow = value[0];
            int nextColumn = value[1];
            if(!isUsedCord(nextRow,nextColumn)) {
                isHit = player.shoot(nextRow, nextColumn);
                addTousedCord(nextRow, nextColumn);
                if (isHit) {
                    int[] hited_entry = nextHitnext.get(hashCode(nextRow,nextColumn));
                    if(hited_entry[2] == 0){
                        //hori
                        for (Map.Entry<Integer, int[]> s: nextHitnext.entrySet()) {
                            int[] value_2 = s.getValue();
                            if(nextHit.get(s.getKey()) != null && value_2[2]==1){
                                nextHit.remove(s.getKey());
                            }
                        }
                        hitShipBehind(nextRow, nextColumn, true);
                    }else{
                        //verti
                        for (Map.Entry<Integer, int[]> s: nextHitnext.entrySet()) {
                            int[] value_2 = s.getValue();
                            if(nextHit.get(s.getKey()) != null && value_2[2]==0){
                                nextHit.remove(s.getKey());
                            }
                        }
                        hitShipBehind(nextRow, nextColumn, false);
                    }
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

    public void hitShipBehind(int row, int column, boolean next) {
        boolean hitH= next;
        boolean hitV= !next;
        if (column + 1 <= fieldsize-1 && hitH) {
            int[] cord = {row, column +1};
            if(usedCord.get(hashCode(row, column + 1)) == null) {
                nextHit.put(hashCode(row, column + 1), cord);
                int[] cordSide = {row, column + 1, 0};
                nextHitnext.put(hashCode(row, column + 1), cordSide);
                hitH = hori(row, column, true);
                hitV = vert(row, column, false);
            }
        }
        if (row - 1 >= 0 && hitV) {
            int[] cord = {row -1, column};
            if(usedCord.get(hashCode(row - 1,column)) == null) {
                nextHit.put(hashCode(row - 1, column), cord);
                int[] cordSide = {row - 1, column, 1};
                nextHitnext.put(hashCode(row - 1, column), cordSide);
                hitH = hori(row, column, false);
                hitV = vert(row, column, true);
            }
        }
        if (column - 1 >= 0 && hitH) {
            int[] cord = {row, column-1};
            if(usedCord.get(hashCode(row,column-1)) == null) {
                nextHit.put(hashCode(row, column - 1), cord);
                int[] cordSide = {row, column - 1, 0};
                nextHitnext.put(hashCode(row, column - 1), cordSide);
                hitH = hori(row, column, true);
                hitV = vert(row, column, false);
            }
        }

        if (row + 1 <= fieldsize-1 && hitV) {
            int[] cord = {row+1, column};
            if(usedCord.get(hashCode(row + 1, column)) == null) {
                nextHit.put(hashCode(row + 1, column), cord);
                int[] cordSide = {row + 1, column, 1};
                nextHitnext.put(hashCode(row + 1, column), cordSide);
                hitH = hori(row, column, false);
                hitV = vert(row, column, true);
            }
        }
    }

    public void hitShipBehind(int row, int column) {
        if (column + 1 <= fieldsize-1) {
            int[] cord = {row, column +1};
            if(usedCord.get(hashCode(row, column + 1)) == null) {
                nextHit.put(hashCode(row, column + 1), cord);
                int[] cordSide = {row, column + 1, 0};
                nextHitnext.put(hashCode(row, column + 1), cordSide);
            }
        }
        if (row - 1 >= 0) {
            int[] cord = {row -1, column};
            if(usedCord.get(hashCode(row - 1,column)) == null) {
                nextHit.put(hashCode(row - 1, column), cord);
                int[] cordSide = {row - 1, column, 1};
                nextHitnext.put(hashCode(row - 1, column), cordSide);
            }
        }
        if (column - 1 >= 0) {
            int[] cord = {row, column-1};
            if(usedCord.get(hashCode(row,column-1)) == null) {
                nextHit.put(hashCode(row, column - 1), cord);
                int[] cordSide = {row, column - 1, 0};
                nextHitnext.put(hashCode(row, column - 1), cordSide);
            }
        }

        if (row + 1 <= fieldsize-1) {
            int[] cord = {row+1, column};
            if(usedCord.get(hashCode(row + 1, column)) == null) {
                nextHit.put(hashCode(row + 1, column), cord);
                int[] cordSide = {row + 1, column, 1};
                nextHitnext.put(hashCode(row + 1, column), cordSide);
            }
        }
    }

    //
//
//    public void hitShipBehindHori(int row, int column) {
//        boolean hitH= false;
//        boolean hitV= true;
//
//        if (row - 1 >= 0 && hitV) {
//            int[] cord = {row -1, column};
//            if(usedCord.get(hashCode(row - 1,column)) == null) {
//                nextHit.put(hashCode(row - 1, column), cord);
//                hitH = hori(row, column, true);
//                hitV = vert(row, column, false);
//                int[] cordSide = {row - 1, column, 1};
//                nextHitnext.put(hashCode(row - 1, column), cordSide);
//            }
//        }
//
//        if (row + 1 <= fieldsize-1 && hitV) {
//            int[] cord = {row+1, column};
//            if(usedCord.get(hashCode(row + 1, column)) == null) {
//                nextHit.put(hashCode(row + 1, column), cord);
//                hitH = hori(row, column, true);
//                int[] cordSide = {row + 1, column, 1};
//                nextHitnext.put(hashCode(row + 1, column), cordSide);
//            }
//        }
//
//        if (column + 1 <= fieldsize-1 && hitH) {
//            int[] cord = {row, column +1};
//            if(usedCord.get(hashCode(row, column + 1)) == null) {
//                nextHit.put(hashCode(row, column + 1), cord);
//                int[] cordSide = {row, column + 1, 0};
//                nextHitnext.put(hashCode(row , column + 1), cordSide);
//            }
//        }
//        if (column - 1 >= 0 && hitH) {
//            int[] cord = {row, column-1};
//            if(usedCord.get(hashCode(row,column-1)) == null) {
//                nextHit.put(hashCode(row, column - 1), cord);
//                int[] cordSide = {row, column - 1, 0};
//                nextHitnext.put(hashCode(row , column - 1), cordSide);
//            }
//        }
//    }
//
//    public void hitShipBehindVerti(int row, int column) {
//        boolean hitH= false;
//        boolean hitV= true;
//
//        if (column + 1 <= fieldsize-1 && hitH) {
//            int[] cord = {row, column +1};
//            if(usedCord.get(hashCode(row, column + 1)) == null) {
//                nextHit.put(hashCode(row, column + 1), cord);
//                hitH = hori(row, column, false);
//                hitV = vert(row, column, true);
//                int[] cordSide = {row - 1, column, 1};
//                nextHitnext.put(hashCode(row - 1, column), cordSide);
//            }
//        }
//        if (column - 1 >= 0 && hitH) {
//            int[] cord = {row, column-1};
//            if(usedCord.get(hashCode(row,column-1)) == null) {
//                nextHit.put(hashCode(row, column - 1), cord);
//                hitV = vert(row, column, false);
//                int[] cordSide = {row + 1, column, 1};
//                nextHitnext.put(hashCode(row + 1, column), cordSide);
//            }
//        }
//
//        if (row - 1 >= 0 && hitV) {
//            int[] cord = {row -1, column};
//            if(usedCord.get(hashCode(row - 1,column)) == null) {
//                nextHit.put(hashCode(row - 1, column), cord);
//                int[] cordSide = {row, column + 1, 0};
//                nextHitnext.put(hashCode(row , column + 1), cordSide);
//            }
//        }
//
//        if (row + 1 <= fieldsize-1 && hitV) {
//            int[] cord = {row+1, column};
//            if(usedCord.get(hashCode(row + 1, column)) == null) {
//                nextHit.put(hashCode(row + 1, column), cord);
//                int[] cordSide = {row, column - 1, 0};
//                nextHitnext.put(hashCode(row , column - 1), cordSide);
//            }
//        }
//    }
//
    public boolean hori (int row, int column, boolean hori){
        if(hori){
            int[] c = { row + 1, column};
            int[] c2 = {row - 1,column};
            usedCord.put(hashCode(row + 1, column), c);
            usedCord.put(hashCode(row - 1,column), c2);
            nextHit.remove(hashCode(row + 1, column));
            nextHit.remove(hashCode(row - 1,column));
            return true;
        }
        return false;
    }
    public boolean vert (int row, int column, boolean verti){
        if(verti){
            int[] c = {row, column + 1};
            int[] c2 = {row,column - 1};
            usedCord.put(hashCode(row, column + 1), c);
            usedCord.put(hashCode(row,column - 1), c2);
            nextHit.remove(hashCode(row, column + 1));
            nextHit.remove(hashCode(row,column - 1));
            return true;
        }
        return false;
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