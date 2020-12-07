package com.Battleship.Network;

import java.net.*;
import java.io.*;

public class vorlage_client {
    // Client-Seite eines sehr einfachen Chat-Programms mit Sockets.
    // (Anstelle von "throws IOException" sollte man Ausnahmen besser
    // gezielt mit try-catch auffangen.)
    public static void main (String [] args) throws IOException {
        // Verbindung zum Server mit Name oder IP-Adresse args[0]
        // über Portnummer port herstellen.
        // Als Resultat erhält man ein Socket.
        Socket s = new Socket("localhost", 5000);
        System.out.println("Connection established.");

        // Ein- und Ausgabestrom des Sockets ermitteln
        // und als BufferedReader bzw. Writer verpacken
        // (damit man zeilen- bzw. zeichenweise statt byteweise arbeiten kann).
        BufferedReader in =
                new BufferedReader(new InputStreamReader(s.getInputStream()));
        Writer out = new OutputStreamWriter(s.getOutputStream());

        // Standardeingabestrom ebenfalls als BufferedReader verpacken.
        BufferedReader usr =
                new BufferedReader(new InputStreamReader(System.in));

        // Abwechselnd vom Benutzer lesen und ins Socket schreiben
        // bzw. vom Socket lesen und auf den Bildschirm schreiben.
        // Abbruch bei EOF oder Leerzeile vom Benutzer bzw. bei EOF vom Socket.
        while (true) {
            System.out.print(">>> ");
            String line = usr.readLine();
            if (line == null || line.equals("")) break;
            out.write(String.format("%s%n", line));
            out.flush();
            // flush sorgt dafür, dass der Writer garantiert alle Zeichen
            // in den unterliegenden Ausgabestrom schreibt.

            line = in.readLine();
            if (line == null) break;
            System.out.println("<<< " + line);
        }

        // EOF ins Socket "schreiben".
        s.shutdownOutput();
        System.out.println("Connection closed.");
    }
}
