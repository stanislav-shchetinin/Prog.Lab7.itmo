package org.example;

import admin.Admin;
import base.Vehicle;
import collection.CollectionDirector;
import connection.Connection;
import connection.ReadingCommand;
import connection.ReadingObject;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        CollectionDirector<PriorityQueue<Vehicle>> collectionDirector =
                new CollectionDirector<>(new PriorityQueue<>());

        new Admin().start();

        ReadingObject readingCommand = new ReadingCommand(collectionDirector);
        Connection connection = new Connection(readingCommand);
        connection.connect();
        connection.interaction();

    }
}