package org.example;

import connection.Connection;

public class Main {
    public static void main(String[] args){

        Connection connection = new Connection();
        connection.connect();
        connection.interaction();

    }
}