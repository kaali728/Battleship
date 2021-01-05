package com.Battleship.Model;

import com.Battleship.Player.AIPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
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

    private boolean broke=true;

    private JLabel carrierText;
    private JLabel battleshipText;
    private JLabel submarineText;
    private JLabel destroyerText;

    private boolean playerBoard = true;
    private Board playerBoardobj;

    public AIPlayer aiPlayer;

    //0 player 1 ai
    private boolean turn = true;

    private int allHealthPlayer = 0;
    private int allHealthEnemy = 0;

    private int counter=0;


    public Board(int size, ArrayList<Ship> fleet, String GameState){
        this.size=size;
        this.fleet = fleet;
        this.gameState = GameState;
        this.button = new Field[size][size];
        countShip();
        countHealth();
        initlayout();
    }

    public Board(int size, ArrayList<Ship> fleet, String GameState, boolean playerBoard){
        this.size=size;
        this.fleet = fleet;
        this.gameState = GameState;
        this.button = new Field[size][size];
        this.playerBoard = playerBoard;
        countShip();
        countHealth();
        initlayout();
    }
    public Board(int size, ArrayList<Ship> fleet, String GameState, boolean playerBoard, AIPlayer aiPlayer, Board playerBoardobj){
        this.size=size;
        this.fleet = fleet;
        this.gameState = GameState;
        this.button = new Field[size][size];
        this.playerBoard = playerBoard;
        this.aiPlayer = aiPlayer;
        this.playerBoardobj = playerBoardobj;
        countShip();
        countHealth();
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
                        }
                        if(gameState == "battle"){
                            try {
                                if(counter<1){
                                    aiPlayer.Health(allHealthPlayer);
                                    counter++;
                                }
                                allHealthPlayer = aiPlayer.health;
                                if(!isGameOver()) {
                                    boolean success = shoot(e);
                                }
                                if(allHealthPlayer == 1){
                                    for (int i = 0; i < size ; i++) {
                                        for (int j = 0; j <size ; j++) {
                                            button[i][j].setShot(true);
                                            button[i][j].setMark(true);
                                        }
                                    }
                                    JOptionPane.showMessageDialog(aiPlayer.getPlayerBoard(), "You Lose!", "End Game", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }catch (NullPointerException ex){
                            }
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
            destroyerText = new JLabel("Destroyer" + destoryerCount);

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

    public List convert(){
        List<List<Field>> list = Arrays.stream(button).map(Arrays::asList).collect(Collectors.toList());
        return list;
    }

    public void setMyShip() {
        for (Ship s: fleet) {
            //System.out.println(s);
            for (int l = 0; l<s.getShiplength(); l++){
                //System.out.println(l);
                //System.out.println(s.getRow()+ " "+ s.getColumn());
                if(s.getHorizontal()){
                    button[s.getRow()][s.getColumn() + l].setBackground(s.getShipColor());
                    button[s.getRow()][s.getColumn() + l].setMark(true);
                }else{
                    button[s.getRow() + l][s.getColumn()].setBackground(s.getShipColor());
                    button[s.getRow() + l][s.getColumn()].setMark(true);
                }
            }
        }
    }

    public void setMyShip(Field buttonLoad[][]) {
        if(playerBoard){
            for (int i = 0; i <buttonLoad.length ; i++) {
                for (int j = 0; j <buttonLoad[i].length ; j++) {
                    button[i][j].setShot(buttonLoad[i][j].isShot());
                    button[i][j].setMark(buttonLoad[i][j].isMark());
                    if(!buttonLoad[i][j].isMark() && buttonLoad[i][j].isShot()){
                        button[i][j].setText("<html><b color=white>X</b></html>");
                        button[i][j].setBackground(new Color(0x0000B2));
                    }
                }
            }
            for (Ship s: fleet) {
                //System.out.println(s);
                for (int l = 0; l<s.getShiplength(); l++){
                    //System.out.println(l);
                    //System.out.println(s.getRow()+ " "+ s.getColumn());
                    //System.out.println(s.getHorizontal());
                    if(s.getHorizontal()){
                        if(buttonLoad[s.getRow()][s.getColumn() + l].isMark() && buttonLoad[s.getRow()][s.getColumn() + l].isShot()){
                            button[s.getRow()][s.getColumn() + l].setText("<html><b color=white>💣</b></html>");
                            button[s.getRow()][s.getColumn() + l].setBackground(new Color(0xE52100));
                            buttonLoad[s.getRow()][s.getColumn() + l].setMark(true);
                        }
                        if(buttonLoad[s.getRow()][s.getColumn() + l].isMark() && !buttonLoad[s.getRow()][s.getColumn() + l].isShot()){
                            button[s.getRow()][s.getColumn() + l].setBackground(s.getShipColor());
                            button[s.getRow()][s.getColumn() + l].setMark(true);
                        }
                    }else{
                        if( button[s.getRow() + l][s.getColumn()].isMark() &&  button[s.getRow() + l][s.getColumn()].isShot()){
                            button[s.getRow() + l][s.getColumn()].setText("<html><b color=white>💣</b></html>");
                            button[s.getRow() + l][s.getColumn()].setBackground(new Color(0xE52100));
                            button[s.getRow() + l][s.getColumn()].setMark(true);
                        }
                        if( button[s.getRow() + l][s.getColumn()].isMark() && ! button[s.getRow() + l][s.getColumn()].isShot()){
                            button[s.getRow() + l][s.getColumn()].setBackground(s.getShipColor());
                            button[s.getRow() + l][s.getColumn()].setMark(true);
                        }
                    }
                }
            }
        }else{
            for (int i = 0; i <buttonLoad.length ; i++) {
                for (int j = 0; j <buttonLoad[i].length ; j++) {
                    button[i][j].setShot(buttonLoad[i][j].isShot());
                    button[i][j].setMark(buttonLoad[i][j].isMark());
                    if(!buttonLoad[i][j].isMark() && buttonLoad[i][j].isShot()){
                        button[i][j].setText("<html><b color=white>X</b></html>");
                        button[i][j].setBackground(new Color(0x0000B2));
                    }
                }
            }
            for (Ship s: fleet) {
                //System.out.println(s);
                for (int l = 0; l<s.getShiplength(); l++){
                    //System.out.println(l);
                    //System.out.println(s.getRow()+ " "+ s.getColumn());
                    if(s.getHorizontal()){
                        if(buttonLoad[s.getRow()][s.getColumn() + l].isMark() && buttonLoad[s.getRow()][s.getColumn() + l].isShot()){
                            button[s.getRow()][s.getColumn() + l].setText("<html><b color=white>💣</b></html>");
                            button[s.getRow()][s.getColumn() + l].setBackground(new Color(0xE52100));
                            buttonLoad[s.getRow()][s.getColumn() + l].setMark(true);
                        }
                        if(buttonLoad[s.getRow()][s.getColumn() + l].isMark() && !buttonLoad[s.getRow()][s.getColumn() + l].isShot()){
                            button[s.getRow()][s.getColumn() + l].setMark(true);
                        }
                    }else{
                        if( button[s.getRow() + l][s.getColumn()].isMark() &&  button[s.getRow() + l][s.getColumn()].isShot()){
                            button[s.getRow() + l][s.getColumn()].setText("<html><b color=white>💣</b></html>");
                            button[s.getRow() + l][s.getColumn()].setBackground(new Color(0xE52100));
                            button[s.getRow() + l][s.getColumn()].setMark(true);
                        }
                        if( button[s.getRow() + l][s.getColumn()].isMark() && ! button[s.getRow() + l][s.getColumn()].isShot()){
                            button[s.getRow() + l][s.getColumn()].setMark(true);
                        }
                    }
                }
            }
        }
    }


    public boolean shoot(ActionEvent e){
        String[] coordinate = e.getActionCommand().split(",");
        int row = Integer.parseInt(coordinate[0]);
        int column = Integer.parseInt(coordinate[1]);
        //schauen ob der person gewonnen hat oder nicht
        //health von sag ob ein ship noch leben hat wenn alle 0 sind dann gameover
        //cordianten von enemy schiffe
        //System.out.println(allHealth);
        if(!playerBoard && broke && !isGameOver() && !button[row][column].isShot()) {
            if (button[row][column].isMark()) {
                //System.out.println(fleet);
                button[row][column].setText("<html><b color=white>💣</b></html>");
                button[row][column].setBackground(new Color(0xE52100));
                //aiPlayer.Enemyshoot(playerBoardobj);
                button[row][column].setShot(true);
                allHealthEnemy--;
                if (isGameOver()) {
                    JOptionPane.showMessageDialog(this, "You Win!", "End Game", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                return true;
            } else {
                button[row][column].setText("<html><b color=white>X</b></html>");
                button[row][column].setBackground(new Color(0x0000B2));
                button[row][column].setShot(true);
                aiPlayer.Enemyshoot(playerBoardobj);
                Field enemybutton[][] = aiPlayer.getEnemyBoard().getButton();
                new SwingWorker(){
                    @Override
                    protected Object doInBackground() throws Exception {
                        while (aiPlayer.isHit) {
                            broke=false;
                            for (int i = 0; i <enemybutton.length ; i++) {
                                for (int j = 0; j <enemybutton[i].length ; j++) {
                                    if(!enemybutton[i][j].isShot()){
                                        enemybutton[i][j].setBackground(Color.black);
                                    }
                                }
                            }
                            Thread.sleep(600);
                            aiPlayer.Enemyshoot(playerBoardobj);
                        }
                        for (int i = 0; i <enemybutton.length ; i++) {
                            for (int j = 0; j <enemybutton[i].length ; j++) {
                                if(!enemybutton[i][j].isShot()){
                                    enemybutton[i][j].setBackground(Color.GRAY); //default Gray color is easily interchangable here
                                }
                            }
                        }
                        broke = true;
                        return null;
                    }
                }.execute();
            }
        }
        return false;
    }

    public boolean shoot(int row, int column){
        //schauen ob der person gewonnen hat oder nicht
        //health von sag ob ein ship noch leben hat wenn alle 0 sind dann gameover
        //cordianten von enemy schiffe
        //!playerboard shoot doppelt and that can be help and extra feature
            if(playerBoard && !isGameOver() && !button[row][column].isShot()) {
                ArrayList<Field> posFields = getPosShip();
                //System.out.println(posFields);
                for (Field f : posFields) {
                    if (f.getRow() == row && f.getColumn() == column && !f.isShot() && !f.isMark()) {
                        f.setShot(true);
                    }
                    if (f.getRow() == row && f.getColumn() == column && f.isMark()) {
                        button[f.getRow()][f.getColumn()].setText("<html><b color=white>🔥</b></html>");
                        button[f.getRow()][f.getColumn()].setBackground(new Color(0xE52100));
                        button[f.getRow()][f.getColumn()].setShot(true);
                        f.setShot(true);
                        this.allHealthPlayer--;
                        if (isGameOver()) {
                            for (int i = 0; i < size; i++) {
                                for (int j = 0; j < size; j++) {
                                    button[i][j].setShot(true);
                                    button[i][j].setMark(true);
                                }
                            }
                            return false;

                        }
                        return true;
                    }
                }
                button[row][column].setText("<html><b color=white>X</b></html>");
                button[row][column].setBackground(new Color(0x0000B2));
                button[row][column].setShot(true);
            }
        return false;
    }



    public void countHealth(){
        for (Ship s: fleet) {
            allHealthPlayer += s.getHealth();
        }
        allHealthEnemy = allHealthPlayer;
    }

//    public int getAllHealth() {
//        return allHealth;
//    }

    public boolean isGameOver(){
        if(allHealthPlayer == 0 || allHealthEnemy == 0){
            gameState="Game Over";
            return true;
        }
        return false;
    }


    public ArrayList<Field> getPosShip(){
        ArrayList<Field> fieldsShip = new ArrayList<>();
        for (Ship s: fleet) {
            for (Field f: s.getShipBoard()) {
                fieldsShip.add(f);
            }
        }
        return fieldsShip;
    }



    public Ship setShip(ActionEvent e){
        String[] coordinate = e.getActionCommand().split(",");
        int row = Integer.parseInt(coordinate[0]);
        int column = Integer.parseInt(coordinate[1]);
        if(carrierCount != 0){
            for (Ship s: fleet) {
                if(s.getShipModel() == "carrier" && s.getRow() == -1 && s.getColumn() == -1){
                    boolean postionCheck = checkBoardPostion(row,column,s.getShiplength(),horizontal);
                    boolean areaCheck = checkShipArea(row,column,s.getShiplength(),horizontal);
                    if(postionCheck && areaCheck){
                        s.setRowColumn(row,column);
                        if(horizontal){
                            s.setHorizontal(true);
                        }else{
                            s.setHorizontal(false);
                        }
                        ArrayList<Field> pos = new ArrayList<>();
                        for (int i=0; i<s.getShiplength(); i++){
                            if(!horizontal){
                                button[row + i][column].setBackground(s.getShipColor());
                                makeMark(row + i,column);
                                pos.add(button[row + i][column]);
                            }else{
                                button[row][column + i].setBackground(s.getShipColor());
                                makeMark(row,column + i);
                                pos.add(button[row][column + i]);
                            }
                        }
                        carrierCount--;
                        carrierText = new JLabel("Carrier" + carrierCount);
                        s.setShipBoard(pos);
                        return s;
                    }
                }
            }
        }else{
            if(battleshipCount != 0){
                for (Ship s: fleet) {
                    if(s.getShipModel() == "battleship"  && s.getRow() == -1 && s.getColumn() == -1){
                        boolean postionCheck = checkBoardPostion(row,column,s.getShiplength(),horizontal);
                        boolean areaCheck = checkShipArea(row,column,s.getShiplength(),horizontal);
                        if(postionCheck && areaCheck) {
                            s.setRowColumn(row,column);
                            if(horizontal){
                                s.setHorizontal(true);
                            }else{
                                s.setHorizontal(false);
                            }
                            ArrayList<Field> pos = new ArrayList<>();

                            for (int i = 0; i < s.getShiplength(); i++) {
                                if(!horizontal){
                                    button[row + i][column].setBackground(s.getShipColor());
                                    makeMark(row + i,column);
                                    pos.add(button[row + i][column]);
                                }else{
                                    button[row][column + i].setBackground(s.getShipColor());
                                    makeMark(row,column + i);
                                    pos.add(button[row][column + i]);
                                }
                            }
                            battleshipCount--;
                            battleshipText = new JLabel("Battleship" + battleshipCount);
                            s.setShipBoard(pos);
                            return s;
                        }
                    }
                }
            }else{
                if(submarineCount != 0){
                    for (Ship s: fleet) {
                        if(s.getShipModel() == "submarine"  && s.getRow() == -1 && s.getColumn() == -1){
                            boolean postionCheck = checkBoardPostion(row,column,s.getShiplength(),horizontal);
                            boolean areaCheck = checkShipArea(row,column,s.getShiplength(),horizontal);
                            if(postionCheck && areaCheck) {
                                s.setRowColumn(row,column);
                                if(horizontal){
                                    s.setHorizontal(true);
                                }else{
                                    s.setHorizontal(false);
                                }
                                ArrayList<Field> pos = new ArrayList<>();

                                for (int i = 0; i < s.getShiplength(); i++) {
                                    if(!horizontal){
                                        button[row + i][column].setBackground(s.getShipColor());
                                        makeMark(row + i,column);
                                        pos.add(button[row + i][column]);
                                    }else{
                                        button[row][column + i].setBackground(s.getShipColor());
                                        makeMark(row,column + i);
                                        pos.add(button[row][column + i]);
                                    }
                                }
                                submarineCount--;
                                submarineText = new JLabel("Submarine" + submarineCount);
                                s.setShipBoard(pos);
                                return s;
                            }
                        }
                    }
                }else{
                    if(destoryerCount != 0){
                        for (Ship s: fleet) {
                            if(s.getShipModel() == "destroyer" && s.getRow() == -1 && s.getColumn() == -1){
                                boolean postionCheck = checkBoardPostion(row,column,s.getShiplength(),horizontal);
                                boolean areaCheck = checkShipArea(row,column,s.getShiplength(),horizontal);
                                if(postionCheck && areaCheck) {
                                    s.setRowColumn(row,column);
                                    if(horizontal){
                                        s.setHorizontal(true);
                                    }else{
                                        s.setHorizontal(false);
                                    }
                                    ArrayList<Field> pos = new ArrayList<>();
                                    for (int i = 0; i < s.getShiplength(); i++) {
                                        if(!horizontal){
                                            button[row + i][column].setBackground(s.getShipColor());
                                            makeMark(row + i,column);
                                            pos.add(button[row + i][column]);
                                        }else{
                                            button[row][column + i].setBackground(s.getShipColor());
                                            makeMark(row,column + i);
                                            pos.add(button[row][column + i]);
                                        }
                                    }
                                    destoryerCount--;
                                    destroyerText = new JLabel("Destroyer" + destoryerCount);
                                    s.setShipBoard(pos);
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

    public Ship setShip(int row,int column){
        Random random= new Random();
        boolean hori = random.nextBoolean();
        if(size <= 7){
            hori = true;
        }
        if(carrierCount != 0){
            for (Ship s: fleet) {
                if(s.getShipModel() == "carrier"  && s.getRow() == -1 && s.getColumn() == -1){
                    boolean postionCheck = checkBoardPostion(row,column,s.getShiplength(),hori);
                    boolean areaCheck = checkShipArea(row,column,s.getShiplength(),hori);
                    if(postionCheck && areaCheck){
                        s.setRowColumn(row,column);
                        if(hori){
                            s.setHorizontal(true);
                        }else{
                            s.setHorizontal(false);
                        }
                        ArrayList<Field> pos = new ArrayList<>();
                        for (int i=0; i<s.getShiplength(); i++){
                            if(!hori){
                                //button[row + i][column].setBackground(s.getShipColor());
                                pos.add(button[row + i][column]);
                                makeMark(row + i,column);
                            }else{
                                //button[row][column + i].setBackground(s.getShipColor());
                                pos.add(button[row][column + i]);
                                makeMark(row,column + i);
                            }
                        }
                        carrierCount--;
                        carrierText = new JLabel("Carrier" + carrierCount);
                        s.setShipBoard(pos);
                        return s;
                    }
                }
            }
        }else{
            if(battleshipCount != 0){
                for (Ship s: fleet) {
                    if(s.getShipModel() == "battleship"  && s.getRow() == -1 && s.getColumn() == -1){
                        boolean postionCheck = checkBoardPostion(row,column,s.getShiplength(),hori);
                        boolean areaCheck = checkShipArea(row,column,s.getShiplength(),hori);
                        if(postionCheck && areaCheck) {
                            s.setRowColumn(row,column);
                            if(hori){
                                s.setHorizontal(true);
                            }else{
                                s.setHorizontal(false);
                            }
                            ArrayList<Field> pos = new ArrayList<>();

                            for (int i = 0; i < s.getShiplength(); i++) {
                                if(!hori){
                                    //button[row + i][column].setBackground(s.getShipColor());
                                    pos.add(button[row + i][column]);
                                    makeMark(row + i,column);
                                }else{
                                    //button[row][column + i].setBackground(s.getShipColor());
                                    pos.add(button[row][column + i]);
                                    makeMark(row,column + i);
                                }
                            }
                            battleshipCount--;
                            battleshipText = new JLabel("Battleship" + battleshipCount);
                            s.setShipBoard(pos);
                            return s;
                        }
                    }
                }
            }else{
                if(submarineCount != 0){
                    for (Ship s: fleet) {
                        if(s.getShipModel() == "submarine"  && s.getRow() == -1 && s.getColumn() == -1){
                            boolean postionCheck = checkBoardPostion(row,column,s.getShiplength(),hori);
                            boolean areaCheck = checkShipArea(row,column,s.getShiplength(),hori);
                            if(postionCheck && areaCheck) {
                                s.setRowColumn(row,column);
                                if(hori){
                                    s.setHorizontal(true);
                                }else{
                                    s.setHorizontal(false);
                                }
                                ArrayList<Field> pos = new ArrayList<>();

                                for (int i = 0; i < s.getShiplength(); i++) {
                                    if(!hori){
                                        //button[row + i][column].setBackground(s.getShipColor());
                                        pos.add(button[row + i][column]);
                                        makeMark(row + i,column);
                                    }else{
                                        //button[row][column + i].setBackground(s.getShipColor());
                                        pos.add(button[row][column + i]);
                                        makeMark(row,column + i);
                                    }
                                }
                                submarineCount--;
                                submarineText = new JLabel("Submarine" + submarineCount);
                                s.setShipBoard(pos);

                                return s;
                            }
                        }
                    }
                }else{
                    if(destoryerCount != 0){
                        for (Ship s: fleet) {
                            if(s.getShipModel() == "destroyer" && s.getRow() == -1 && s.getColumn() == -1){
                                boolean postionCheck = checkBoardPostion(row,column,s.getShiplength(),hori);
                                boolean areaCheck = checkShipArea(row,column,s.getShiplength(),hori);
                                if(postionCheck && areaCheck) {
                                    s.setRowColumn(row,column);
                                    if(hori){
                                        s.setHorizontal(true);
                                    }else{
                                        s.setHorizontal(false);
                                    }
                                    ArrayList<Field> pos = new ArrayList<>();

                                    for (int i = 0; i < s.getShiplength(); i++) {
                                        if(!hori){
                                            //button[row + i][column].setBackground(s.getShipColor());
                                            pos.add(button[row + i][column]);
                                            makeMark(row + i,column);
                                        }else{
                                            //button[row][column + i].setBackground(s.getShipColor());
                                            pos.add(button[row][column + i]);
                                            makeMark(row,column + i);
                                        }
                                    }
                                    destoryerCount--;
                                    destroyerText = new JLabel("Destoryer" + destoryerCount);
                                    s.setShipBoard(pos);
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

                if(i == length - 1){
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
            for (int i=0; i<length; i++){
                if(i == 0){
                    if(hasUpRow && button[upRow][column].isMark()){
                        return false;
                    }
                    if(hasLeftColumn && hasUpRow && button[upRow][leftColumn].isMark()){
                        return false;
                    }
                    if(hasLeftColumn && hasDownRow && button[downRow][leftColumn].isMark()){
                        return false;
                    }
                    if(hasRightColumn && hasUpRow && button[upRow][rightColumn].isMark()){
                        return false;
                    }
                    if(hasRightColumn && hasDownRow && button[downRow][rightColumn].isMark()){
                        return false;
                    }
                }
                try{
                    if(hasLeftColumn && button[row +i][leftColumn].isMark()){
                        return false;
                    }
                    if(hasRightColumn && button[row+i][rightColumn].isMark()){
                        return false;
                    }
                }catch (Exception e){
                    return false;
                }

                if(i == length - 1){
                    if((row + i)+1<=rows - 1 && button[row+i+1][column].isMark()){
                        return false;
                    }
                    if((row + i)+1<=rows - 1  && hasLeftColumn && button[(row + i) + 1][leftColumn].isMark()){
                        return false;
                    }
                    if((row + i)+1<=rows - 1  && hasRightColumn && button[(row +i)+ 1][rightColumn].isMark()){
                        return false;
                    }
                }
            }

        }
        return true;
    }


    /**
     * mark all field who we have a ship on it
     * <p>
     * @param  row
     * @param  column
     */
    public void makeMark(int row,int column){
        button[row][column].setMark(true);
    }


    /**
     * count how many ship do we have in our arraylist
     * <p>
     */
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

    public void makealarm() {
        JOptionPane.showMessageDialog(this, "You should place all ships!", "Place Ship", JOptionPane.INFORMATION_MESSAGE);
    }

    public Field[][] getButton() {
        return this.button;
    }

    public Field getonebutton(int row, int columns){
        return button[row][columns];
    }

    public void setFleet(ArrayList<Ship> fleet) {
        this.fleet = fleet;
    }

    public int getAllHealthEnemy() {
        return allHealthEnemy;
    }

    public int getAllHealthPlayer() {
        return allHealthPlayer;
    }

    public void setAllHealthEnemy(int allHealthEnemy) {
        this.allHealthEnemy = allHealthEnemy;
    }

    public void setAllHealthPlayer(int allHealthPlayer) {
        this.allHealthPlayer = allHealthPlayer;
    }

    @Override
    public String toString() {
        return "Board{" +
                "size=" + size +
                ", button=" + Arrays.toString(button) +
                ", buttonPanel=" + buttonPanel +
                ", rows=" + rows +
                ", columns=" + columns +
                ", fleet=" + fleet +
                ", carrierCount=" + carrierCount +
                ", battleshipCount=" + battleshipCount +
                ", submarineCount=" + submarineCount +
                ", destoryerCount=" + destoryerCount +
                '}';
    }
}
