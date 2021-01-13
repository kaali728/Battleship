package com.Battleship.UI;

import com.Battleship.Model.*;
import com.Battleship.SpielstandLaden.GameLoad;
import com.Battleship.SpielstandLaden.GameObj;
import com.Battleship.SpielstandLaden.SpeichernUnterClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class LoadMultiPlayerScreen extends JPanel {
    Integer portnum;
    GamePanel mainPanel;
    String loadedfileName;
    GameObj spielStand;
    private int fieldsize;
    Board postionBoard;
    Board enemyBoard;
    public static Writer out;
    public JButton saveButton;
    SpeichernUnterClass speicher;
    public static JTextArea chat;
    private static JTextField chatInput;
    private static JScrollPane chatScroll;
    JButton ready;

    public LoadMultiPlayerScreen(Integer port, String filename, GameObj loadGame, GamePanel mainPanel){
        this.portnum = port;
        this.mainPanel = mainPanel;
        this.loadedfileName = filename;
        this.spielStand = loadGame;
        initSpiel();
        new SwingWorker() {
            @Override
            protected Object doInBackground() {
                try {
                    ServerSocket serverSocket = new ServerSocket(port);
                    Socket socket = serverSocket.accept();

                    // Send message to client.
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.println("S: load " + filename);
                    printWriter.flush();
                    System.out.println("SERVER LOG");
                    System.out.println("S: load " + filename);

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


                        // Server ist bereit für die Schlacht
                        if (line.equals("C: done")) {
                            SwingUtilities.invokeLater(() -> {
                                ready.setEnabled(true);
                            });
                        }

                        if (line.equals("C: ready")) {
                            SwingUtilities.invokeLater(() -> {
                                // Spielbrett
                                mainPanel.setGameState("battle");
                                enemyBoard.setVisible(true);
                                enemyBoard.setOut(out);
                                postionBoard.setOut(out);
                                saveButton.setVisible(true);
                            });
                        }

                        // Schuss vom client verarbeiten.
                        if (line.contains("shot")) {
                            int row = Integer.parseInt(line.split(" ")[2]) - 1;
                            int col = Integer.parseInt(line.split(" ")[3]) - 1;
                            postionBoard.multiplayershoot(row, col);
                        }

                        if (line.contains("answer")) {
                            int ans = Integer.parseInt(line.split(" ")[2]);
                            enemyBoard.multiShoot(ans);

                            // Man darf erst bei Wasser wieder schießen.
                            if (ans == 0) {
                                enemyBoard.multiEnableBtns(false);
                                printWriter.println("S: next");
                                printWriter.flush();
                                SwingUtilities.invokeLater(()->{
                                    saveButton.setVisible(false);
                                });
                            }
                        }

                        if(line.contains("save")){
                            int ut = Integer.parseInt(line.split(" ")[2]);
                            SwingUtilities.invokeAndWait(() -> {
                                speicher = new SpeichernUnterClass(postionBoard, enemyBoard);
                                speicher.setDefaultname(ut);
                                speicher.setMultiplayer(true);
                                speicher.saveAs(null);
                            });

                            printWriter.println("S: done");
                            printWriter.flush();
                        }

                        if (line.contains("next") && mainPanel.getGameState().equals("battle")) {
                            enemyBoard.multiEnableBtns(true);
                            saveButton.setVisible(true);
                        }

                        SwingUtilities.invokeLater(
                                () -> {
                                    String tmp = chat.getText();

                                    // Pruefen ob es in der Nachricht um ein Spielereignis handelt
                                    // oder es einfach nur eine Chat Nachricht ist.
                                    if (line.contains("[Battleship]:")) {
                                        // Ping Pong und Nachricht an den Gegner,
                                        // dass er an der Reihe ist.
                                        //button.setEnabled(true);
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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
        initLayout();
    }
    public void initLayout() {
        saveButton = new JButton("Save Game");
        saveButton.setVisible(false);
        ready = new JButton("Ready");
        //enemyBoard.setVisible(false);

        ready.addActionListener(
                // Wenn der Knopf gedrückt wird,
                // erfolgt eine Kontrollausgabe auf System.out.
                // Anschließend wird der Knopf deaktiviert
                // und eine beliebige Nachricht an die andere "Seite" geschickt,
                // damit diese ihren Knopf aktivieren kann.
                (e) -> {
                    ArrayList<Ship> placedShips = this.mainPanel.getSingleplayer().getFleet();
                    boolean allSet = true;
                    for (Ship s : placedShips) {
                        if (s.getColumn() == -1 || s.getRow() == -1) {
                            allSet = false;
                            break;
                        }
                    }
                    if (allSet) {
                        ready.setEnabled(false);
                        try {
                            // Gibt dem Gegner die Nachricht, dass er an der Reihe ist.
                            out.write(String.format("%s%n", "S: ready"));
                            System.out.println("S: ready");
                            out.flush();
                            this.mainPanel.setGameState("battle");
                            ready.setVisible(false);
                        } catch (IOException ex) {
                            System.out.println("write to socket failed");
                        }
                    }
                }
        );


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Server Save Game");
                Date now = new Date();
                long ut3 = now.getTime() / 1000L;
                speicher = new SpeichernUnterClass(postionBoard, enemyBoard);
                speicher.setDefaultname(ut3);
                speicher.setMultiplayer(true);
                speicher.saveAs(null);
                try{
                    out.write(String.format("%s%n", "S: save "+ut3));
                    System.out.println("S: save "+ ut3);
                    out.flush();
                }catch (Exception es){
                    System.out.println("write to socket failed by S:save" + es);
                }
            }
        });


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

        add(ready);
        add(saveButton);
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(100));
        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(vbox);
        // Board
        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            hbox.add(postionBoard);
            hbox.add(Box.createHorizontalStrut(10));
            hbox.add(enemyBoard);
            hbox.add(Box.createHorizontalStrut(10));
        }
        add(hbox);


        add(chatScroll);
        add(chatInput);
        repaint();
    }
    void initSpiel() {
        if (spielStand != null) {
            this.fieldsize = spielStand.size;
            Field bt[][] = convertSaveField(spielStand.playerButton);
            Field enbt[][] = convertSaveField(spielStand.enemyButton);
            ArrayList<Ship> playerFleet = convertSaveShip(spielStand.playerFleet, bt);
            this.mainPanel.getSingleplayer().setFieldsize(spielStand.size);
            this.mainPanel.getSingleplayer().setFleet(playerFleet);
            this.mainPanel.setLoadedPlayerHealth(spielStand.PlayerHealth);
            this.mainPanel.setLoadedEnemyHealth(spielStand.EnemyHealth);
            postionBoard = new Board(fieldsize, playerFleet, "battle");
            this.postionBoard.setMyShip(bt);
            enemyBoard = new Board(fieldsize, "battle", out);
            this.enemyBoard.setMyShipMultiPlayerLoad(enbt);
        }
    }

    private ArrayList<Ship> convertSaveShip(ArrayList<SaveShip> saveShips, Field b[][]){
        ArrayList<Ship> retList = new ArrayList<>();
        for (SaveShip saveS:saveShips) {
            Ship ship = new Ship(saveS.getShipModel());
            ship.setRowColumn(saveS.getRow(), saveS.getColumn());
            //ship.setHorizontal(saveS.isHorizontal());
            if(saveS.getShipBoard().get(0).getColumn() !=  saveS.getShipBoard().get(saveS.getShipBoard().size() -1).getColumn()){
                ship.setHorizontal(true);
            }else{
                ship.setHorizontal(false);
            }
            for (SaveField f: saveS.getShipBoard()) {
                Field newField = new Field(f.getRow(),f.getColumn(), "battle");
                for (int i = 0; i <b.length ; i++) {
                    for (int j = 0; j <b[i].length ; j++) {
                        if(b[i][j].getRow() == newField.getRow() && b[i][j].getColumn() == newField.getColumn()){
                            newField.setMark(b[i][j].isMark());
                            newField.setShot(b[i][j].isShot());
                        }
                    }
                }
                ship.getShipBoard().add(newField);
            }
            retList.add(ship);
        }
        return retList;
    }
    private Field[][] convertSaveField(SaveField bt[][]){
        Field button[][] = new Field[bt.length][bt.length];
        for (int i = 0; i <bt.length ; i++) {
            for (int j = 0; j <bt[i].length ; j++) {
                button[i][j] = new Field(bt[i][j].getRow(),bt[i][j].getColumn(), "battle");
                button[i][j].setMark(bt[i][j].isMark());
                button[i][j].setShot(bt[i][j].isShot());
            }
        }
        return button;
    }

}
