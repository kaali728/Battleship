package com.Battleship.SpielstandLaden;

import com.Battleship.Model.Board;
import com.Battleship.Model.Ship;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SpeichernUnterClass  extends JFrame {

    //kommt variable die von der spiel brett kommen
    private Board player;
    private  Board enemy;
    public SpeichernUnterClass(Board player , Board enemy){
        this.player= player;
        this.enemy= enemy;
    }

    public boolean saveAs(String path) {
        JFileChooser choosePath;
        File file;
        FileWriter fw;
        BufferedWriter bw;
        if (path == null) {
            path = System.getProperty("user.home");
        }

        choosePath = new JFileChooser(path);
        choosePath.setDialogType(JFileChooser.SAVE_DIALOG);
        FileNameExtensionFilter plainFilter = new FileNameExtensionFilter("txt", "txt");
        choosePath.removeChoosableFileFilter(choosePath.getAcceptAllFileFilter());
        //choosePath.setFileFilter(plainFilter);

        int result = choosePath.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try{
                path = choosePath.getSelectedFile().toString();

                String playerFleet = "p:"+ player.getFleet() + "e:"+  enemy.getFleet();


                file =new File(path);

                fw = new FileWriter(file+ ".txt");

                bw = new BufferedWriter(fw);
                bw.write(playerFleet);

                bw.close();

            }catch(IOException e){
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