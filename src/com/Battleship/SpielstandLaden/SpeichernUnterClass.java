package com.Battleship.SpielstandLaden;

import com.Battleship.Model.Board;
import com.Battleship.Model.Field;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Speichern unter class.
 */
public class SpeichernUnterClass  extends JFrame {

    //kommt variable die von der spiel brett kommen

    private Board player;
    private Board enemy;

    private long defaultname = 0;

    private boolean multiplayer = false;
    private String fileName;
    private boolean client = false;


    /**
     * Instantiates a new Speichern unter class.
     *
     * @param playerBoard the player board
     * @param enemyBoard  the enemy board
     */
    public SpeichernUnterClass(Board playerBoard, Board enemyBoard) {
        this.player = playerBoard;
        this.enemy = enemyBoard;
    }

    /**
     * Save game in a file
     * The player can choose in Singleplayer the place where the file is saved
     * In Multiplayer both files are saved with a unique ID in a folder where the players ran the game
     *
     * @param path the path
     * @return the boolean
     */
    public boolean saveAs(String path) {
        JFileChooser choosePath;
        if (path == null ) {
            path =  System.getProperty("user.dir");
        }
        int result = 0;

            choosePath = new JFileChooser(path);
            choosePath.setDialogType(JFileChooser.SAVE_DIALOG);
            choosePath.removeChoosableFileFilter(choosePath.getAcceptAllFileFilter());
        if(!multiplayer){
            result = choosePath.showSaveDialog(this);
        }


        if (result == JFileChooser.APPROVE_OPTION || multiplayer) {
            try {
                if(client || multiplayer){
                    path = System.getProperty("user.dir");
                }else {
//                    System.out.println("ich bin hier");
                    path = choosePath.getSelectedFile().toString();
                }
                Map<String, Object> map = new HashMap<>();
                map.put("Player", this.player);
                Field enemybutton[][] = enemy.getButton();

                Writer writer;
                if(defaultname == 0){
                    writer= Files.newBufferedWriter(Paths.get(path+".json"));
                }else {
                    if(client){
                        //umunterscheidung zwischen client und server datei(wenn auf der gleiche computer gespielt wird, wird dann überschrieben) deswegen "-c"
                        writer = Files.newBufferedWriter(Paths.get(path+"/"+defaultname+"-c"+".json"));
                    }else{
                        //umunterscheidung zwischen client und server datei(wenn auf der gleiche computer gespielt wird, wird dann überschrieben) deswegen "-s"
                        writer = Files.newBufferedWriter(Paths.get(path+"/"+defaultname+"-s"+".json"));
                    }
                }

                GameObj data = new GameObj(this.player, this.enemy, multiplayer);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                // convert map to JSON File
                gson.toJson(data, writer);

                writer.close();


            }  catch (Exception e) {
                System.out.println("Fehler");
                e.printStackTrace();
            }
            choosePath.setVisible(false);
            return true;
        }
        choosePath.setVisible(false);
        return false;
    }

    /**
     * Set Default name
     * @param defaultname
     */
    public void setDefaultname(long defaultname) {
        this.defaultname = defaultname;
    }


    /**
     * Save game for multiplayer
     *
     * @return
     */
    public boolean isMultiplayer() {
        return multiplayer;
    }

    /**
     * Set Multiplayer
     * @param multiplayer
     */
    public void setMultiplayer(boolean multiplayer) {
        this.multiplayer = multiplayer;
    }

    /**
     * Set filename
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Check is it a Client or Server
     * @return
     */
    public boolean isClient() {
        return client;
    }

    /**
     * Set Client
     * @param client
     */
    public void setClient(boolean client) {
        this.client = client;
    }
}