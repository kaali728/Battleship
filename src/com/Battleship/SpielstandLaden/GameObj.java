package com.Battleship.SpielstandLaden;

import com.Battleship.Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Game obj. This Class will
 * be used for saving in json. Its converts the Ships and Field object to new Objects Saveship and SaveField
 */
public class GameObj {

    /**
     * The Player button.
     */
    public SaveField playerButton[][];
    /**
     * The Enemy button.
     */
    public SaveField enemyButton[][];

    /**
     * The Player fleet.
     */
    public ArrayList<SaveShip> playerFleet = new ArrayList<>();
    /**
     * The Enemy fleet.
     */
    public ArrayList<SaveShip> enemyFleet = new ArrayList<>();

    /**
     * The Enemy health.
     */
    public int EnemyHealth;
    /**
     * The Player health.
     */
    public int PlayerHealth;
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
     * The Size.
     */
    public int size;
    /***
     * Count of rest Ships to shoot
     */
    public int allShipsCount;

    public boolean multiplayer = false;
    /**
     * Instantiates a new Game obj.
     *
     * @param playerBoard the player board
     * @param enemyBoard  the enemy board
     */
    public GameObj(Board playerBoard, Board enemyBoard, boolean multiplayer){
        if(!multiplayer){
            this.size = playerBoard.getButton().length;
            playerButton = new SaveField[size][size];
            enemyButton = new SaveField[size][size];

            setSavedField(playerBoard.getButton(), 1);
            setSavedField(enemyBoard.getButton(), 2);
            for (Ship s: playerBoard.getFleet()) {
                this.playerFleet.add(new SaveShip(s));
            }
            //System.out.println("enemyBoard"+enemyBoard.getFleet());
            for (Ship s: enemyBoard.getFleet()) {
                this.enemyFleet.add(new SaveShip(s));
            }
            EnemyHealth = enemyBoard.getAllHealthEnemy();
            PlayerHealth = enemyBoard.getAllHealthPlayer();
            usedCord =  enemyBoard.aiPlayer.getUsedCord();
            nextHit = enemyBoard.aiPlayer.getNextHit();
            nextHitnext = enemyBoard.aiPlayer.getNextHitnext();
        }else{
            this.size = playerBoard.getButton().length;
            playerButton = new SaveField[size][size];
            enemyButton = new SaveField[size][size];

            setSavedField(playerBoard.getButton(), 1);
            setSavedField(enemyBoard.getButton(), 2);
            for (Ship s: playerBoard.getFleet()) {
                this.playerFleet.add(new SaveShip(s));
            }
            EnemyHealth = enemyBoard.getAllHealthEnemy();
            PlayerHealth = enemyBoard.getAllHealthPlayer();
            usedCord =  enemyBoard.getUsedCord();
        }

    }

    public GameObj(Board playerBoard, Board enemyBoard, boolean multiplayer, int allShipsCount){
        if(!multiplayer){
            this.size = playerBoard.getButton().length;
            playerButton = new SaveField[size][size];
            enemyButton = new SaveField[size][size];

            setSavedField(playerBoard.getButton(), 1);
            setSavedField(enemyBoard.getButton(), 2);
            for (Ship s: playerBoard.getFleet()) {
                this.playerFleet.add(new SaveShip(s));
            }
            for (Ship s: enemyBoard.getFleet()) {
                this.enemyFleet.add(new SaveShip(s));
            }
            EnemyHealth = enemyBoard.getAllHealthEnemy();
            PlayerHealth = enemyBoard.getAllHealthPlayer();
            usedCord =  enemyBoard.aiPlayer.getUsedCord();
            nextHit = enemyBoard.aiPlayer.getNextHit();
            nextHitnext = enemyBoard.aiPlayer.getNextHitnext();
        }else{
            this.size = playerBoard.getButton().length;
            playerButton = new SaveField[size][size];
            enemyButton = new SaveField[size][size];
            this.allShipsCount = allShipsCount;
            setSavedField(playerBoard.getButton(), 1);
            setSavedField(enemyBoard.getButton(), 2);
            for (Ship s: playerBoard.getFleet()) {
                this.playerFleet.add(new SaveShip(s));
            }
            EnemyHealth = enemyBoard.getAllHealthEnemy();
            PlayerHealth = enemyBoard.getAllHealthPlayer();
            usedCord =  enemyBoard.getUsedCord();
        }

    }


    /**
     * Set saved field. Converting Field to SaveField
     *
     * @param button the button
     * @param who    the who
     */
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
