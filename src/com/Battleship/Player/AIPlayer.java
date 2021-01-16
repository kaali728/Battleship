package com.Battleship.Player;

import com.Battleship.Model.Board;
import com.Battleship.Model.Field;
import com.Battleship.Model.Ship;

import java.util.*;

/**
 * The type Ai player.
 */
public class AIPlayer {
    /**
     * The Is hit.
     */
    public boolean isHit = false;
    /**
     * The Next row shoot.
     */
    public int nextRowShoot;
    /**
     * The Next column shoot.
     */
    public int nextColumnShoot;
    /**
     * The Gametrun.
     */
    public boolean gametrun = false;
    /**
     * The Fieldsize.
     */
    public int fieldsize;
    /**
     * The Fleet.
     */
    public ArrayList<Ship> fleet = new ArrayList<>();
    /**
     * The Next hit.
     */
    public Map<Integer, int[]> nextHit = new LinkedHashMap<>();
    /**
     * The Next hitnext.
     */
    public Map<Integer, int[]> nextHitnext = new LinkedHashMap<>();
    /**
     * The Used cord.
     */
    public Map<Integer, int[]> usedCord = new HashMap<>();

    /**
     * The Enemy board.
     */
    public Board enemyBoard;
    /**
     * The Player board.
     */
    public Board playerBoard;

    /**
     * The Sunked ship.
     */
    public boolean sunkedShip = false;

    /**
     * Instantiates a new Ai player.
     */
    public AIPlayer() {
        fieldsize = 0;
    }

    private Field isMarked;

    /**
     * Sets fieldsize.
     *
     * @param fieldsize the fieldsize
     */
    public void setFieldsize(int fieldsize) {
        this.fieldsize = fieldsize;
    }

    /**
     * Gets fieldsize.
     *
     * @return the fieldsize
     */
    public int getFieldsize() {
        return fieldsize;
    }

    /**
     * Gets fleet.
     *
     * @return the fleet
     */
    public ArrayList<Ship> getFleet() {
        return fleet;
    }

    /**
     * Sets fleet.
     *
     * @param fleet the fleet
     */
    public void setFleet(ArrayList<Ship> fleet) {
        this.fleet = fleet;
    }

    /**
     * Gets enemy board.
     *
     * @return the enemy board
     */
    public Board getEnemyBoard() {
        return enemyBoard;
    }

    /**
     * Sets used cord.
     *
     * @param usedCord the used cord
     */
    public void setUsedCord(Map<Integer, int[]> usedCord) {
        this.usedCord = usedCord;
    }

    /**
     * Gets used cord.
     *
     * @return the used cord
     */
    public Map<Integer, int[]> getUsedCord() {
        return usedCord;
    }

    /**
     * Sets next hit.
     *
     * @param nextHit the next hit
     */
    public void setNextHit(Map<Integer, int[]> nextHit) {
        this.nextHit = nextHit;
    }

    /**
     * Gets next hit.
     *
     * @return the next hit
     */
    public Map<Integer, int[]> getNextHit() {
        return nextHit;
    }

    /**
     * Sets next hitnext.
     *
     * @param nextHitnext the next hitnext
     */
    public void setNextHitnext(Map<Integer, int[]> nextHitnext) {
        this.nextHitnext = nextHitnext;
    }

    /**
     * Gets next hitnext.
     *
     * @return the next hitnext
     */
    public Map<Integer, int[]> getNextHitnext() {
        return nextHitnext;
    }

    /**
     * Sets enemy board.
     *
     * @param enemyBoard the enemy board
     */
    public void setEnemyBoard(Board enemyBoard) {
        this.enemyBoard = enemyBoard;
    }

    /**
     * Gets player board.
     *
     * @return the player board
     */
    public Board getPlayerBoard() {
        return playerBoard;
    }

    /**
     * Sets enemy ship.
     *
     * @return the enemy ship
     */
    public boolean setEnemyShip() {
        Random random = new Random();

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
                    if (shipret != null) {
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }


    /**
     * Enemyshoot boolean.
     *
     * @param player the player
     * @return the boolean
     */
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


    /**
     * Add toused cord.
     *
     * @param row    the row
     * @param column the column
     */
    public void addTousedCord(int row, int column) {
        int[] entry = {row, column};
        usedCord.put(hashCode(row, column), entry);
    }


    /**
     * Is used cord boolean.
     *
     * @param row    the row
     * @param column the column
     * @return the boolean
     */
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

    /**
     * Hash code int.
     *
     * @param x the x
     * @param y the y
     * @return the int
     */
    public int hashCode(int x, int y) {
        return x * 31 + y;
    }

    /**
     * Hit ship behind.
     *
     * @param row    the row
     * @param column the column
     * @param next   the next
     */
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

    /**
     * Hit ship behind.
     *
     * @param row    the row
     * @param column the column
     */
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

    /**
     * Hori boolean.
     *
     * @param row    the row
     * @param column the column
     * @param hori   the hori
     * @return the boolean
     */
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

    /**
     * Vert boolean.
     *
     * @param row    the row
     * @param column the column
     * @param verti  the verti
     * @return the boolean
     */
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

    /**
     * Sunked ship.
     */
    public void sunkedShip(){
        sunkedShip = true;
    }

    /**
     * Not sunked.
     */
    public void notSunked(){
        sunkedShip =false;
    }

    /**
     * End game.
     */
    public void endGame() {
        usedCord.clear();
    }

    /**
     * Gets next column shoot.
     *
     * @return the next column shoot
     */
    public int getNextColumnShoot() {
        return nextColumnShoot;
    }

    /**
     * Gets next row shoot.
     *
     * @return the next row shoot
     */
    public int getNextRowShoot() {
        return nextRowShoot;
    }
}