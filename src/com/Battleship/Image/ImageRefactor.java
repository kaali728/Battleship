package com.Battleship.Image;

import com.Battleship.Constants.Constants;

import javax.swing.*;

public class ImageRefactor {
    public static ImageIcon createImage(Image image){
        ImageIcon imageIcon = null;
        switch (image){
            case ICON:
                imageIcon = new ImageIcon(Constants.ICON);
                break;
            default:
                return null;
        }
        return imageIcon;
    }
}
