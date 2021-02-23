package com.example.niotcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

    private static final int PORT = 8999;

    public static void main(String[] args) {

        SocketChannel socketChannel = null;
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            SocketAddress socketAddress = new InetSocketAddress(PORT);
            if (!socketChannel.connect(socketAddress)) {
                while (!socketChannel.finishConnect()) {
                    System.out.print(".");
                }
            }

            System.out.print("\n");
            System.out.println("connect to " + socketAddress.toString());

            ByteBuffer readBuffer = ByteBuffer.allocate(1024);

            while (true) {

                String input = inputReader.readLine();

                if (input.equals("exit")) {
                    break;
                }

                if (input != null || !input.equals("")) {
                    System.out.println("input message: " + input);
                    socketChannel.write(Util.string2Bytebuffer(input));
                    Thread.sleep(1000);
                }

                readBuffer.clear();
                int byteLen = socketChannel.read(readBuffer);

                if (byteLen == -1) {
                    break;
                } else if (byteLen > 0) {
                    String message = Util.byteBuffer2String(readBuffer);
                    System.out.println("receive message: " + message);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
