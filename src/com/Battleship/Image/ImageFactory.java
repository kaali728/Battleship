package com.Battleship.Image;

import com.Battleship.Constants.Constants;

import javax.swing.*;

/**
 * The type Image factory.
 */
public class ImageFactory {
    /**
     * Create image image icon.
     *
     * @param image the image
     * @return the image icon
     */
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
