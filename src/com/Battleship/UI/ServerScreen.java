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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The type Server screen.
 */
public class ServerScreen extends JPanel {
    /**
     * The constant out.
     */
    public static Writer out;        // Verpackung des Socket-Ausgabestroms.
    /**
     * The constant button.
     */
    public static JButton button;    // Der o. g. Knopf.
    public JButton saveButton;
    JButton spielstandLaden;
    /**
     * The constant chat.
     */
    public static JTextArea chat;
    private static JTextField chatInput;
    private static JScrollPane chatScroll;
    private int fieldsize;
    /**
     * The Port.
     */
    int port;
    /**
     * The Carrier count.
     */
    int carrierCount, /**
     * The Battleship count.
     */
    battleshipCount, /**
     * The Submarine count.
     */
    submarineCount, /**
     * The Destroyer count.
     */
    destroyerCount;
    /**
     * The Vertical.
     */
    JButton vertical;
    /**
     * The Main panel.
     */
    GamePanel mainPanel;
    /**
     * The Postion board.
     */
    Board postionBoard;
    /**
     * The Enemy board.
     */
    Board enemyBoard;
    /**
     * The Game over.
     */
    boolean gameOver = false;
    SpeichernUnterClass speicher;
    private GameLoad load;


    /**
     * Instantiates a new Server screen.
     *
     * @param port            the port
     * @param fieldsize       the fieldsize
     * @param carrierCount    the carrier count
     * @param battleshipCount the battleship count
     * @param submarineCount  the submarine count
     * @param destroyerCount  the destroyer count
     * @param mainPanel       the main panel
     */
    ServerScreen(int port, int fieldsize, int carrierCount, int battleshipCount, int submarineCount, int destroyerCount, GamePanel mainPanel) {
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
                            SwingUtilities.invokeLater(() -> {
                                button.setEnabled(true);
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
                                        button.setEnabled(true);
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

    /**
     * Init layout.
     */
    public void initLayout() {
        button = new JButton("Ready");
        spielstandLaden = new JButton("Load Game");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setEnabled(false);
        enemyBoard = new Board(fieldsize, "battle", out);
        vertical = new JButton("vertical");
        saveButton = new JButton("Save Game");
        saveButton.setVisible(false);
        enemyBoard.setVisible(false);
        load = new GameLoad();
        button.addActionListener(
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
                        button.setEnabled(false);
                        try {
                            // Gibt dem Gegner die Nachricht, dass er an der Reihe ist.
                            out.write(String.format("%s%n", "S: ready"));
                            System.out.println("S: ready");
                            out.flush();
                            this.mainPanel.setGameState("battle");
                            button.setVisible(false);
                            vertical.setVisible(false);
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
        spielstandLaden.addActionListener(
                (e) -> {
                    try{
                        GameObj spielStand = load.readFile(null);
                        out.write(String.format("%s%n", "S: load "+load.getFileName()));
                        System.out.println("S: load "+ load.getFileName());
                        out.flush();
                    }catch (Exception es){
                        System.out.println("write to socket failed by S:load " + es);
                    }

//                    GameObj spielStand = load.readFile(null);
//                    if(spielStand != null){
//                        Field bt[][] = convertSaveField(spielStand.playerButton);
//                        Field enbt[][] = convertSaveField(spielStand.enemyButton);
//                        ArrayList<Ship> playerFleet = convertSaveShip(spielStand.playerFleet, bt);
//                        ArrayList<Ship> enemyFleet = convertSaveShip(spielStand.enemyFleet, enbt);
//                        this.mainPanel.getSingleplayer().setFieldsize(spielStand.size);
//                        this.mainPanel.getEnemyPlayer().setFieldsize(spielStand.size);
//                        this.mainPanel.getSingleplayer().setFleet(playerFleet);
//                        postionBoard.setMyShip(bt);
//                        enemyBoard.setMyShip(enbt);
//                        this.mainPanel.setLoadedPlayerHealth(spielStand.PlayerHealth);
//                        this.mainPanel.setLoadedEnemyHealth(spielStand.EnemyHealth);
//                        this.mainPanel.changeScreen("battle");
//                    }
                    //change screen to battle
                }
        );

        chat = new JTextArea(7, 55);
        chat.setEditable(false);
        chat.setBackground(Color.lightGray);

        chatInput = new JTextField(55);
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
        add(saveButton);
        add(spielstandLaden);
        add(button);
        add(vertical);
        // Board
        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            ArrayList<Ship> fleet = this.mainPanel.getSingleplayer().getFleet();
            postionBoard = new Board(fieldsize, fleet, this.mainPanel.getGameState());
            hbox.add(postionBoard);
            hbox.add(enemyBoard);
            hbox.add(Box.createHorizontalStrut(10));
        }
        add(hbox);

        vertical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(postionBoard != null) {
                    if (postionBoard.isHorizontal()) {
                        vertical.setText("horizontal");
                        postionBoard.setHorizontal(!postionBoard.isHorizontal());
                    } else {
                        vertical.setText("vertical");
                        postionBoard.setHorizontal(!postionBoard.isHorizontal());
                    }
                }
            }
        });

        Box chat = Box.createVerticalBox();
        {
            chat.add(Box.createVerticalStrut(15));
            chat.add(chatScroll);
            chat.add(Box.createVerticalStrut(10));
            chat.add(chatInput);
        }

        add(chat);
        repaint();
    }

    private  ArrayList<Ship> convertSaveShip(ArrayList<SaveShip> saveShips, Field b[][]){
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
