package org.example;

import com.opencsv.exceptions.CsvException;
import commands.executor.CommandExecutor;
import exceptions.FileException;
import util.annatations.vehicle.CheckIt;
import util.arguments.ConsoleGetterArgument;
import util.arguments.WayGetArgument;
import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import util.arguments.filed.CSVGetterFieldArgument;
import util.builders.CommandBuilder;
import util.GlobalGenerate;
import util.PathGetter;
import util.builders.VehicleBuilder;

import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        CollectionDirector<PriorityQueue<Vehicle>> collectionDirector =
                new CollectionDirector<>(new PriorityQueue<>());

        ArrayList<Command> listCommands = GlobalGenerate.getListCommands();
        HashMap<String, Command> hashMap = GlobalGenerate.getMapCommands(listCommands);
        Path path = PathGetter.getPathFileCollection();

        CSVGetterFieldArgument csvGetterFieldArgument = new CSVGetterFieldArgument();
        csvGetterFieldArgument.setPath(path);
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

        while (true){
            try {
                CommandBuilder commandBuilder = new CommandBuilder(WayGetArgument.CONSOLE, hashMap, listCommands);
                commandBuilder.buildCommand();
                CommandExecutor commandExecutor = new CommandExecutor(collectionDirector, commandBuilder.getCommand());
                commandExecutor.executeCommand();
            } catch (IllegalAccessException | IllegalArgumentException | FileException e) { //Недостаточное кол-во арг и несущ. команда обрабат. тут
                System.out.println(e.getMessage());
            }
        }

    }
}