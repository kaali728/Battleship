package com.Battleship.Network;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;

public class Client {
    // Variablen, die im gesamten Programm benötigt werden.
    public static Writer out;		// Verpackung des Socket-Ausgabestroms.
    public static JButton button;	// Der o. g. Knopf.

    public static void create(String address, int port) {
        System.out.println("CLIENT CREATE");

        Thread clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    Socket socket = new Socket(address, port);

                    // Send message to server.
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.println("Hello, I am a message from the client.");
                    printWriter.flush();

                    // Get message from server.
                    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    out = new OutputStreamWriter(socket.getOutputStream());

                    String str = bufferedReader.readLine();
                    System.out.println("[Server]: " + str);

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

        clientThread.start();
    }

    // Graphische Oberfläche aufbauen und anzeigen.
    private static void startGui () {
        // Hauptfenster mit Titelbalken etc. (JFrame) erzeugen.
        JFrame frame = new JFrame("Client");

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
        button = new JButton("Client");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(
                // Wenn der Knopf gedrückt wird,
                // erfolgt eine Kontrollausgabe auf System.out.
                // Anschließend wird der Knopf deaktiviert
                // und eine beliebige Nachricht an die andere "Seite" geschickt,
                // damit diese ihren Knopf aktivieren kann.
                (e) -> {
                    System.out.println("Client clicked the button.");
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
        frame.add(button);

        // Dehnbaren Zwischenraum am unteren Rand hinzufügen.
        frame.add(Box.createGlue());

        // Am Schluss (!) die optimale Fenstergröße ermitteln (pack)
        // und das Fenster anzeigen (setVisible).
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);

        // Send message to server.
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println("Hello, I am a message from the client.");
        printWriter.flush();

        // Get message from server.
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        out = new OutputStreamWriter(socket.getOutputStream());

        String str = bufferedReader.readLine();
        System.out.println("[Server]: " + str);

        // Graphische Oberfläche aufbauen.
        SwingUtilities.invokeLater(
                Client::startGui
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
                    () -> button.setEnabled(true)
            );
        }

        // EOF ins Socket "schreiben" und das Programm explizit beenden
        // (weil es sonst weiterlaufen würde, bis der Benutzer das Hauptfenster
        // schließt).
        socket.shutdownOutput();
        System.out.println("Connection closed.");
        System.exit(0);
    }
}
