package org.example;

import commands.executor.CommandExecutor;
import exceptions.FileException;
import util.GlobalGenerate;
import util.PathGetter;
import util.arguments.ConsoleGetterArgument;
import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import util.arguments.filed.CSVGetterFieldArgument;
import util.builders.CommandBuilder;
import util.builders.VehicleBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.util.*;

public class Main {

    private static final String HOSTNAME = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) throws Exception{

        ByteBuffer buf = ByteBuffer.allocate(32000);
        SocketChannel client = SocketChannel.open(new InetSocketAddress(HOSTNAME, PORT));
        client.configureBlocking(false);

        VehicleBuilder vehicleBuilder = new VehicleBuilder(new ConsoleGetterArgument());

        while (true) {
            vehicleBuilder.createVehicle();
            vehicleBuilder.buildVehicle();
            Vehicle vehicle = vehicleBuilder.getVehicle();
            buf.clear();

            try(ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)){
                objectStream.writeObject(vehicle);
                byte[] data = byteStream.toByteArray();

                buf.put(data);
                buf.flip();

                while (buf.hasRemaining()) {
                    client.write(buf);
                }
                System.out.println("Sent: " + vehicle);
            }


            buf.clear();
            int numBytesRead = client.read(buf);
            if (numBytesRead == -1) {
                break;
            }
            /*String receivedMessage = new String(buf.array(), 0, numBytesRead);
            System.out.println("Received: " + receivedMessage);*/
            Thread.sleep(2000);
        }
        client.close();

        /*CollectionDirector<PriorityQueue<Vehicle>> collectionDirector =
                new CollectionDirector<>(new PriorityQueue<>());

        ArrayList<Command> listCommands = GlobalGenerate.getListCommands();
        HashMap<String, Command> hashMap = GlobalGenerate.getMapCommands(listCommands);
        Path path = PathGetter.getPathFileCollection();

        CSVGetterFieldArgument csvGetterFieldArgument = new CSVGetterFieldArgument(path);
        VehicleBuilder vehicleBuilder = new VehicleBuilder(csvGetterFieldArgument);
        try {
            while (true){
                vehicleBuilder.createVehicle();
                vehicleBuilder.buildVehicle();
                Vehicle vehicle = vehicleBuilder.getVehicle();
                collectionDirector.add(vehicle);
            }
        } catch (FileException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        //execute_script src/main/java/files/script

        while (true){
            try {
                CommandBuilder commandBuilder = new CommandBuilder(new ConsoleGetterArgument(), hashMap, listCommands);
                commandBuilder.buildCommand();
                CommandExecutor commandExecutor = new CommandExecutor(collectionDirector, commandBuilder.getCommand());
                commandExecutor.executeCommand();
            } catch (IllegalAccessException | IllegalArgumentException | FileException e) { //Недостаточное кол-во арг и несущ. команда обрабат. тут
                System.out.println(e.getMessage());
            }
        }*/

    }
}