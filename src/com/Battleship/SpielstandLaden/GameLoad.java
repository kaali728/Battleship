package com.Battleship.SpielstandLaden;

import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileReader;

/**
 * The type Game load.
 */
public class GameLoad {
    /**
     * The Player.
     */
    Border player = null;
    /**
     * The Loaded game.
     */
    GameObj loadedGame;
    /**
     * The Gson.
     */
    Gson gson = new Gson();
    private String fileName;
    private boolean client = false;

    /**
     * Read file and rebuild the game in Singleplayer.
     * In Multiplayer the player who saved the game is also the only one who can reload it
     *
     * @param path the path
     * @return the game obj
     */
    public GameObj readFile(String path) {
        if(client){
            path =System.getProperty("user.dir");
            File file = new File(path+"/"+ fileName);
            try {
                FileReader fr = new FileReader(file);
                String filenamewith = file.getName();
                //umunterscheidung zwischen client und server datei(wenn auf der gleiche computer gespielt wird, wird dann überschrieben) deswegen "-c"
                if(filenamewith.contains("-s.json")){
                    fileName = filenamewith.replace("-s.json", "");
                }else{
                    fileName = filenamewith.replace("-c.json", "");
                }
                loadedGame = gson.fromJson(fr, GameObj.class);
                return loadedGame;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            if (path == null) {
                path = System.getProperty("user.dir");
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
                    //umunterscheidung zwischen client und server datei(wenn auf der gleiche computer gespielt wird, wird dann überschrieben) deswegen "-s"
                    if(filenamewith.contains("-s.json")){
                        fileName = filenamewith.replace("-s.json", "");
                    }else{
                        fileName = filenamewith.replace("-c.json", "");
                    }
                    System.out.println(fileName);

                    loadedGame = gson.fromJson(fr, GameObj.class);
                    //System.out.println(loadedGame.size);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return loadedGame;
    }

    /**
     * Set Filename
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Get File name
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Check if it is a Client
     * @return
     */
    public boolean isClient() {
        return client;
    }

    /**
     * set Client
     * @param client
     */
    public void setClient(boolean client) {
        this.client = client;
    }
}