package com.Battleship.UI;

import com.Battleship.Model.Ship;
import com.Battleship.Model.Board;
import com.Battleship.Network.Server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServerScreen extends JPanel {
    public static Writer out;        // Verpackung des Socket-Ausgabestroms.
    public static JButton button;    // Der o. g. Knopf.
    public static JTextArea chat;
    private static JTextField chatInput;
    private static JScrollPane chatScroll;
    private int fieldsize;
    int port;
    int carrierCount, battleshipCount, submarineCount, destroyerCount;
    GamePanel mainPanel;
    Board postionBoard;

    ServerScreen(int port, int fieldsize, int carrierCount,int battleshipCount,int submarineCount,int destroyerCount, GamePanel mainPanel) {
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
                    printWriter.println("size " + fieldsize + fieldsize);
                    printWriter.println("CarrierCount " + carrierCount);
                    printWriter.println("battleshipCount " + battleshipCount);
                    printWriter.println("submarineCount " + submarineCount);
                    printWriter.println("destroyerCount " + destroyerCount);
                    int ca = 5;
                    int bat = 4;
                    int subma = 3;
                    int des= 2;
                    int sum = carrierCount + battleshipCount+ submarineCount + destroyerCount;


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
                    System.out.println("Ships "+list.replace(",", ""));
                    printWriter.print("Ships "+list.replace(",", ""));
                    printWriter.flush();

                    // Get message from client.
                    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    out = new OutputStreamWriter(socket.getOutputStream());

                    String str = bufferedReader.readLine();
                    String finalStr = "[Battleship]: " + str;
                    System.out.println("[Battleship]: " + str);

                    // Graphische Oberfläche aufbauen.
                    SwingUtilities.invokeLater(() -> {
                                initLayout();
                                chat.setText(finalStr);
                            }
                    );

                    // Netzwerknachrichten lesen und verarbeiten.
                    // Da die graphische Oberfläche von einem separaten Thread verwaltet
                    // wird, kann man hier unabhängig davon auf Nachrichten warten.
                    // Manipulationen an der Oberfläche sollten aber mittels invokeLater
                    // (oder invokeAndWait) ausgeführt werden.
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) break;
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
                                        chat.setText(tmp + "\n" + "[Enemy]: " + line);
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

    public void initLayout() {
        button = new JButton("Server");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(
                // Wenn der Knopf gedrückt wird,
                // erfolgt eine Kontrollausgabe auf System.out.
                // Anschließend wird der Knopf deaktiviert
                // und eine beliebige Nachricht an die andere "Seite" geschickt,
                // damit diese ihren Knopf aktivieren kann.
                (e) -> {
                    button.setEnabled(false);
                    try {
                        // Gibt dem Gegner die Nachricht, dass er an der Reihe ist.
                        out.write(String.format("%s%n", "[Battleship]: It's your turn."));
                        out.flush();
                    } catch (IOException ex) {
                        System.out.println("write to socket failed");
                    }
                }
        );

        // Der Server-Knopf soll anfangs deaktiviert sein.
        button.setEnabled(false);

        chat = new JTextArea(10, 70);
        chat.setEditable(false);
        chat.setBackground(Color.lightGray);

        chatInput = new JTextField(70);
        chatInput.addActionListener(
                (e) -> {
                    try {
                        // Schreibt die Chat Nachricht an den Gegner.
                        out.write(String.format("%s%n", chatInput.getText()));
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

        // Board
        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            ArrayList<Ship> fleet = this.mainPanel.getSingleplayer().getFleet();
            postionBoard = new Board(fieldsize, fleet,this.mainPanel.getGameState());
            hbox.add(postionBoard);
            hbox.add(Box.createHorizontalStrut(10));
        }
        add(hbox);

        add(chatScroll);
        add(chatInput);
        updateUI();
    }
}
