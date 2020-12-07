package com.Battleship.Network;

import java.net.*;
import java.io.*;

public class vorlage_Server {
    // Server-Seite eines sehr einfachen Chat-Programms mit Sockets.
    // (Anstelle von "throws IOException" sollte man Ausnahmen besser
    // gezielt mit try-catch auffangen.)
    public static void main (String [] args) throws IOException {
        // Server-Socket erzeugen und an diesen Port binden.
        ServerSocket ss = new ServerSocket(5000);

        // Auf eine Client-Verbindung warten und diese akzeptieren.
        // Als Resultat erhält man ein "normales" Socket.
        System.out.println("Waiting for client connection ...");
        Socket s = ss.accept();
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

        // Abwechselnd vom Socket lesen und auf den Bildschirm schreiben
        // bzw. vom Benutzer lesen und ins Socket schreiben.
        // Abbruch bei EOF vom Socket bzw. bei EOF oder Leerzeile vom Benutzer.
        while (true) {
            String line = in.readLine();
            if (line == null) break;
            System.out.println("<<< " + line);

            System.out.print(">>> ");
            line = usr.readLine();
            if (line == null || line.equals("")) break;
            out.write(String.format("%s%n", line));
            out.flush();
            // flush sorgt dafür, dass der Writer garantiert alle Zeichen
            // in den unterliegenden Ausgabestrom schreibt.
        }

        // EOF ins Socket "schreiben".
        s.shutdownOutput();
        System.out.println("Connection closed.");
    }
}
