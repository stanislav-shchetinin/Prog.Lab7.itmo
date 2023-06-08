package util.builders;

import base.Vehicle;
import commands.auxiliary.Command;
import exceptions.FileException;
import lombok.extern.java.Log;
import util.annatations.command.Input;
import util.arguments.GetterArgument;

import java.lang.reflect.Field;
import java.util.HashMap;

import static util.constants.ConstantsForCommandExecutor.*;

@Log
public class CommandInput {
    GetterArgument getterArgument;
    HashMap<String, Command> commandHashMap;

    public CommandInput(GetterArgument getterArgument, HashMap<String, Command> commandHashMap){
        this.getterArgument = getterArgument;
        this.commandHashMap = commandHashMap;
    }

    public Command inputCommand() throws IllegalArgumentException, IllegalAccessException, FileException {

        String[] args = getterArgument.getCommandArgument();
        Command command = commandHashMap.get(args[0]);

        if (command == null){
            throw  new IllegalArgumentException(NOT_FOUND_COMMAND);
        }
        int index_args = 1;
        for (Field field : command.getClass().getDeclaredFields()){
            //Всё что написано ниже перенести в отлов аннотаций
            if (!field.isAnnotationPresent(Input.class)) continue;
            if (field.getType().equals(Vehicle.class)){
                VehicleBuilder vehicleBuilder = new VehicleBuilder(getterArgument);
                vehicleBuilder.createVehicle();
                vehicleBuilder.buildVehicle();
                field.setAccessible(true);
                field.set(command, vehicleBuilder.getVehicle());
            } else {
                if (index_args > args.length - 1){
                    throw new IllegalArgumentException(NOT_ENOUGH_ARGUMENTS);
                } else {
                    FieldBuilder fieldBuilder = new FieldBuilder(args[index_args], command, field);
                    fieldBuilder.toUUID().toDouble().toPath().setField();
                    ++index_args;
                }
            }
        }

        return command;

    }
}
