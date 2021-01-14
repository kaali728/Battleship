package com.Battleship.SpielstandLaden;

import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class GameLoad {
    Border player = null;
    GameObj loadedGame;
    Gson gson = new Gson();
    private String fileName;
    private boolean client = false;

    public GameObj readFile(String path) {
        if(client){
            File file = new File("savedGames/"+fileName);
            try {
                FileReader fr = new FileReader(file);
                String filenamewith = file.getName();
                fileName = filenamewith.replace(".json", "");
                loadedGame = gson.fromJson(fr, GameObj.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            if (path == null) {
                path = "savedGames/";
                //String.valueOf(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory());

                //System.getProperty("user.home.Desktop");
                //System.getProperty("user.dir");
            }
            JFileChooser choosePath = new JFileChooser(path);
            JFrame frame = new JFrame();

            choosePath.setDialogType(JFileChooser.SAVE_DIALOG);
            FileNameExtensionFilter plainFilter = new FileNameExtensionFilter("json", "json");
            choosePath.removeChoosableFileFilter(choosePath.getAcceptAllFileFilter());
            choosePath.setFileFilter(plainFilter);

            int returnVal = choosePath.showOpenDialog(frame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = choosePath.getSelectedFile();
                try {

                    FileReader fr = new FileReader(file);
                    String filenamewith = file.getName();
                    fileName = filenamewith.replace(".json", "");

                    loadedGame = gson.fromJson(fr, GameObj.class);
                    //System.out.println(loadedGame.size);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return loadedGame;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isClient() {
        return client;
    }

    public void setClient(boolean client) {
        this.client = client;
    }
}