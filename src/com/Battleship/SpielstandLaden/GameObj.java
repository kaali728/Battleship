package com.Battleship.SpielstandLaden;

import com.Battleship.Model.Board;

public class GameObj {

    private Board player;
    private  Board enemy;

    public GameObj(Board playerBoard, Board enemyBoard){
        this.player= playerBoard;
        this.enemy = enemyBoard;
    }
}
