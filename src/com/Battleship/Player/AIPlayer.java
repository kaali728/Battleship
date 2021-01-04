package com.Battleship.Player;

import com.Battleship.Model.Board;
import com.Battleship.Model.Field;
import com.Battleship.Model.Ship;

import java.util.*;

public class AIPlayer {
    public boolean isHit = false;
    public boolean gametrun = false;
    private int fieldsize;
    private ArrayList<Ship> fleet = new ArrayList<>();
    private Map<Integer, int[]> nextHit = new LinkedHashMap<>();
    private Map<Integer, int[]> nextHitnext = new LinkedHashMap<>();
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
        int counter = 10000;

//        for (Ship sj: fleet) {
//            if(sj.getRow() != -1 && sj.getColumn() != -1){
//                enemyBoard.getonebutton(sj.getRow(), sj.getColumn()).setMark(false);
//                if(!sj.getHorizontal()){
//                    enemyBoard.getonebutton(sj.getRow() + 1, sj.getColumn()).setMark(false);
//                }else{
//                    enemyBoard.getonebutton(sj.getRow(), sj.getColumn() +1).setMark(false);
//                }
//
//                sj.setRow(-1);
//                sj.setColumn(-1);
//            }
//        }

        if(fieldsize <= 7){
            for (Ship s: fleet) {
                if(s.getHealth() == 5){

                    enemyBoard.setShip(0,0);
                }
                if(s.getHealth() == 4){
                    enemyBoard.setShip(2,0);
                }
                if(s.getHealth() == 3){
                    enemyBoard.setShip(4,0);
                }
                if(s.getHealth() == 2){
                    enemyBoard.setShip(5,4);
                }
            }

        }else{
            for (Ship s : fleet) {
                while (true) {
                    int row = random.nextInt(fieldsize - 1);
                    int column = random.nextInt(fieldsize - 1);
                    Ship shipret = enemyBoard.setShip(row, column);
                    //counter--;
                    //System.out.println(counter);
//                    if(counter<= 0){
//                        setEnemyShip();
//                    }
                    if (shipret != null) {
                        break;
                    }
                }
            }
        }
    }
    public int health=0;
    public int Health(int health){
        this.health= health;
        return this.health;
    }

    public boolean Enemyshoot(Board player) {
        this.playerBoard = player;
        if (nextHit.size() == 0) {
            Random random = new Random();
            while (true) {
                int row = random.nextInt(fieldsize);
                int column = random.nextInt(fieldsize);
                if ((row + column) % 2 == 0) {
                    if (!isUsedCord(row, column)) {
                        addTousedCord(row, column);
                       isHit = player.shoot(row, column);
                        if (isHit) {
                            this.health--;
                            hitShipBehind(row, column);
//                            System.out.println(isHit);
//                            Enemyshoot(player);
                        }
                        break;
                    }
                }
            }
        } else {
            Map.Entry<Integer, int[]> entry = nextHit.entrySet().iterator().next();
            int key = entry.getKey();
            int[] value = entry.getValue();
            int nextRow = value[0];
            int nextColumn = value[1];
            if (!isUsedCord(nextRow, nextColumn)) {
                isHit = player.shoot(nextRow, nextColumn);
                addTousedCord(nextRow, nextColumn);
                if (isHit) {
                    this.health--;
                    int[] hited_entry = nextHitnext.get(hashCode(nextRow, nextColumn));
                    if (hited_entry[2] == 0) {
                        //hori
                        for (Map.Entry<Integer, int[]> s : nextHitnext.entrySet()) {
                            int[] value_2 = s.getValue();
                            if (nextHit.get(s.getKey()) != null && value_2[2] == 1) {
                                nextHit.remove(s.getKey());
                            }
                        }
                        hitShipBehind(nextRow, nextColumn, true);
                    } else {
                        //verti
                        for (Map.Entry<Integer, int[]> s : nextHitnext.entrySet()) {
                            int[] value_2 = s.getValue();
                            if (nextHit.get(s.getKey()) != null && value_2[2] == 0) {
                                nextHit.remove(s.getKey());
                            }
                        }
                        hitShipBehind(nextRow, nextColumn, false);
                    }
//                    System.out.println(isHit);
//                    Enemyshoot(player);
                }
            }
            nextHit.remove(key);
        }
        return isHit;
    }

//    private void sleep(){
//        try {
//            TimeUnit.MILLISECONDS.timedJoin(100);
//        } catch (InterruptedException ie) {
//            Thread.currentThread().interrupt();
//        }
//    }

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
        boolean hitH = next;
        boolean hitV = !next;
        if (column + 1 <= fieldsize - 1 && hitH) {
            int[] cord = {row, column + 1};
            if (usedCord.get(hashCode(row, column + 1)) == null) {
                nextHit.put(hashCode(row, column + 1), cord);
                int[] cordSide = {row, column + 1, 0};
                nextHitnext.put(hashCode(row, column + 1), cordSide);
                hitH = hori(row, column, true);
                hitV = vert(row, column, false);
            }
        }
        if (row - 1 >= 0 && hitV) {
            int[] cord = {row - 1, column};
            if (usedCord.get(hashCode(row - 1, column)) == null) {
                nextHit.put(hashCode(row - 1, column), cord);
                int[] cordSide = {row - 1, column, 1};
                nextHitnext.put(hashCode(row - 1, column), cordSide);
                hitH = hori(row, column, false);
                hitV = vert(row, column, true);
            }
        }
        if (column - 1 >= 0 && hitH) {
            int[] cord = {row, column - 1};
            if (usedCord.get(hashCode(row, column - 1)) == null) {
                nextHit.put(hashCode(row, column - 1), cord);
                int[] cordSide = {row, column - 1, 0};
                nextHitnext.put(hashCode(row, column - 1), cordSide);
                hitH = hori(row, column, true);
                hitV = vert(row, column, false);
            }
        }

        if (row + 1 <= fieldsize - 1 && hitV) {
            int[] cord = {row + 1, column};
            if (usedCord.get(hashCode(row + 1, column)) == null) {
                nextHit.put(hashCode(row + 1, column), cord);
                int[] cordSide = {row + 1, column, 1};
                nextHitnext.put(hashCode(row + 1, column), cordSide);
                hitH = hori(row, column, false);
                hitV = vert(row, column, true);
            }
        }
    }

    public void hitShipBehind(int row, int column) {
        if (column + 1 <= fieldsize - 1) {
            int[] cord = {row, column + 1};
            if (usedCord.get(hashCode(row, column + 1)) == null) {
                nextHit.put(hashCode(row, column + 1), cord);
                int[] cordSide = {row, column + 1, 0};
                nextHitnext.put(hashCode(row, column + 1), cordSide);
            }
        }
        if (row - 1 >= 0) {
            int[] cord = {row - 1, column};
            if (usedCord.get(hashCode(row - 1, column)) == null) {
                nextHit.put(hashCode(row - 1, column), cord);
                int[] cordSide = {row - 1, column, 1};
                nextHitnext.put(hashCode(row - 1, column), cordSide);
            }
        }
        if (column - 1 >= 0) {
            int[] cord = {row, column - 1};
            if (usedCord.get(hashCode(row, column - 1)) == null) {
                nextHit.put(hashCode(row, column - 1), cord);
                int[] cordSide = {row, column - 1, 0};
                nextHitnext.put(hashCode(row, column - 1), cordSide);
            }
        }

        if (row + 1 <= fieldsize - 1) {
            int[] cord = {row + 1, column};
            if (usedCord.get(hashCode(row + 1, column)) == null) {
                nextHit.put(hashCode(row + 1, column), cord);
                int[] cordSide = {row + 1, column, 1};
                nextHitnext.put(hashCode(row + 1, column), cordSide);
            }
        }
    }

    public boolean hori(int row, int column, boolean hori) {
        if (hori) {
            int[] c = {row + 1, column};
            int[] c2 = {row - 1, column};
            usedCord.put(hashCode(row + 1, column), c);
            usedCord.put(hashCode(row - 1, column), c2);
            nextHit.remove(hashCode(row + 1, column));
            nextHit.remove(hashCode(row - 1, column));
            return true;
        }
        return false;
    }

    public boolean vert(int row, int column, boolean verti) {
        if (verti) {
            int[] c = {row, column + 1};
            int[] c2 = {row, column - 1};
            usedCord.put(hashCode(row, column + 1), c);
            usedCord.put(hashCode(row, column - 1), c2);
            nextHit.remove(hashCode(row, column + 1));
            nextHit.remove(hashCode(row, column - 1));
            return true;
        }
        return false;
    }

    public void endGame() {
        usedCord.clear();
    }
}