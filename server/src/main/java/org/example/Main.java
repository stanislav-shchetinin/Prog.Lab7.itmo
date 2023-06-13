package org.example;

import admin.Admin;
import base.Vehicle;
import collection.CollectionDirector;
import connection.Connection;
import connection.ReadingCommand;
import connection.ReadingObject;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {

        /*Properties info = new Properties();
        info.load(new FileInputStream("server/src/main/java/db/db.cfg"));
        String jdbcURL = "jdbc:postgresql://localhost:4782/studs";
        java.sql.Connection connectionDataBase = DriverManager.getConnection(jdbcURL, info);*/

        CollectionDirector<PriorityQueue<Vehicle>> collectionDirector =
                new CollectionDirector<>(new PriorityQueue<>());

        new Admin().start();

        ReadingObject readingCommand = new ReadingCommand(collectionDirector);
        Connection connection = new Connection(readingCommand);
        connection.connect();
        connection.interaction();

    }
}