package com.Battleship.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Board extends JPanel {
    int size=0;

    public Field button[][];

    JPanel buttonPanel;

    int rows;
    int columns;

    private ArrayList<Ship> fleet;

    private int carrierCount;
    private int battleshipCount;
    private int submarineCount;
    private int destoryerCount;

    private boolean horizontal = true;

    private String gameState;

    private JLabel carrierText;
    private JLabel battleshipText;
    private JLabel submarineText;
    private JLabel destroyerText;


    public Board(int size, ArrayList<Ship> fleet, String GameState){
        this.size=size;
        this.fleet = fleet;
        this.gameState = GameState;
        this.button = new Field[size][size];
        countShip();
        initlayout();
    }
    public void initlayout(){
        setSize(new Dimension(650,650));
        setLayout(new GridBagLayout());
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(size, size));

        for (rows = 0; rows < size; rows++) {
            for (columns = 0; columns < size; columns++) {
                button[rows][columns] = new Field(rows,columns, gameState);
                button[rows][columns].setBackground(Color.GRAY); //default Gray color is easily interchangable here
                button[rows][columns].setPreferredSize(new Dimension(650/size,650/size));
                button[rows][columns].setActionCommand(button[rows][columns].getRow() + ","+ button[rows][columns].getColumn());
                //wenn wir noch carrier haben (counter != 0) dann soll der carrier gesetzt werden
                button[rows][columns].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(gameState == "setzen"){
                            setShip(e);
                            carrierText = new JLabel("Carrier" + carrierCount);
                            battleshipText = new JLabel("Battleship" + battleshipCount);
                            submarineText = new JLabel("Submarine" + submarineCount);
                            destroyerText = new JLabel("Destoryer" + destoryerCount);
                        }
                        if(gameState == "battle"){
                            boolean success = shoot(e);
                        }
                    }
                });
                buttonPanel.add(button[rows][columns]);
            }
        }


        Box vBox = Box.createVerticalBox();
        vBox.setBackground(Color.white);
        {
            carrierText = new JLabel("Carrier" + carrierCount);
            battleshipText = new JLabel("Battleship" + battleshipCount);
            submarineText = new JLabel("Submarine" + submarineCount);
            destroyerText = new JLabel("Destoryer" + destoryerCount);

            vBox.add(carrierText);
            vBox.add(battleshipText);
            vBox.add(submarineText);
            vBox.add(destroyerText);
        }
        add(buttonPanel);
        if(gameState.equals("setzen")){
            add(vBox);
        }

    }

    public boolean shoot(ActionEvent e){
        return false;
    }

    public Ship setShip(ActionEvent e){
        String[] coordinate = e.getActionCommand().split(",");
        int row = Integer.parseInt(coordinate[0]);
        int column = Integer.parseInt(coordinate[1]);
        if(carrierCount != 0){
            for (Ship s: fleet) {
                if(s.getShipModel() == "carrier"){
                    boolean postionCheck = checkBoardPostion(row,column,s.getShiplength(),horizontal);
                    boolean areaCheck = checkShipArea(row,column,s.getShiplength(),horizontal);
                    if(postionCheck && areaCheck){
                        s.setRowColumn(row,column);
                        for (int i=0; i<s.getShiplength(); i++){
                            if(!horizontal){
                                button[row + i][column].setBackground(s.getShipColor());
                                makeMark(row + i,column);
                            }else{
                                button[row][column + i].setBackground(s.getShipColor());
                                makeMark(row,column + i);
                            }
                        }
                        carrierCount--;
                        return s;
                    }
                }
            }
        }else{
            if(battleshipCount != 0){
                for (Ship s: fleet) {
                    if(s.getShipModel() == "battleship"){
                        boolean postionCheck = checkBoardPostion(row,column,s.getShiplength(),horizontal);
                        boolean areaCheck = checkShipArea(row,column,s.getShiplength(),horizontal);
                        if(postionCheck && areaCheck) {
                            s.setRowColumn(row, column);
                            for (int i = 0; i < s.getShiplength(); i++) {
                                if(!horizontal){
                                    button[row + i][column].setBackground(s.getShipColor());
                                    makeMark(row + i,column);
                                }else{
                                    button[row][column + i].setBackground(s.getShipColor());
                                    makeMark(row,column + i);
                                }
                            }
                            battleshipCount--;

                            return s;
                        }
                    }
                }
            }else{
                if(submarineCount != 0){
                    for (Ship s: fleet) {
                        if(s.getShipModel() == "submarine"){
                            boolean postionCheck = checkBoardPostion(row,column,s.getShiplength(),horizontal);
                            boolean areaCheck = checkShipArea(row,column,s.getShiplength(),horizontal);
                            if(postionCheck && areaCheck) {
                                s.setRowColumn(row, column);
                                for (int i = 0; i < s.getShiplength(); i++) {
                                    if(!horizontal){
                                        button[row + i][column].setBackground(s.getShipColor());
                                        makeMark(row + i,column);
                                    }else{
                                        button[row][column + i].setBackground(s.getShipColor());
                                        makeMark(row,column + i);
                                    }
                                }
                                submarineCount--;
                                return s;
                            }
                        }
                    }
                }else{
                    if(destoryerCount != 0){
                        for (Ship s: fleet) {
                            if(s.getShipModel() == "destroyer"){
                                boolean postionCheck = checkBoardPostion(row,column,s.getShiplength(),horizontal);
                                boolean areaCheck = checkShipArea(row,column,s.getShiplength(),horizontal);
                                if(postionCheck && areaCheck) {
                                    s.setRowColumn(row, column);
                                    for (int i = 0; i < s.getShiplength(); i++) {
                                        if(!horizontal){
                                            button[row + i][column].setBackground(s.getShipColor());
                                            makeMark(row + i,column);
                                        }else{
                                            button[row][column + i].setBackground(s.getShipColor());
                                            makeMark(row,column + i);
                                        }
                                    }
                                    destoryerCount--;
                                    return s;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


    public boolean checkBoardPostion(int row, int column, int length, boolean horizontal){
        int diffRow = rows - (row+1);
        int diffCol = columns - (column+1);

        if(!horizontal){
            if(length - 1 > diffRow){
                return false;
            }
        }else{
            if(length - 1 > diffCol){
                return false;
            }
        }
        //schaut ob es richtige postion ist
        return true;
    }



    /**
     * Returns if Ship can be placed on this place.
     * <p>
     * @param  row
     * @param  column
     * @param  length
     * @param  horizontal
     * @return      true or false
     */
    public boolean checkShipArea(int row, int column, int length, boolean horizontal){
        boolean hasLeftColumn = false;
        boolean hasRightColumn = false;
        boolean hasUpRow = false;
        boolean hasDownRow = false;

        int leftColumn = 0;
        int rightColumn = 0;
        int upRow = 0;
        int downRow = 0;

        if(column - 1 >= 0){
            hasLeftColumn = true;
            leftColumn = column -1;
        }
        if(column + 1 <= columns - 1){
            hasRightColumn = true;
            rightColumn = column + 1;
        }
        if(row - 1 >= 0){
            hasUpRow = true;
            upRow = row -1;
        }
        if(row + 1 <= rows -1){
            hasDownRow = true;
            downRow = row + 1;
        }
        //schaut ob es der umgebung für shiff past
        if(horizontal){
            for (int i=0; i<length; i++){
                if(i == 0){
                    if(hasLeftColumn && button[row][leftColumn].isMark()){
                        return false;
                    }
                    if(hasLeftColumn && hasUpRow && button[upRow][leftColumn].isMark()){
                        return false;
                    }
                    if(hasLeftColumn && hasDownRow && button[downRow][leftColumn].isMark()){
                        return false;
                    }
                }
                try{
                    if(hasUpRow && button[upRow][column + i].isMark()){
                        return false;
                    }
                    if(hasDownRow && button[downRow][column + i].isMark()){
                        return false;
                    }
                }catch (Exception e){
                    return false;
                }

                if(i<length){
                    if((column + i)+1<=columns - 1 && button[row][(column + i) +1].isMark()){
                        return false;
                    }
                    if((column + i)+1<=columns - 1  && hasUpRow && button[upRow][(column + i) +1].isMark()){
                        return false;
                    }
                    if((column + i)+1<=columns - 1  && hasDownRow && button[downRow][(column + i) +1].isMark()){
                        return false;
                    }
                }

            }
        }else{

        }
        return true;
    }

    public void makeMark(int row,int column){
        button[row][column].setMark(true);
    }

    public void countShip(){
        for (Ship s: fleet) {
            if(s.getShipModel().equals("carrier")){
                carrierCount++;
            }
            if(s.getShipModel().equals("battleship")){
                battleshipCount++;
            }
            if(s.getShipModel().equals("submarine")){
                submarineCount++;
            }
            if(s.getShipModel().equals("destroyer")){
                destoryerCount++;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public ArrayList<Ship> getFleet() {
        return fleet;
    }
}
