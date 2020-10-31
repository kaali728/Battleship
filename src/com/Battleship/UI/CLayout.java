//package com.Battleship.UI;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class CLayout extends GameMainFrame {
//
//    // Initialization the value of
//    // current card is 1 .
//    private int currentCard = 1;
//
//    // Declaration of objects
//    // of CardLayout class.
//    private CardLayout cl;
//
//    public CLayout()
//    {
//
//        // Creating Object of "Jpanel" class
//        JPanel cardPanel = new JPanel();
//
//        // Initialization of object "c1"
//        // of CardLayout class.
//        cl = new CardLayout();
//
//        // set the layout
//        cardPanel.setLayout(cl);
//
//        // Initialization of object
//        JPanel panelOne = new JPanel();
//
//        // Initialization of object
//        JPanel panelSecond = new JPanel();
//
//        // Initialization of object
//        JPanel panelThird = new JPanel();
//
//        // Initialization of object
//        JPanel panelFoured = new JPanel();
//
//        // Initialization of object
//        panelOne.setBackground(Color.BLUE);
//
//        // Initialization of object
//        // "jl2" of JLabel class.
//        panelSecond.setBackground(Color.YELLOW);
//
//        // Initialization of object
//        // "jl3" of JLabel class.
//        panelThird.setBackground(Color.CYAN);
//
//        // Initialization of object
//        // "jl4" of JLabel class.
//        panelFoured.setBackground(Color.RED);
//
//
//        // Adding the cardPanel on "jp1"
//        cardPanel.add(panelOne, "1");
//
//        // Adding the cardPanel on "jp2"
//        cardPanel.add(panelSecond, "2");
//
//        // Adding the cardPanel on "jp3"
//        cardPanel.add(panelThird, "3");
//
//        // Adding the cardPanel on "jp4"
//        cardPanel.add(panelFoured, "4");
//
//        // Creating Object of "JPanel" class
//        JPanel buttonPanel = new JPanel();
//
//        // Initialization of object
//        // "nextbtn" of JButton class.
//        JButton nextButton = new JButton("Next");
//
//        // Initialization of object
//        // "previousbtn" of JButton class.
//        JButton previousButton = new JButton("Previous");
//
//
//        // Adding JButton "nextbtn" on JFrame.
//        buttonPanel.add(nextButton);
//
//        // Adding JButton "previousbtn" on JFrame.
//        buttonPanel.add(previousButton);
//
//
//        // add nextbtn in ActionListener
//        nextButton.addActionListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//
//                // if condition apply
//                if (currentCard < 4)
//                {
//
//                    // increment the value of currentcard by 1
//                    currentCard += 1;
//
//                    // show the value of currentcard
//                    cl.show(cardPanel, "" + (currentCard));
//                }
//            }
//        });
//
//        // add previousbtn in ActionListener
//        previousButton.addActionListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                // if condition apply
//                if (currentCard > 1) {
//
//                    // decrement the value
//                    // of currentcard by 1
//                    currentCard -= 1;
//
//                    // show the value of currentcard
//                    cl.show(cardPanel, "" + (currentCard));
//                }
//            }
//        });
//
//        // used to get content pane
//        getContentPane().add(cardPanel, BorderLayout.NORTH);
//
//        // used to get content pane
//        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
//    }
//
//    // Main Method
//    public static void main(String[] args)
//    {
//
//        // Creating Object of CardLayoutDemo class.
//        CLayout cl = new CLayout();
//
//        // Function to set default operation of JFrame.
//        cl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // Function to set vivibility of JFrame.
//        cl.setVisible(true);
//    }
//}
//
