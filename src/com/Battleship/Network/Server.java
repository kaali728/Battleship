package com.Battleship.Network;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Server {
    // Variablen, die im gesamten Programm benötigt werden.
    public static Writer out;        // Verpackung des Socket-Ausgabestroms.
    public static JButton button;    // Der o. g. Knopf.
    public static int fieldsize;
    public static int carrierCount, battleshipCount, submarineCount, destroyerCount;


    private static ServerSocket serverSocket;
    private static Socket socket;

    public static void create(int port) {
        System.out.println("SERVER CREATE");

        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
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
                    System.out.println("[Client]: " + str);

                    // Graphische Oberfläche aufbauen.
//                        SwingUtilities.invokeLater(
//                                Server::startGui
//                        );

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
            }
        });

        serverThread.start();

//                    try {
//                        // Send message to client.
//                        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
//                        printWriter.println("Hello, I am a message from the almighty server.");
//                        printWriter.flush();
//
//                        // Get message from client.
//                        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
//                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                        out = new OutputStreamWriter(socket.getOutputStream());
//
//                        String str = bufferedReader.readLine();
//                        System.out.println("[Client]: " + str);
//
//                        // Graphische Oberfläche aufbauen.
////                        SwingUtilities.invokeLater(
////                                Server::startGui
////                        );
//
//                        // Netzwerknachrichten lesen und verarbeiten.
//                        // Da die graphische Oberfläche von einem separaten Thread verwaltet
//                        // wird, kann man hier unabhängig davon auf Nachrichten warten.
//                        // Manipulationen an der Oberfläche sollten aber mittels invokeLater
//                        // (oder invokeAndWait) ausgeführt werden.
//                        //            while (true) {
//                        //                String line = bufferedReader.readLine();
//                        //                if (line == null) break;
//                        //                SwingUtilities.invokeLater(
//                        //                        () -> button.setEnabled(true)
//                        //                );
//                        //            }
//
//                        // EOF ins Socket "schreiben" und das Programm explizit beenden
//                        // (weil es sonst weiterlaufen würde, bis der Benutzer das Hauptfenster
//                        // schließt).
//                        socket.shutdownOutput();
//                        System.out.println("Connection closed.");
//                        System.exit(0);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }


    }

    // Graphische Oberfläche aufbauen und anzeigen.
    private static void startGui() {
        // Hauptfenster mit Titelbalken etc. (JFrame) erzeugen.
        JFrame frame = new JFrame("Server");

        // Beim Schließen des Fensters (z. B. durch Drücken des
        // X-Knopfs in Windows) soll das Programm beendet werden.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Der Inhalt des Fensters soll von einem BoxLayout-Manager
        // verwaltet werden, der seine Bestandteile vertikal (von
        // oben nach unten) anordnet.
        frame.setContentPane(Box.createVerticalBox());

        // Dehnbaren Zwischenraum am oberen Rand hinzufügen.
        frame.add(Box.createGlue());

        // Horizontal zentrierten Knopf (JButton) hinzufügen.
        button = new JButton("Server");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(
                // Wenn der Knopf gedrückt wird,
                // erfolgt eine Kontrollausgabe auf System.out.
                // Anschließend wird der Knopf deaktiviert
                // und eine beliebige Nachricht an die andere "Seite" geschickt,
                // damit diese ihren Knopf aktivieren kann.
                (e) -> {
                    System.out.println("Server clicked the button.");
                    button.setEnabled(false);
                    try {
                        out.write(String.format("%s%n", "message"));
                        out.flush();
                    } catch (IOException ex) {
                        System.out.println("write to socket failed");
                    }
                }
        );
        frame.add(button);

        // Der Server-Knopf soll anfangs deaktiviert sein.
        button.setEnabled(false);

        // Dehnbaren Zwischenraum am unteren Rand hinzufügen.
        frame.add(Box.createGlue());

        // Am Schluss (!) die optimale Fenstergröße ermitteln (pack)
        // und das Fenster anzeigen (setVisible).
        frame.pack();
        frame.setVisible(true);
    }

//    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = new ServerSocket(5000);
//        Socket socket = serverSocket.accept();
//
//        // Send message to client.
//        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
//        printWriter.println("Hello, I am a message from the almighty server.");
//        printWriter.flush();
//
//        // Get message from client.
//        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
//        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//        out = new OutputStreamWriter(socket.getOutputStream());
//
//        String str = bufferedReader.readLine();
//        System.out.println("[Client]: " + str);
//
//        // Graphische Oberfläche aufbauen.
//        SwingUtilities.invokeLater(
//                Server::startGui
//        );
//
//        // Netzwerknachrichten lesen und verarbeiten.
//        // Da die graphische Oberfläche von einem separaten Thread verwaltet
//        // wird, kann man hier unabhängig davon auf Nachrichten warten.
//        // Manipulationen an der Oberfläche sollten aber mittels invokeLater
//        // (oder invokeAndWait) ausgeführt werden.
//        while (true) {
//            String line = bufferedReader.readLine();
//            if (line == null) break;
//            SwingUtilities.invokeLater(
//                    () -> button.setEnabled(true)
//            );
//        }
//
//        // EOF ins Socket "schreiben" und das Programm explizit beenden
//        // (weil es sonst weiterlaufen würde, bis der Benutzer das Hauptfenster
//        // schließt).
//        socket.shutdownOutput();
//        System.out.println("Connection closed.");
//        System.exit(0);
//    }
}
