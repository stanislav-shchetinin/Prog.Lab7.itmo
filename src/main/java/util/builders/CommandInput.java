package util.builders;

import util.arguments.FactoryGettersArgument;
import util.arguments.GetterArgument;
import util.arguments.WayGetArgument;
import base.Vehicle;
import util.annatations.command.Input;
import commands.auxiliary.Command;
import lombok.extern.java.Log;

import java.lang.reflect.Field;
import java.util.HashMap;

import static util.constants.ConstantsForCommandExecutor.*;

@Log
public class CommandInput {
    GetterArgument getterArgument;
    HashMap<String, Command> commandHashMap;

    public CommandInput(WayGetArgument wayGetArgument, HashMap<String, Command> commandHashMap){
        getterArgument = new FactoryGettersArgument(wayGetArgument).getGetterArgument();
        this.commandHashMap = commandHashMap;
    }

    public Command inputCommand() throws IllegalArgumentException, IllegalAccessException {

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
                    fieldBuilder.toUUID().toDouble().setField();
                    ++index_args;
                }
            }
        }

        return command;

    }
}
