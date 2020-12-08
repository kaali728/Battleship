package com.Battleship.Image;

import com.Battleship.Constants.Constants;

import javax.swing.*;

public class ImageFactory {
    public static ImageIcon createImage(Image image) {
        ImageIcon imageIcon;
        switch (image) {
            case ICON:
                imageIcon = new ImageIcon(Constants.ICON);
                break;
            case BACKGROUND:
                imageIcon = new ImageIcon(Constants.BACKGROUND);
                break;
            case SHIP:
                imageIcon = new ImageIcon(Constants.SHIP);
                break;
            default:
                return null;
        }
        return imageIcon;
    }
}
