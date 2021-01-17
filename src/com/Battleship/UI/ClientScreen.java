package com.Battleship.UI;

import com.Battleship.Model.*;
import com.Battleship.SpielstandLaden.GameLoad;
import com.Battleship.SpielstandLaden.GameObj;
import com.Battleship.SpielstandLaden.SpeichernUnterClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/**
 * The type Client screen.
 */
public class ClientScreen extends JPanel {
    private static JButton button;
    private static JTextArea chat;
    private static JTextField chatInput;
    private static JScrollPane chatScroll;
    /**
     * The constant out.
     */
    public static Writer out;        // Verpackung des Socket-Ausgabestroms.
    /**
     * The Fieldsize.
     */
    public int fieldsize;
    private String ships;
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
     * The Vertical.
     */
    JButton vertical;

    public JButton saveButton;

    /**
     * The Game over.
     */

    boolean gameOver = false;
    SpeichernUnterClass speicher;
    GameObj spielStand;
    private GameLoad load;
    private boolean loadedGame = false;


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
     * Instantiates a new Client screen.
     *
     * @param address   the address
     * @param port      the port
     * @param mainPanel the main panel
     */
    ClientScreen(String address, Integer port, GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        load = new GameLoad();
        new SwingWorker() {
            @Override
            protected Object doInBackground() {
                try {
                    Socket socket = new Socket(address, port);

                    // Send message to server.
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    System.out.println("CLIENT LOG");

                    // Get message from server.
                    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    out = new OutputStreamWriter(socket.getOutputStream());

                    // Wir ueberpruefen ob die Spielfeld groesse schon gesetzt ist,
                    // ist das nicht der fall nehmen wir die erste Nachricht des Servers
                    // was als Schluss die Groesse des Spielfeldes ausgibt.
                    String str = bufferedReader.readLine();
                    if(str.contains("S: load")){
                        System.out.println(str);
                        int ut = Integer.parseInt(str.split(" ")[2]);
                        load.setFileName(ut+".json");
                        load.setClient(true);
                        GameObj spielStand = load.readFile(null);
                        if (spielStand != null) {
                            SwingUtilities.invokeAndWait(()->{
                                fieldsize = spielStand.size;
                                Field bt[][] = convertSaveField(spielStand.playerButton);
                                Field enbt[][] = convertSaveField(spielStand.enemyButton);
                                ArrayList<Ship> playerFleet = convertSaveShip(spielStand.playerFleet, bt);
                                mainPanel.getSingleplayer().setFieldsize(spielStand.size);
                                mainPanel.getSingleplayer().setFleet(playerFleet);
                                mainPanel.setLoadedPlayerHealth(spielStand.PlayerHealth);
                                mainPanel.setLoadedEnemyHealth(spielStand.EnemyHealth);
                                postionBoard = new Board(fieldsize, playerFleet, "battle");
                                postionBoard.setMyShip(bt);
                                enemyBoard = new Board(fieldsize, "battle", out,true);
                                enemyBoard.setMyShipMultiPlayerLoad(enbt);
                                enemyBoard.setUsedCord(spielStand.usedCord);
                            });

                        }
                        loadedGame = true;
//                        System.out.println("positionboard"+ postionBoard);
//                        System.out.println("positionboard"+ enemyBoard);
//                        System.out.println(loadedGame);
                       // System.out.println("S: load client " + ut);
                        printWriter.println("C: done");
                        printWriter.flush();

                        SwingUtilities.invokeLater(() -> {
                                    initLayout();
                                }
                        );

                    }else{
                        str = str.substring(str.length() - 2);
                        str = str.replaceAll("\\s", "");
                        fieldsize = Integer.parseInt(str);
                        System.out.println("CLIENT FIELDSIZE: " + fieldsize);
                    }

                    if (fieldsize != 0 && !loadedGame) {
                        printWriter.println("C: next");
                        printWriter.flush();
                        System.out.println("C: next");
                    }

                    // Netzwerknachrichten lesen und verarbeiten.
                    // Da die graphische Oberfläche von einem separaten Thread verwaltet
                    // wird, kann man hier unabhängig davon auf Nachrichten warten.
                    // Manipulationen an der Oberfläche sollten aber mittels invokeLater
                    // (oder invokeAndWait) ausgeführt werden.
                    while (true) {
                        String line = bufferedReader.readLine();

                        if (line == null) break;

                        // Protokoll
                        if (line.equals("S: size " + fieldsize + " " + fieldsize)) {
                            printWriter.println("C: next");
                            printWriter.flush();
                            System.out.println("C: next");
                        }

                        // Schiffe synchronisieren
                        if (fieldsize != 0 && line.contains("ships")) {
                            ships = line;
                            printWriter.println("C: done");
                            printWriter.flush();
                            System.out.println("C: done");

                            carrierCount = (int) ships.chars().filter(ch -> ch == '5').count();
                            battleshipCount = (int) ships.chars().filter(ch -> ch == '4').count();
                            submarineCount = (int) ships.chars().filter(ch -> ch == '3').count();
                            destroyerCount = (int) ships.chars().filter(ch -> ch == '2').count();

                            System.out.println(carrierCount);

                            SwingUtilities.invokeLater(() -> {
                                        initLayout();
                                    }
                            );
                        }



                        // Client ist bereit für die Schlacht
                        if (fieldsize != 0 && !loadedGame && line.equals("S: ready")) {
                            SwingUtilities.invokeLater(() -> {
                                button.setEnabled(true);
                                postionBoard.setOut(out);
                                postionBoard.setClient(true);
                                enemyBoard.multiEnableBtns(false);
                                saveButton.setVisible(false);
                            });
                        }

                        // Client ist bereit für die Schlacht
                        if (loadedGame && line.equals("S: ready")) {
                            SwingUtilities.invokeLater(() -> {
                                button.setEnabled(true);
                                postionBoard.setOut(out);
                                postionBoard.setClient(true);
                                enemyBoard.multiEnableBtns(false);
                                saveButton.setVisible(false);
                            });
                        }

                        // Schuss vom Server verarbeiten.
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
                                saveButton.setVisible(false);
                                printWriter.println("C: next");
                                printWriter.flush();
                            }
                        }

                        if (line.contains("next")) {
                            enemyBoard.multiEnableBtns(true);
                            saveButton.setVisible(true);
                        }

                        if(line.contains("save")){
                            int ut = Integer.parseInt(line.split(" ")[2]);
                            System.out.println("save clinet"+ut);
                            SwingUtilities.invokeAndWait(() -> {
                                speicher = new SpeichernUnterClass(postionBoard, enemyBoard);
                                speicher.setDefaultname(ut);
                                speicher.setMultiplayer(true);
                                speicher.setClient(true);
                                speicher.setFileName(ut+".json");
                                speicher.saveAs(null);
                            });
                            printWriter.println("C: done");
                            printWriter.flush();
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
    }

    /**
     * Init layout.
     */
    public void initLayout() {
        System.out.println("LAYOUT" + carrierCount);
        if(!loadedGame){
            ArrayList<Ship> fleet = new ArrayList<>();
            for (int i = 0; i < carrierCount; i++) {
                fleet.add(new Ship("carrier"));
            }
            for (int i = 0; i < battleshipCount; i++) {
                fleet.add(new Ship("battleship"));
            }
            for (int i = 0; i < submarineCount; i++) {
                fleet.add(new Ship("submarine"));
            }
            for (int i = 0; i < destroyerCount; i++) {
                fleet.add(new Ship("destroyer"));
            }
            this.mainPanel.getNetworkPlayer().setFleet(fleet);
            this.mainPanel.getNetworkPlayer().setFieldsize(fieldsize);
            mainPanel.setGameState("setzen");
            vertical = new JButton("vertical");


            enemyBoard = new Board(fieldsize, "battle", out, true);
            enemyBoard.setVisible(false);

            ArrayList<Ship> fleet1 = this.mainPanel.getNetworkPlayer().getFleet();
            postionBoard = new Board(fieldsize, fleet1, this.mainPanel.getGameState());

            add(vertical);

            vertical.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (postionBoard != null) {
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
        }
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(100));
        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(vbox);

        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            hbox.add(postionBoard);
            hbox.add(Box.createHorizontalStrut(10));
            hbox.add(enemyBoard);
            hbox.add(Box.createHorizontalStrut(10));
        }

        button = new JButton("Ready");

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setEnabled(false);
        button.addActionListener(
                // Wenn der Knopf gedrückt wird,
                // erfolgt eine Kontrollausgabe auf System.out.
                // Anschließend wird der Knopf deaktiviert
                // und eine beliebige Nachricht an die andere "Seite" geschickt,
                // damit diese ihren Knopf aktivieren kann.
                (e) -> {
                    ArrayList<Ship> placedShips = this.mainPanel.getNetworkPlayer().getFleet();
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
                            out.write(String.format("%s%n", "C: ready"));
                            System.out.println("C: ready");
                            out.flush();
                            button.setVisible(false);
                            // enemy Spielbrett
                            mainPanel.setGameState("battle");
                            enemyBoard.setVisible(true);
                            vertical.setVisible(false);
                        } catch (IOException ex) {
                            System.out.println("write to socket failed");
                        }
                    }
                }
        );
        saveButton = new JButton("Save Game");
        saveButton.setVisible(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Client save Game");
                Date now = new Date();
                long ut3 = now.getTime() / 1000L;
                try {
                    out.write(String.format("%s%n", "C: save " + ut3));
                    System.out.println("C: save " + ut3);
                    speicher = new SpeichernUnterClass(postionBoard, enemyBoard);
                    speicher.setDefaultname(ut3);
                    speicher.setMultiplayer(true);
                    speicher.saveAs(null);
                    out.flush();
                } catch (Exception es) {
                    System.out.println("write to socket failed by C; save" + es);
                }
            }
        });

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
        add(hbox);
        add(button);
        add(saveButton);
        Box chat = Box.createVerticalBox();
        {
            chat.add(Box.createVerticalStrut(15));
            chat.add(chatScroll);
            chat.add(Box.createVerticalStrut(10));
            chat.add(chatInput);
        }

        add(chat);
        updateUI();
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
