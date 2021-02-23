package com.example.niotcp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;

public class HandlerImpl implements Handler {

    private int bufferSize;

    HandlerImpl(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void handleAccept(SelectionKey selectionKey) {

        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();

        SocketChannel socketChannel = null;
        try {
            socketChannel = serverSocketChannel.accept();

            socketChannel.configureBlocking(false);

            socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));

            System.out.println("accept client " + socketChannel.getLocalAddress());

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public void handleRead(SelectionKey selectionKey) {

        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();

        byteBuffer.clear();

        try {
            int byteLen = socketChannel.read(byteBuffer);

            if (byteLen == -1) {
                socketChannel.close();
            } else if (byteLen > 0) {

                String message = Util.byteBuffer2String(byteBuffer);
                System.out.println("receive message: " + message);

                selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socketChannel.socket().close();
                socketChannel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    public void handleWrite(SelectionKey selectionKey) {

        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        // 把收到地再发回去
        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
        byteBuffer.flip();

        try {
            socketChannel.write(byteBuffer);

            if (!byteBuffer.hasRemaining()) {
                selectionKey.interestOps(SelectionKey.OP_READ);
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
