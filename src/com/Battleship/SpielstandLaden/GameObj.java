package com.Battleship.SpielstandLaden;

import com.Battleship.Model.*;
import com.Battleship.Player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Game obj.
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

    /**
     * Instantiates a new Game obj.
     *
     * @param playerBoard the player board
     * @param enemyBoard  the enemy board
     */
    public GameObj(Board playerBoard, Board enemyBoard){
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
    }


    /**
     * Set saved field.
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
