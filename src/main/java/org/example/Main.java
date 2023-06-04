package org.example;

import commands.executor.CommandExecutor;
import util.arguments.WayGetArgument;
import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import util.builders.CommandBuilder;
import util.GlobalGenerate;
import util.PathGetter;

import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        CollectionDirector<PriorityQueue<Vehicle>> collectionDirector =
                new CollectionDirector<>(new PriorityQueue<>());
        ArrayList<Command> listCommands = GlobalGenerate.getListCommands();
        HashMap<String, Command> hashMap = GlobalGenerate.getMapCommands(listCommands);
        Path path = PathGetter.getPathFileCollection();
        while (true){
            try {
                CommandBuilder commandBuilder = new CommandBuilder(WayGetArgument.CONSOLE, hashMap, listCommands);
                commandBuilder.buildCommand();
                CommandExecutor commandExecutor = new CommandExecutor(collectionDirector, commandBuilder.getCommand());
                commandExecutor.executeCommand();
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}