package com.example.niotcp;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

public class Util {

    public static ByteBuffer string2Bytebuffer(String content) throws UnsupportedEncodingException {
        return ByteBuffer.wrap(content.getBytes("UTF-8"));
    }

    public static String byteBuffer2String(ByteBuffer buffer) throws CharacterCodingException {
        Charset charset = Charset.forName("UTF-8");
        CharBuffer charBuffer = charset.newDecoder().decode(ByteBuffer.wrap(buffer.array()));
        return charBuffer.toString();
    }
}
