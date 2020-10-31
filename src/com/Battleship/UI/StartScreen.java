//package com.Battleship.UI;
//
//import com.Battleship.Constants.Constants;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class StartScreen extends JPanel implements ActionListener {
//    private Button singleplayer;
//    StartScreen(){
//        initVariable();
//        initLayout();
//    }
//    void initVariable(){
//        singleplayer = new Button("Single Player");
//    }
//    void initLayout() {
//        setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
//        //setBackground(Color.black);
//        add(singleplayer);
//        singleplayer.addActionListener(this);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        System.out.println(e.getActionCommand());
////        System.out.println(new GamePanel().getCurrentCard());
////        if (new GamePanel().getCurrentCard() <4){
////            int c = new GamePanel().getCurrentCard();
////            new GamePanel().setCurrentCard(c+1);
////        }
//    }
//}
