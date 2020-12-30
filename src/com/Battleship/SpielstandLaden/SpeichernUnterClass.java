package com.Battleship.SpielstandLaden;

import com.Battleship.Model.Board;
import com.Battleship.Model.Field;
import com.Battleship.Model.Ship;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpeichernUnterClass  extends JFrame {

    //kommt variable die von der spiel brett kommen
    private Board player;
    private Board enemy;
    private Field[][] button;
    private  ArrayList<Ship> fleet;

    public SpeichernUnterClass(Board player, Board enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public SpeichernUnterClass(Field[][] button, ArrayList<Ship> fleet, Board enemyBoard) {
        this.button= button;
        this.fleet= fleet;
        this.enemy = enemy;
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
                //map.put("Fleet", this.fleet);
                //map.put("Enemy", this.enemy);
                //System.out.println("läuft");
                Writer writer = new FileWriter(path + ".json");

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                // convert map to JSON File
                gson.toJson(map, writer);

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
}