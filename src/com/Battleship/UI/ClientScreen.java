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
                    printWriter.println("Hello, I am a message from the client.");
                    printWriter.flush();

                    // Get message from server.
                    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    out = new OutputStreamWriter(socket.getOutputStream());

                    // Wir ueberpruefen ob die Spielfeld groesse schon gesetzt ist,
                    // ist das nicht der fall nehmen wir die erste Nachricht des Servers
                    // was als Schluss die Groesse des Spielfeldes ausgibt.

                    String str = bufferedReader.readLine();
                    System.out.println("[Server]: " + str);
                    str = str.substring(str.length() - 2);
                    str = str.replaceAll("\\s","");
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

                    SwingUtilities.invokeLater(() -> initLayout());

                    // Netzwerknachrichten lesen und verarbeiten.
                    // Da die graphische Oberfläche von einem separaten Thread verwaltet
                    // wird, kann man hier unabhängig davon auf Nachrichten warten.
                    // Manipulationen an der Oberfläche sollten aber mittels invokeLater
                    // (oder invokeAndWait) ausgeführt werden.
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) break;
                        SwingUtilities.invokeLater(
                                () -> button.setEnabled(true)
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

        // Hauptfenster mit Titelbalken etc. (JFrame) erzeugen.
        //JFrame frame = new JFrame("Client");

        // Beim Schließen des Fensters (z. B. durch Drücken des
        // X-Knopfs in Windows) soll das Programm beendet werden.
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // Der Inhalt des Fensters soll von einem BoxLayout-Manager
//        // verwaltet werden, der seine Bestandteile vertikal (von
//        // oben nach unten) anordnet.
//        frame.setContentPane(Box.createVerticalBox());
//
//        // Dehnbaren Zwischenraum am oberen Rand hinzufügen.
//        frame.add(Box.createGlue());



        // Horizontal zentrierten Knopf (JButton) hinzufügen.
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
                        out.write(String.format("%s%n", "message"));
                        out.flush();
                    }
                    catch (IOException ex) {
                        System.out.println("write to socket failed");
                    }
                }
        );
        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            ArrayList<Ship> fleet1 = this.mainPanel.getNetworkPlayer().getFleet();
            postionBoard = new Board(fieldsize, fleet1,this.mainPanel.getGameState());
            hbox.add(postionBoard);
            hbox.add(Box.createHorizontalStrut(10));
        }
        add(hbox);
        add(button);
        updateUI();
        //frame.add(button);
        //playerBoard = new Board(this.mainPanel.getSingleplayer().getFieldsize(), this.mainPanel.getSingleplayer().getFleet(), this.mainPanel.getGameState(), true);
        //frame.add(new Board(fieldsize));

        // Dehnbaren Zwischenraum am unteren Rand hinzufügen.
//        frame.add(Box.createGlue());
//
//        // Am Schluss (!) die optimale Fenstergröße ermitteln (pack)
//        // und das Fenster anzeigen (setVisible).
//        frame.pack();
//        frame.setVisible(true);

//        button = new JButton("Client");
//        button.setAlignmentX(Component.CENTER_ALIGNMENT);
//        button.addActionListener(
//                // Wenn der Knopf gedrückt wird,
//                // erfolgt eine Kontrollausgabe auf System.out.
//                // Anschließend wird der Knopf deaktiviert
//                // und eine beliebige Nachricht an die andere "Seite" geschickt,
//                // damit diese ihren Knopf aktivieren kann.
//                (e) -> {
//                    button.setEnabled(false);
//                    try {
//                        out.write(String.format("%s%n", "message"));
//                        out.flush();
//                    }
//                    catch (IOException ex) {
//                        System.out.println("write to socket failed");
//                    }
//                }
//        );
//
//        setBackground(Color.green);
//        Box vbox = Box.createVerticalBox();
//        vbox.add(Box.createVerticalStrut(100));
//        vbox.setAlignmentX(Component.CENTER_ALIGNMENT);
//        add(vbox);
    }
}
