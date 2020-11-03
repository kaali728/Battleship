package com.Battleship.Model;

import javax.swing.*;
import java.awt.*;

public class Board extends Canvas {
    int size=0;
    Object[][] gridArray;
    public Board(int size){
        this.size=size;
        gridArray = new Object[size][size];
        initlayout();
    }
    public void initlayout(){
        setBackground(Color.DARK_GRAY);
        setSize(new Dimension(500,500));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i=0; i< gridArray.length; i++){
            for (int j=0; j<gridArray[i].length; j++){
                gridArray[i][j] = null;
                g.fillRect(i*(this.getWidth()/size), j*(this.getHeight()/size), (this.getWidth()/size)-2,(this.getHeight()/size)-2);
            }
        }
    }
}
