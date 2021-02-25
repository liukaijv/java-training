package com.exampe.udp;

import java.io.IOException;
import java.net.*;

public class Client {

    private static final int PORT = 4444;

    public static void main(String[] args) {

        DatagramSocket socket = null;
        byte[] buf = new byte[1024];

        try {

            socket = new DatagramSocket();

            for (int i = 0; i < 1; i++) {

                byte[] sendData = ("hello_" + i).getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), PORT);
                socket.send(sendPacket);
                System.out.println("send data: " + new String(sendData));

                DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
                socket.receive(receivePacket);
                byte[] data = receivePacket.getData();
                String message = new String(data, 0, receivePacket.getLength());
                System.out.println("receive data: " + message);
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }

    }
}
