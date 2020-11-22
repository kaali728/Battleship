package com.Battleship.Network;

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 5000);

        // Send message to server.
        PrintWriter printWriter = new PrintWriter(s.getOutputStream());
        printWriter.println("Hello, I am a message from the client.");
        printWriter.flush();

        // Get message from server.
        InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String str = bufferedReader.readLine();
        System.out.println("[Server]: " + str);
    }
}
