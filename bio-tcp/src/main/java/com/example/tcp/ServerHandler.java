package com.example.tcp;

import java.io.*;
import java.net.Socket;

public class ServerHandler implements Runnable {

    Socket socket = null;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        BufferedReader reader = null;
        PrintStream writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintStream(socket.getOutputStream());

            while (true) {
                String message = reader.readLine();
                if (message == null) {
                    break;
                }
                System.out.println("receive message: " + message);
                writer.println(message);
                writer.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
