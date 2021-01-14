package com.Battleship.SpielstandLaden;

import com.Battleship.Model.*;
import com.Battleship.Player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GameObj {

    public SaveField playerButton[][];
    public SaveField enemyButton[][];

    public ArrayList<SaveShip> playerFleet = new ArrayList<>();
    public ArrayList<SaveShip> enemyFleet = new ArrayList<>();

    public int EnemyHealth;
    public int PlayerHealth;
    public Map<Integer, int[]> nextHit = new LinkedHashMap<>();
    public Map<Integer, int[]> nextHitnext = new LinkedHashMap<>();
    public Map<Integer, int[]> usedCord = new HashMap<>();
    public int size;


    public boolean multiplayer = false;

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
            //System.out.println("enemyBoard"+enemyBoard.getFleet());
//            for (Ship s: enemyBoard.getFleet()) {
//                this.enemyFleet.add(new SaveShip(s));
//            }
            EnemyHealth = enemyBoard.getAllHealthEnemy();
            PlayerHealth = enemyBoard.getAllHealthPlayer();
            usedCord =  enemyBoard.getUsedCord();
//            System.out.println("aiOnline from multi " + enemyBoard.aiOnline);
//            usedCord =  enemyBoard.aiOnline.getUsedCord();
//            nextHit = enemyBoard.aiOnline.getNextHit();
//            nextHitnext = enemyBoard.aiOnline.getNextHitnext();
        }

    }


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
