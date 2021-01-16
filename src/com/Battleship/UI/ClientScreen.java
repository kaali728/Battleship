package com.Battleship.UI;

import com.Battleship.Model.Board;
import com.Battleship.Model.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

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
    /**
     * The Game over.
     */
    boolean gameOver = false;


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
                    str = str.substring(str.length() - 2);
                    str = str.replaceAll("\\s", "");
                    fieldsize = Integer.parseInt(str);
                    System.out.println("CLIENT FIELDSIZE: " + fieldsize);

                    if (fieldsize != 0) {
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
                        if (fieldsize != 0 && carrierCount != 0 && battleshipCount != 0 && submarineCount != 0 && destroyerCount != 0 && line.equals("S: ready")) {
                            SwingUtilities.invokeLater(() -> {
                                button.setEnabled(true);
                                postionBoard.setOut(out);
                                postionBoard.setClient(true);
                                enemyBoard.multiEnableBtns(false);
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
                                printWriter.println("C: next");
                                printWriter.flush();
                            }
                        }

                        if (line.contains("next")) {
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

        button = new JButton("Client");
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


        enemyBoard = new Board(fieldsize, "battle", out, true);
        enemyBoard.setVisible(false);


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
        add(button);
        add(vertical);
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(100));
        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(vbox);

        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            ArrayList<Ship> fleet1 = this.mainPanel.getNetworkPlayer().getFleet();
            postionBoard = new Board(fieldsize, fleet1, this.mainPanel.getGameState());
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
        add(chatScroll);
        add(chatInput);
        updateUI();
    }
}
