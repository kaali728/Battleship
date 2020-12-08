package com.Battleship.UI;

import com.Battleship.Model.Board;
import com.Battleship.Model.Ship;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientScreen extends JPanel {
    private static JButton button;
    private static JTextArea chat;
    private static JTextField chatInput;
    private static JScrollPane chatScroll;
    public static Writer out;        // Verpackung des Socket-Ausgabestroms.
    public int fieldsize;
    GamePanel mainPanel;
    Board postionBoard;


    int carrierCount, battleshipCount, submarineCount, destroyerCount;

    ClientScreen(String address, Integer port, GamePanel mainPanel) {
        this.mainPanel = mainPanel;
        new SwingWorker() {
            @Override
            protected Object doInBackground() {
                try {
                    Socket socket = new Socket(address, port);

                    // Send message to server.
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.println("Enemy has connected.");
                    printWriter.flush();

                    // Get message from server.
                    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    out = new OutputStreamWriter(socket.getOutputStream());

                    // Wir ueberpruefen ob die Spielfeld groesse schon gesetzt ist,
                    // ist das nicht der fall nehmen wir die erste Nachricht des Servers
                    // was als Schluss die Groesse des Spielfeldes ausgibt.

                    String str = bufferedReader.readLine();
                    String finalStr = "[Battleship]: " + str;
                    System.out.println("[Battleship]: " + str);
                    str = str.substring(str.length() - 2);
                    str = str.replaceAll("\\s", "");
                    fieldsize = Integer.parseInt(str);
                    for (int i = 0; i <4 ; i++) {
                        String str2 = bufferedReader.readLine();
                        str2 = str2.substring(str2.length() - 2);
                        str2 = str2.replaceAll("\\s","");
                        //System.out.println("[Server]: " + str2);
                        switch (i){
                            case 0:
                                carrierCount = Integer.parseInt(str2);
                                break;
                            case 1:
                                battleshipCount = Integer.parseInt(str2);
                                break;
                            case 2:
                                submarineCount = Integer.parseInt(str2);
                                break;
                            case 3:
                                destroyerCount = Integer.parseInt(str2);
                                break;
                        }
                    }

                    System.out.println("CONNECTION THREAD: " + Thread.currentThread().getName());

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
        System.out.println("LAYOUT THREAD: " + Thread.currentThread().getName());
        System.out.println("FIELDSIZE " + fieldsize);
        System.out.println("carrierCount " + carrierCount);
        System.out.println("battleshipCount " + battleshipCount);
        System.out.println("submarineCount " + submarineCount);
        System.out.println("destroyerCount " + destroyerCount);

        ArrayList<Ship> fleet = new ArrayList<>();
        for (int i=0; i<carrierCount; i++){
            fleet.add(new Ship("carrier"));
        }
        for (int i=0; i<battleshipCount; i++){
            fleet.add(new Ship("battleship"));
        }
        for (int i=0; i<submarineCount; i++){
            fleet.add(new Ship("submarine"));
        }
        for (int i=0; i<destroyerCount; i++){
            fleet.add(new Ship("destroyer"));
        }
        this.mainPanel.getNetworkPlayer().setFleet(fleet);
        this.mainPanel.getNetworkPlayer().setFieldsize(fieldsize);
        mainPanel.setGameState("setzen");
      
        button = new JButton("Client");
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
                    }
                    catch (IOException ex) {
                        System.out.println("write to socket failed");
                    }
                }
        );

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
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(100));
        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(vbox);

        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            ArrayList<Ship> fleet1 = this.mainPanel.getNetworkPlayer().getFleet();
            postionBoard = new Board(fieldsize, fleet1,this.mainPanel.getGameState());
            hbox.add(postionBoard);
            hbox.add(Box.createHorizontalStrut(10));
        }
        add(hbox);

        add(chatScroll);
        add(chatInput);
        updateUI();
    }
}
