package org.example;

import commands.executor.CommandExecutor;
import exceptions.FileException;
import response.Response;
import util.GlobalGenerate;
import util.PathGetter;
import util.arguments.ConsoleGetterArgument;
import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import util.arguments.filed.CSVGetterFieldArgument;
import util.builders.CommandBuilder;
import util.builders.VehicleBuilder;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.util.*;

public class Main {

    private static final String HOSTNAME = "localhost";
    private static final int PORT = 4782;

    public static void main(String[] args) throws Exception{

        ArrayList<Command> listCommand = GlobalGenerate.getListCommands();
        HashMap<String, Command> commandHashMap = GlobalGenerate.getMapCommands(listCommand);

        ByteBuffer buf = ByteBuffer.allocate(1024);
        SocketChannel client = SocketChannel.open(new InetSocketAddress(HOSTNAME, PORT));
        client.configureBlocking(true);

        while (true) {
            buf.clear();
            CommandBuilder commandBuilder = new CommandBuilder(new ConsoleGetterArgument(), commandHashMap, listCommand);
            commandBuilder.buildCommand();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(commandBuilder.getCommand());
            byte[] bytes = byteArrayOutputStream.toByteArray();
            buf.put(bytes);
            buf.flip();

            while (buf.hasRemaining()) {
                client.write(buf);
            }

            buf.clear();
            int numBytesRead = client.read(buf);
            if (numBytesRead == -1) {
                break;
            }

            buf.flip();

            ByteArrayInputStream bis = new ByteArrayInputStream(buf.array());
            ObjectInputStream ois = new ObjectInputStream(bis);
            Response response = (Response) ois.readObject();

            if (response.getMessage() != null && response.getMessage() != ""){
                System.out.println(response.getMessage());
            }

        }
        client.close();

    }
}