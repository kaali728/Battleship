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
     * The Used coordinates for shot.
     */
    public Map<Integer, int[]> usedCord = new HashMap<>();

    /**
     * The Used coordinates for placeing a ship.
     */
    public Map<Integer, int[]> tousedCord = new HashMap<>();

    /**
     * The Enemy board.
     */
    public Board enemyBoard;
    /**
     * The Player board.
     */
    public Board playerBoard;

    /**
     * Instantiates a new Ai player.
     */
    public AIPlayer() {
        fieldsize = 0;
    }


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
     * Enemyships are randomly placed on the field, except for field sizes 5, 6 and 7
     *
     * @return the enemy ship
     */
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
            return true;
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
    }


    /**
     * Enemy shoots randomly at the Playerfield
     * When a ship is hit, it checks whether it is Vertikal or Horizontal and destroys the entire Ship
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
                            for (Map.Entry<Integer, int[]> s : nextHitnext.entrySet()) {
                                int[] value_2 = s.getValue();
                                if (nextHit.get(s.getKey()) != null && value_2[2] == 1) {
                                    nextHit.remove(s.getKey());
                                }
                            }
                            hitShipBehind(nextRow, nextColumn, true);
                        } else {
                            for (Map.Entry<Integer, int[]> s : nextHitnext.entrySet()) {
                                int[] value_2 = s.getValue();
                                if (nextHit.get(s.getKey()) != null && value_2[2] == 0) {
                                    nextHit.remove(s.getKey());
                                }
                            }
                            hitShipBehind(nextRow, nextColumn, false);
                        }
                    }
                }
                nextHit.remove(key);
            }
            return isHit;
        }
        return false;
    }


    /**
     * Put coordinate for set Ship into a Hashmap
     *
     * @param row
     * @param column
     */
    public void UsedcordforSet(int row, int column) {
        int[] entry = {row, column};
        tousedCord.put(hashCode(row, column), entry);
    }

    /**
     *
     * Checks whether the coordinates were used when placing the ships or not
     *
     * @param row
     * @param column
     * @return
     */
    public boolean isUsedCordforSet(int row, int column) {
        for (Map.Entry<Integer, int[]> entry : tousedCord.entrySet()) {
            int[] value = entry.getValue();
            if (value[0] == row && value[1] == column) {
                return true;
            }
        }
        return false;
    }

    /**
     * Put coordinate for shoot Ship into a Hashmap
     *
     * @param row    the row
     * @param column the column
     */
    public void addTousedCord(int row, int column) {
        int[] entry = {row, column};
        usedCord.put(hashCode(row, column), entry);
    }


    /**
     *
     * Checks whether the coordinate was used when shooting the ships or not
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
     * calculates a hash
     *
     * @param x the x
     * @param y the y
     * @return the int
     */
    public int hashCode(int x, int y) {
        return x * 31 + y;
    }

    /**
     *
     * If a ship has been hit, the above, right, left and below are checked to see if it can continue
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
     * If he found out in which direction it goes, the rest of the ship will be destroyed
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
     * Checks whether it is horizontal
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
     * Checks whether it is vertical
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
     * If the game ends prematurely, the Hashmaps should be emptied
     */
    public void endGame() {
        usedCord.clear();
        tousedCord.clear();
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