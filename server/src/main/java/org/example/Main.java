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

        ReadingObject readingCommand = new ReadingCommand(collectionDirector);
        Connection connection = new Connection(readingCommand);
        connection.connect();
        connection.interaction();

    }
}