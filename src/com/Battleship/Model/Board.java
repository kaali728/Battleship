package com.Battleship.Model;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    int size=0;
    public static JButton button[][];

    JPanel buttonPanel;

    static int rows;
    static int columns;
    GridBagConstraints gbc;



    public Object[][] gridArray;
    public Board(int size){
        this.size=size;
        this.button = new JButton[size][size];
        gridArray = new Object[size][size];
        initlayout();
    }
    public void initlayout(){
        setBackground(Color.BLUE);
        setSize(new Dimension(650,650));
        setLayout(new GridBagLayout());
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(size, size));

        for (rows = 0; rows < size; rows++) {
            for (columns = 0; columns < size; columns++) {
                button[rows][columns] = new JButton();
                button[rows][columns].setBackground(Color.GRAY); //default Gray color is easily interchangable here
                button[rows][columns].setPreferredSize(new Dimension(650/size,650/size));
                //action listener allows for program to react to user button presses in correct grid quadrant
                //button[rows][columns].addActionListener(new TilePressed(rows, columns));
                buttonPanel.add(button[rows][columns]);
            }
        }
        add(buttonPanel);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
//        for (int i=0; i< gridArray.length; i++){
//            for (int j=0; j<gridArray[i].length; j++){
//                gridArray[i][j] = null;
//                g.fillRect(i*(this.getWidth()/size), j*(this.getHeight()/size), (this.getWidth()/size)-2,(this.getHeight()/size)-2);
//            }
//        }
    }
}
