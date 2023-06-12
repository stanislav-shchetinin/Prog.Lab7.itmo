package org.example;

import base.Vehicle;
import collection.CollectionDirector;
import connection.Connection;
import connection.ReadingCommand;
import connection.ReadingObject;
import java.util.PriorityQueue;

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