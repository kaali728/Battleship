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
     * Aiset enemy ship boolean.
     *
     * @return the boolean
     */
    public boolean aisetEnemyShip() {
        Random random = new Random();

        if(fieldsize <= 7){
            if(fieldsize == 5){
                for (Ship s: fleet) {
                    if(s.getHealth() == 5){
                        enemyBoard.aisetShip(0,0);
                    }
                    if(s.getHealth() == 4){
                        enemyBoard.aisetShip(2,0);
                    }
                    if(s.getHealth() == 3){
                        enemyBoard.aisetShip(4,0);
                    }
                    if(s.getHealth() == 2){
                        enemyBoard.aisetShip(5,4);
                    }
                }
            }else{
                for (Ship s: fleet) {
                    if(s.getHealth() == 5){
                        enemyBoard.aisetShip(0,0);
                    }
                    if(s.getHealth() == 4){
                        enemyBoard.aisetShip(2,0);
                    }
                    if(s.getHealth() == 3){
                        enemyBoard.aisetShip(4,0);
                    }
                    if(s.getHealth() == 2){
                        enemyBoard.aisetShip(5,4);
                    }
                }
            }

            return true;
        }else{
            for (Ship s : fleet) {
                while (true) {
                    int row = random.nextInt(fieldsize - 1);
                    int column = random.nextInt(fieldsize - 1);
                    Ship shipret = enemyBoard.aisetShip(row, column);
                    if (shipret != null) {
                        break;
                    }
                }
            }
            return true;
        }
    }

    /**
     * A ivs a inext shoot boolean.
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
                    //hori
                    System.out.println("horizental");
                    for (Map.Entry<Integer, int[]> s :nextHitnext.entrySet()) {
                        int[] value_2 = s.getValue();
                        if (nextHit.get(s.getKey()) != null && value_2[2] == 1) {
                            nextHit.remove(s.getKey());
                        }
                    }
                    hitShipBehind(nextRowShoot, nextColumnShoot, true);
                } else {
                    //verti
                    System.out.println("vertical");
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
     * A ishot analys.
     */
    public void AIshotAnalys(){
        if (nextHit.size() == 0) {
            Random random = new Random();
            while (true) {
                int row = random.nextInt(fieldsize);
                int column = random.nextInt(fieldsize);
                if ((row + column) % 2 == 0) {
                    if (!isUsedCord(row, column)) {
                        //addTousedCord(row, column);
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
     * A ivs ai shot.
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
