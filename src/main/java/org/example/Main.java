package org.example;

import aruments.WayGetArgument;
import base.Vehicle;
import collection.CollectionDirector;
import commands.Add;
import commands.auxiliary.Command;
import commands.executor.CommandInput;
import util.GlobalGenerate;
import util.builder.VehicleBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        CollectionDirector<PriorityQueue<Vehicle>> collectionDirector =
                new CollectionDirector<>(new PriorityQueue<>());
        ArrayList<Command> listCommands = GlobalGenerate.getListCommands();
        HashMap<String, Command> hashMap = GlobalGenerate.getMapCommands(listCommands);
        //В командах пометить аннотациями поля, которые нужны для .execute(), чтобы они сами вводились при выборе команды
        VehicleBuilder vehicleBuilder = new VehicleBuilder(WayGetArgument.CONSOLE);
        vehicleBuilder.createVehicle();
        vehicleBuilder.buildVehicle();
        System.out.println(vehicleBuilder.getVehicle());
        CommandInput commandInput = new CommandInput(WayGetArgument.CONSOLE, hashMap);
        //  через reflection api интегрировать для command executor аргументы (commandDirector и т.д.)
        try {
            commandInput.inputCommand();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}