package com.example.niotcp;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface Handler {

    void handleAccept(SelectionKey selectionKey);

    void handleRead(SelectionKey selectionKey);

    void handleWrite(SelectionKey selectionKey);
}
