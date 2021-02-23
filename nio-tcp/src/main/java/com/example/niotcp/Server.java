package com.example.niotcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

    private static final int PORT = 8999;
    private static final int TIMEOUT = 3000;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {

        Selector selector = null;
        ServerSocketChannel socketChannel = null;

        try {
            selector = Selector.open();

            socketChannel = ServerSocketChannel.open();

            socketChannel.socket().bind(new InetSocketAddress(PORT));
            System.out.println("bind port " + PORT);

            socketChannel.configureBlocking(false);

            socketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("register OP_ACCEPT");

            Handler handler = new HandlerImpl(BUFFER_SIZE);

            while (true) {

                if (selector.select(TIMEOUT) == 0) {
                    // todo other thing
                    continue;
                }

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();

                    if (!selectionKey.isValid()) {
                        iterator.remove();
                        continue;
                    }

                    // accept
                    if (selectionKey.isAcceptable()) {
                        handler.handleAccept(selectionKey);
                    }

                    if (selectionKey.isReadable()) {
                        handler.handleRead(selectionKey);
                    }

                    if (selectionKey.isWritable()) {
                        handler.handleWrite(selectionKey);
                    }

                }

                selectionKeys.clear();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
