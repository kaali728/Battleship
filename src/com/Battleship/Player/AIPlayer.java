package com.Battleship.Player;

import com.Battleship.Model.Board;
import com.Battleship.Model.Field;
import com.Battleship.Model.Ship;

import java.util.*;

public class AIPlayer {
    public boolean isHit = false;
    public int nextRowShoot;
    public int nextColumnShoot;
    public boolean gametrun = false;
    public int fieldsize;
    public ArrayList<Ship> fleet = new ArrayList<>();
    public Map<Integer, int[]> nextHit = new LinkedHashMap<>();
    public Map<Integer, int[]> nextHitnext = new LinkedHashMap<>();
    public Map<Integer, int[]> usedCord = new HashMap<>();
    public Map<Integer, int[]> tousedCord = new HashMap<>();

    public Board enemyBoard;
    public Board playerBoard;

    public boolean sunkedShip = false;

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

    public void setUsedCord(Map<Integer, int[]> usedCord) {
        this.usedCord = usedCord;
    }

    public Map<Integer, int[]> getUsedCord() {
        return usedCord;
    }

    public void setNextHit(Map<Integer, int[]> nextHit) {
        this.nextHit = nextHit;
    }

    public Map<Integer, int[]> getNextHit() {
        return nextHit;
    }

    public void setNextHitnext(Map<Integer, int[]> nextHitnext) {
        this.nextHitnext = nextHitnext;
    }

    public Map<Integer, int[]> getNextHitnext() {
        return nextHitnext;
    }

    public void setEnemyBoard(Board enemyBoard) {
        this.enemyBoard = enemyBoard;
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public boolean setEnemyShip() {
        Random random = new Random();
        int carrshipCount = 0;
        int battshipCount = 0;
        int subshipCount = 0;
        int destroshipCount = 0;
        for (Ship s: fleet) {
            if(s.getHealth() == 5){
                carrshipCount++;
            }
            if(s.getHealth() == 4){
                battshipCount++;
            }
            if(s.getHealth() == 3){
                subshipCount++;
            }
            if(s.getHealth() == 2){
                destroshipCount++;
            }
        }
        if(fieldsize == 5 && (carrshipCount==0 && battshipCount==1 && subshipCount==1 && destroshipCount==1 ||
                carrshipCount==1 && battshipCount==0 && subshipCount==1 && destroshipCount==1 || carrshipCount==1 && battshipCount==1 && subshipCount==0 && destroshipCount==1 ||
                carrshipCount==1 && battshipCount==1 && subshipCount==1 && destroshipCount==0)){
            boolean carrship = false;
            boolean battship = false;
            boolean subship = false;
            boolean destroship = false;
            for (Ship s: fleet) {
                if(s.getHealth() == 5){
                    enemyBoard.setShip(0,0);
                    carrship = true;
                }
                if(s.getHealth() == 4){
                    if(carrship){
                        enemyBoard.setShip(2,0);
                        battship = true;
                    }else{
                        enemyBoard.setShip(0,0);
                    }
                }
                if(s.getHealth() == 3){
                    if(battship){
                        enemyBoard.setShip(4,0);
                        subship = true;
                    }else{
                        enemyBoard.setShip(2,0);
                    }

                }
                if(s.getHealth() == 2){
                    if(subship){
                        enemyBoard.setShip(4,3);
                        destroship = true;
                    }else{
                        enemyBoard.setShip(4,0);
                    }
                }
            }
            return true;
        }
        if((fieldsize == 7 || fieldsize == 6) && carrshipCount==1 && battshipCount==1 && subshipCount==1 && destroshipCount==1) {
            for (Ship s : fleet) {
                if (s.getHealth() == 5) {
                    enemyBoard.setShip(0, 0);
                }
                if (s.getHealth() == 4) {
                    enemyBoard.setShip(2, 0);
                }
                if (s.getHealth() == 3) {
                    enemyBoard.setShip(4, 0);
                }
                if (s.getHealth() == 2) {
                    enemyBoard.setShip(5, 4);
                }
            }
        }else{
            for (Ship s : fleet) {
                while (true) {
                    int row = random.nextInt(fieldsize - 1);
                    int column = random.nextInt(fieldsize - 1);
                    if (!isUsedCordforSet(row, column)) {
                        UsedcordforSet(row, column);
                        Ship shipret = enemyBoard.setShip(row, column);
                        if (shipret != null) {
                            break;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }


    public boolean Enemyshoot(Board player) {
        this.playerBoard = player;
        if(!player.isGameOver()){
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
        return false;
    }



//    private void sleep(){
//        try {
//            TimeUnit.MILLISECONDS.timedJoin(100);
//        } catch (InterruptedException ie) {
//            Thread.currentThread().interrupt();
//        }
//    }

    public void UsedcordforSet(int row, int column) {
        int[] entry = {row, column};
        tousedCord.put(hashCode(row, column), entry);
    }

    public boolean isUsedCordforSet(int row, int column) {
        for (Map.Entry<Integer, int[]> entry : tousedCord.entrySet()) {
            int[] value = entry.getValue();
            if (value[0] == row && value[1] == column) {
                return true;
            }
        }
        return false;
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
                hitV = vert(row, column, false);
            }
        }

        if (row + 1 <= fieldsize - 1 && hitV) {
            int[] cord = {row + 1, column};
            if (usedCord.get(hashCode(row + 1, column)) == null) {
                nextHit.put(hashCode(row + 1, column), cord);
                int[] cordSide = {row + 1, column, 1};
                nextHitnext.put(hashCode(row + 1, column), cordSide);
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

    public void sunkedShip(){
        sunkedShip = true;
    }
    public void notSunked(){
        sunkedShip =false;
    }
    public void endGame() {
        usedCord.clear();
        tousedCord.clear();
    }

    public int getNextColumnShoot() {
        return nextColumnShoot;
    }

    public int getNextRowShoot() {
        return nextRowShoot;
    }
}