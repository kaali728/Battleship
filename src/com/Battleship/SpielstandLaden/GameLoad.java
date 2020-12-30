package com.Battleship.SpielstandLaden;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class GameLoad {
    Border player = null;

    public void readFile(String path) {
        if (path == null) {
            path = System.getProperty("user.home");
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
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    //player.paintBorder();
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}