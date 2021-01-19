package com.Battleship.Player;

import com.Battleship.Model.Board;
import com.Battleship.Model.Field;
import com.Battleship.Model.Ship;

import java.util.*;

/**
 * The type Ai network player.
 */
public class AINetworkPlayer extends AIPlayer{


    /**
     * Instantiates a new Ai network player.
     */
    public AINetworkPlayer(){
        super();
    }

    /**
     * AI vs Ai are randomly placed on the field, except for field sizes 5, 6 and 7
     *
     * @return the boolean
     */
    public boolean aisetEnemyShip() {
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
     * AI vs AI analysiert den schuss
     * If the shot has hit a ship, there is a 1 and you can shoot again
     * If you have sunk a ship then a 2 appears and you can shoot one again and
     * If you missed the shot a 0 appears and it's the opponent's turn
     *
     * @param ans the ans
     * @return the boolean
     */
    public boolean AIvsAInextShoot(int ans) {
        if (ans == 1 || ans == 2) {
            isHit = true;
        } else {
            isHit = false;
        }
        if(isHit){
            if(nextHit.size() != 0){
                int[] hited_entry = nextHitnext.get(hashCode(nextRowShoot, nextColumnShoot));
                if (hited_entry[2] == 0) {
                    for (Map.Entry<Integer, int[]> s :nextHitnext.entrySet()) {
                        int[] value_2 = s.getValue();
                        if (nextHit.get(s.getKey()) != null && value_2[2] == 1) {
                            nextHit.remove(s.getKey());
                        }
                    }
                    hitShipBehind(nextRowShoot, nextColumnShoot, true);
                } else {
                    for (Map.Entry<Integer, int[]> s :  nextHitnext.entrySet()) {
                        int[] value_2 = s.getValue();
                        if (nextHit.get(s.getKey()) != null && value_2[2] == 0) {
                            nextHit.remove(s.getKey());
                        }
                    }
                    hitShipBehind(nextRowShoot, nextColumnShoot, false);
                }
            }else{
                hitShipBehind(nextRowShoot, nextColumnShoot);
            }
        }
        addTousedCord(nextRowShoot, nextColumnShoot);
        AIshotAnalys();
        return isHit;
    }

    /**
     * Ai vs Ai shoot randomly
     * When a ship is hit, it checks whether it is Vertikal or Horizontal and destroys the entire Ship
     */
    public void AIshotAnalys(){
        if (nextHit.size() == 0) {
            Random random = new Random();
            while (true) {
                int row = random.nextInt(fieldsize);
                int column = random.nextInt(fieldsize);
                if ((row + column) % 2 == 0) {
                    if (!isUsedCord(row, column)) {
                        nextRowShoot = row;
                        nextColumnShoot = column;
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
                nextRowShoot = nextRow;
                nextColumnShoot = nextColumn;
            }
            nextHit.remove(key);
        }
    }

    /**
     * AI vs AI shot.
     *
     * @param player the player
     */
    public void AIvsAIShot(Board player){
        if(nextRowShoot == 0 && nextColumnShoot == 0 ){
            addTousedCord(0, 0);
        }
        player.aimultiplayerShoot(nextRowShoot, nextColumnShoot);
    }

}
