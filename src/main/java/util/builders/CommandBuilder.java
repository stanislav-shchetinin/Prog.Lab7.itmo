package util.builders;

import commands.auxiliary.Command;
import lombok.Getter;
import lombok.extern.java.Log;
import util.annatations.command.SetInCommand;
import util.arguments.WayGetArgument;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import static util.constants.ConstantsForGlobal.FILE_NAME;

@Log
public class CommandBuilder {
    @Getter
    private Command command;
    private HashMap<String, Command> commandHashMap;
    private CommandInput commandInput;
    private ArrayList<Command> listCommands;
    private Path file = Path.of(FILE_NAME);

    //Подумать над тем, как распределить все объекты так, чтобы не использовать лишние при создании команды
    public CommandBuilder(WayGetArgument wayGetArgument, HashMap<String, Command> commandHashMap,
                          ArrayList<Command> listCommands){
        commandInput = new CommandInput(wayGetArgument, commandHashMap);
        this.commandHashMap = commandHashMap;
        this.listCommands = listCommands;
    }

    public void buildCommand() throws IllegalAccessException{
        command = commandInput.inputCommand();
        for (Field fieldCommand : command.getClass().getDeclaredFields()){
            if (fieldCommand.isAnnotationPresent(SetInCommand.class)){
                for (Field fieldCommandBuilder : this.getClass().getDeclaredFields()){
                    if (fieldCommand.getType().equals(fieldCommandBuilder.getType())){
                        fieldCommand.setAccessible(true);
                        fieldCommand.set(command, fieldCommandBuilder.get(this)); //если тип поля, которое нужно добавить совпадает с каким-то аргументом из строителя команд, то присвоим его
                    }
                }
            }
        }
    }
}
