package org.example;

import base.Vehicle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class Main {

    private static final int PORT = 12345;

    public static void main(String[] args) throws Exception{

        ByteBuffer buf = ByteBuffer.allocateDirect(1024);

        Selector selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(PORT));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int numSelectedKeys = selector.selectNow();
            if (numSelectedKeys == 0) {
                continue;
            }

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectedKeys.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();

                if (key.isAcceptable()) {
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    System.out.println("Connected: " + client.getRemoteAddress());
                } else if (key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    buf.clear();
                    int numBytesRead = client.read(buf);
                    if (numBytesRead == -1) {
                        System.out.println("Disconnected: " + client.getRemoteAddress());
                        client.close();
                        continue;
                    }
                    buf.flip();

                    int bytesRead = client.read(buf);
                    byte[] data = buf.array();

                    // Десериализуем объект
                    try(ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
                    ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {
                        Vehicle vehicle = (Vehicle) objectStream.readObject();
                        System.out.println("Recive: " + vehicle);
                    }

                    buf.clear();
                }
            }
        }

    }
}