package com.Battleship.AIvsAI;

import com.Battleship.Model.Board;
import com.Battleship.Model.Ship;
import com.Battleship.Player.AIPlayer;
import com.Battleship.UI.GamePanel;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class AIServerScreen extends JPanel {
    public static Writer out;        // Verpackung des Socket-Ausgabestroms.
    public static JTextArea chat;
    private static JTextField chatInput;
    private static JScrollPane chatScroll;
    private int fieldsize;
    public AIPlayer aiPlayer;
    boolean finish;
    int port;
    int carrierCount, battleshipCount, submarineCount, destroyerCount;
    GamePanel mainPanel;
    Board postionBoard;
    Board enemyBoard;

    public AIServerScreen(int port, int fieldsize, int carrierCount, int battleshipCount, int submarineCount, int destroyerCount, GamePanel mainPanel, AIPlayer aiPlayer) {
        this.aiPlayer= aiPlayer;
        this.port = port;
        this.fieldsize = fieldsize;
        this.carrierCount = carrierCount;
        this.battleshipCount = battleshipCount;
        this.submarineCount = submarineCount;
        this.destroyerCount = destroyerCount;
        this.mainPanel = mainPanel;
        this.fieldsize = fieldsize;
        new SwingWorker() {
            @Override
            protected Object doInBackground() {
                try {
                    ServerSocket serverSocket = new ServerSocket(port);
                    Socket socket = serverSocket.accept();

                    // Send message to client.
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.println("S: size " + fieldsize + " " + fieldsize);
                    printWriter.flush();
                    System.out.println("SERVER LOG");
                    System.out.println("S: size " + fieldsize + " " + fieldsize);

                    int ca = 5;
                    int bat = 4;
                    int subma = 3;
                    int des = 2;

                    ArrayList<Integer> anzahl = new ArrayList<>();

                    for (int i = 0; i < carrierCount; i++) {
                        anzahl.add(ca);
                    }
                    for (int i = 0; i < battleshipCount; i++) {
                        anzahl.add(bat);
                    }
                    for (int i = 0; i < submarineCount; i++) {
                        anzahl.add(subma);
                    }
                    for (int i = 0; i < destroyerCount; i++) {
                        anzahl.add(des);
                    }
                    //ships 5 4 4 3 3 3 2 2 2 2

                    String list = Arrays.toString(anzahl.toArray()).replace("[", "").replace("]", "");

                    // Get message from client.
                    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    out = new OutputStreamWriter(socket.getOutputStream());

                    // Netzwerknachrichten lesen und verarbeiten.
                    // Da die graphische Oberfläche von einem separaten Thread verwaltet
                    // wird, kann man hier unabhängig davon auf Nachrichten warten.
                    // Manipulationen an der Oberfläche sollten aber mittels invokeLater
                    // (oder invokeAndWait) ausgeführt werden.
                    while (true) {
                        String line = bufferedReader.readLine();

                        if (line == null) break;

                        // Protokoll
                        if (line.equals("C: next") && !mainPanel.getGameState().equals("battle")) {
                            printWriter.println("S: ships " + list.replace(",", ""));
                            printWriter.flush();
                            System.out.println("S: ships " + list.replace(",", ""));
                        }

                        // Server ist bereit für die Schlacht
                        if (line.equals("C: done")) {
                            finish = aiPlayer.aisetEnemyShip();
                            if (finish) {
                                try {
                                    // Gibt dem Gegner die Nachricht, dass er an der Reihe ist.
                                    printWriter.write(String.format("%s%n", "S: ready"));
                                    System.out.println("S: ready");
                                    printWriter.flush();
                                    mainPanel.setGameState("battle");
                                } catch (Exception e) {
                                    System.out.println("write to socket failed" + e);
                                }
                            }
                        }

                        if (line.equals("C: ready") ) {
                            SwingUtilities.invokeLater(() -> {
                                // Spielbrett
                                mainPanel.setGameState("battle");
                                enemyBoard.setVisible(true);
                                enemyBoard.setOut(out);
                                postionBoard.setOut(out);
                            });
                        }

                        // Schuss vom client verarbeiten.
                        if (line.contains("shot")) {
                            int row = Integer.parseInt(line.split(" ")[2]) - 1;
                            int col = Integer.parseInt(line.split(" ")[3]) - 1;
                            boolean ans = postionBoard.multiplayershoot(row, col);

                        }

                        if (line.contains("answer")) {
                            int ans = Integer.parseInt(line.split(" ")[2]);
                            enemyBoard.multiShoot(ans);

                            // Man darf erst bei Wasser wieder schießen.
                            if (ans == 0) {
                                printWriter.println("S: next");
                                printWriter.flush();
                            }
                        }

                        if (line.contains("next") && mainPanel.getGameState().equals("battle")) {
                            enemyBoard.multiEnableBtns(true);
                        }

                        SwingUtilities.invokeLater(
                                () -> {
                                    String tmp = chat.getText();

                                    // Pruefen ob es in der Nachricht um ein Spielereignis handelt
                                    // oder es einfach nur eine Chat Nachricht ist.
                                    if (line.contains("[Battleship]:")) {
                                        // Ping Pong und Nachricht an den Gegner,
                                        // dass er an der Reihe ist.
                                        chat.setText(tmp + "\n" + line);
                                    } else {
                                        // Chat Historie und aktuelle Nachricht vom Gegner.
                                        chat.setText(tmp + "\n" + line);
                                    }
                                }
                        );
                    }

                    // EOF ins Socket "schreiben" und das Programm explizit beenden
                    // (weil es sonst weiterlaufen würde, bis der Benutzer das Hauptfenster
                    // schließt).
                    socket.shutdownOutput();
                    System.out.println("Connection closed.");
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
        initLayout();
    }

    public void initLayout() {
        enemyBoard = new Board(fieldsize, "battle", out);
        // Board
        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            ArrayList<Ship> fleet = this.mainPanel.getSingleplayer().getFleet();
            postionBoard = new Board(fieldsize, fleet, this.mainPanel.getGameState());
            aiPlayer.setFieldsize(fieldsize);
            aiPlayer.setEnemyBoard(postionBoard);
            aiPlayer.setFleet( fleet);
            postionBoard.multiEnableBtns(false);
//            enemyBoard.multiEnableBtns(false);
            hbox.add(postionBoard);
            hbox.add(enemyBoard);
            hbox.add(Box.createHorizontalStrut(10));
        }



        chat = new JTextArea(10, 70);
        chat.setEditable(false);
        chat.setBackground(Color.lightGray);

        chatInput = new JTextField(70);
        chatInput.addActionListener(
                (e) -> {
                    try {
                        // Schreibt die Chat Nachricht an den Gegner.
                        out.write(String.format("%s%n", "[Enemy] " + chatInput.getText()));
                        out.flush();
                        // Zeigt die Chat Historie und die aktuelle Nachricht an.
                        String tmp = chat.getText();
                        chat.setText(tmp + "\n" + "[You]: " + chatInput.getText());
                        // Leert das Eingabefeld nach dem Senden.
                        chatInput.setText("");
                    } catch (IOException ex) {
                        System.out.println("write to socket failed");
                    }
                }
        );

        chatScroll = new JScrollPane(chat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setBackground(Color.white);

        add(hbox);

        add(chatScroll);
        add(chatInput);
        repaint();
    }
}