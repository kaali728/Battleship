package com.Battleship.UI;

import com.Battleship.Model.Board;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerScreen extends JPanel {
    public static Writer out;        // Verpackung des Socket-Ausgabestroms.
    public static JButton button;    // Der o. g. Knopf.
    public static JTextArea chat;
    private static JTextField chatInput;
    private static JScrollPane chatScroll;
    private int fieldsize;

    ServerScreen(int port, int fieldsize) {
        this.fieldsize = fieldsize;
        new SwingWorker() {
            @Override
            protected Object doInBackground() {
                try {
                    ServerSocket serverSocket = new ServerSocket(port);
                    Socket socket = serverSocket.accept();

                    // Send message to client.
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.println("Welcome to Battleship the field size is selected to " + fieldsize);
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
                                    button.setEnabled(true);
                                    String tmp = chat.getText();
                                    chat.setText(tmp + "\n" + "[Enemy]: " + line);
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
                    System.out.println("Server clicked the button.");
                    button.setEnabled(false);
//                    try {
//                        out.write(String.format("%s%n", "message"));
//                        out.flush();
//                    } catch (IOException ex) {
//                        System.out.println("write to socket failed");
//                    }
                }
        );

        // Der Server-Knopf soll anfangs deaktiviert sein.
        button.setEnabled(false);

        chat = new JTextArea(10, 70);
        chat.setEditable(false);
        chat.setBackground(Color.lightGray);

        chatInput = new JTextField(1);
        chatInput.addActionListener(
                (e) -> {
                    try {
                        System.out.println("CHAT: " + chatInput.getText());
                        out.write(String.format("%s%n", chatInput.getText()));
                        out.flush();
                        String tmp = chat.getText();
                        chat.setText(tmp + "\n" + "[You]: " + chatInput.getText());
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

        // Board
        Box hbox = Box.createHorizontalBox();
        {
            hbox.add(Box.createHorizontalStrut(10));
            hbox.add(new Board(fieldsize));
            hbox.add(Box.createHorizontalStrut(10));
        }
        add(hbox);

        add(chatScroll);
        add(chatInput);
        updateUI();
    }
}
