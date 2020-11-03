package com.Battleship.Model;

import java.awt.*;

public class Board extends Canvas {
    int size=0;
    public Board(int size){
        this.size=size;
        initlayout();
    }
    public void initlayout(){
        setBackground(Color.BLUE);
        setSize(new Dimension(500,500));
    }
}
