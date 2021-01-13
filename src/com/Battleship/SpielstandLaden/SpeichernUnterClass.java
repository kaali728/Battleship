package com.Battleship.SpielstandLaden;

import com.Battleship.Model.Board;
import com.Battleship.Model.Field;
import com.Battleship.Model.MyTypeAdaptor;
import com.Battleship.Model.Ship;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpeichernUnterClass  extends JFrame {

    //kommt variable die von der spiel brett kommen

    private Board player;
    private Board enemy;

    private long defaultname = 0;

    private boolean multiplayer = false;

    public SpeichernUnterClass(Board playerBoard, Board enemyBoard) {
        this.player = playerBoard;
        this.enemy = enemyBoard;
    }

    public boolean saveAs(String path) {
        JFileChooser choosePath;
        if (path == null) {
            path = System.getProperty("user.home");
        }

        choosePath = new JFileChooser(path);
        choosePath.setDialogType(JFileChooser.SAVE_DIALOG);
        //FileNameExtensionFilter plainFilter = new FileNameExtensionFilter("txt", "txt");
        choosePath.removeChoosableFileFilter(choosePath.getAcceptAllFileFilter());
        //choosePath.setFileFilter(plainFilter);

        int result = choosePath.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                path = choosePath.getSelectedFile().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("Player", this.player);
                Field enemybutton[][] = enemy.getButton();
                //System.out.println(enemy.getFleet());
//                for (int i = 0; i <enemybutton.length ; i++) {
//                    for (int j = 0; j <enemybutton[i].length ; j++) {
//                        System.out.println(enemybutton[i][j]);
//                    }
//                }
                //map.put("Fleet", this.fleet);
                //map.put("Enemy", this.enemy);
                //System.out.println("läuft");
                Writer writer;
                if(defaultname == 0){
                    writer= Files.newBufferedWriter(Paths.get(path+".json"));
                }else {
                   writer = Files.newBufferedWriter(Paths.get(defaultname+".json"));
                }

                GameObj data = new GameObj(this.player, this.enemy, multiplayer);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                //Gson gson = new GsonBuilder().registerTypeAdapter(Board.class, new MyTypeAdaptor<Board>()).create();
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

    public long getDefaultname() {
        return defaultname;
    }

    public void setDefaultname(long defaultname) {
        this.defaultname = defaultname;
    }

    public boolean isMultiplayer() {
        return multiplayer;
    }

    public void setMultiplayer(boolean multiplayer) {
        this.multiplayer = multiplayer;
    }
}