package com.Battleship.SpielstandLaden;

import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GameLoad {
    public void readFile(String path) {
        if (path == null) {
            path = System.getProperty("user.home");
        }
        JFileChooser choosePath = new JFileChooser(path);
        JFrame frame = new JFrame();

        choosePath.setDialogType(JFileChooser.SAVE_DIALOG);
        FileNameExtensionFilter plainFilter = new FileNameExtensionFilter("txt", "txt");
        choosePath.removeChoosableFileFilter(choosePath.getAcceptAllFileFilter());
        choosePath.setFileFilter(plainFilter);

        JTextArea txt = new JTextArea(10, 10);
        int returnVal = choosePath.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = choosePath.getSelectedFile();
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                txt.read(br, "Load ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}