package com.Battleship.Network;

import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        Socket socket = serverSocket.accept();

        // Send message to client.
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println("Hello, I am a message from the almighty server.");
        printWriter.flush();

        // Get message from client.
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String str = bufferedReader.readLine();
        System.out.println("[Client]: " + str);
    }
}
