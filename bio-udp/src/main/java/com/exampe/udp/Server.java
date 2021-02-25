package com.exampe.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {

    private static final int PORT = 4444;

    public static void main(String[] args) {

        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket(PORT);
            System.out.println("server start at: " + socket.getLocalAddress() + ":" + socket.getLocalPort());
            byte[] buf = new byte[1024];

            while (true) {

                DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);

                // 阻塞等待接收客户端数据
                socket.receive(receivePacket);

                byte[] data = receivePacket.getData();
                String message = new String(data, 0, receivePacket.getLength());

                InetAddress address = receivePacket.getAddress();
                int port = receivePacket.getPort();
                System.out.println("receive client " + address + ":" + port + " data :" + message);

                DatagramPacket sendPacket = new DatagramPacket(data, data.length, address, port);
                socket.send(sendPacket);

                System.out.println("sent client " + address + ":" + port + " data :" + message);

            }

        } catch (SocketException e) {
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
