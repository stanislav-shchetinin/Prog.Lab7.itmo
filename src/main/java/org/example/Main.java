package org.example;

import aruments.WayGetArgument;
import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import util.GlobalGenerate;
import util.builder.VehicleBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        CollectionDirector<PriorityQueue<Vehicle>> collectionDirector =
                new CollectionDirector<>(new PriorityQueue<>());
        ArrayList<Command> listCommands = GlobalGenerate.getListCommands();
        HashMap<String, Command> hashMap = GlobalGenerate.getMapCommands(listCommands);
        VehicleBuilder vehicleBuilder = new VehicleBuilder(WayGetArgument.CONSOLE);
        try {
            vehicleBuilder.buildVehicle();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.println(vehicleBuilder.getVehicle());
    }
}