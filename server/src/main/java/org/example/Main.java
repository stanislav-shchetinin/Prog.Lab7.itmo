package org.example;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import commands.executor.CommandExecutor;
import connection.Connection;
import connection.ReadingCommand;
import connection.ReadingObject;
import connection.ReadingVehicle;
import response.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception{

        CollectionDirector<PriorityQueue<Vehicle>> collectionDirector =
                new CollectionDirector<>(new PriorityQueue<>());

        ByteBuffer buf = ByteBuffer.allocate(1024);

        Selector selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(4782));
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
                    System.out.println(client.isConnected());
                    buf.clear();
                    int numBytesRead = client.read(buf);
                    if (numBytesRead == -1) {
                        System.out.println("Disconnected: " + client.getRemoteAddress());
                        client.close();
                        continue;
                    }

                    ByteArrayInputStream bis = new ByteArrayInputStream(buf.array());
                    ObjectInputStream ois = new ObjectInputStream(bis);
                    Command command = (Command) ois.readObject();

                    CommandExecutor commandExecutor = new CommandExecutor(collectionDirector, command);
                    Response response = commandExecutor.executeCommand();

                    buf.clear();
                    buf.flip();

                    ByteBuffer buf1 = ByteBuffer.allocate(1024);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(response);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    buf1.put(bytes);
                    buf1.flip();

                    while (buf1.hasRemaining()) {
                        client.write(buf1);
                    }
                    buf1.clear();

                }
                break;
            }
        }
    }
}