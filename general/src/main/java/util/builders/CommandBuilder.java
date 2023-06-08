package util.builders;

import commands.auxiliary.Command;
import exceptions.FileException;
import lombok.Getter;
import util.annatations.command.SetInCommand;
import util.arguments.GetterArgument;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import static util.constants.ConstantsForGlobal.FILE_SAVE_NAME;

public class CommandBuilder implements Serializable {
    @Getter
    private Command command;
    private HashMap<String, Command> commandHashMap;
    private CommandInput commandInput;
    private ArrayList<Command> listCommands;
    private Path fileSave;

    //Подумать над тем, как распределить все объекты так, чтобы не использовать лишние при создании команды
    public CommandBuilder(GetterArgument getterArgument, HashMap<String, Command> commandHashMap,
                          ArrayList<Command> listCommands){
        commandInput = new CommandInput(getterArgument, commandHashMap);
        this.commandHashMap = commandHashMap;
        this.listCommands = listCommands;
        this.fileSave = Path.of(FILE_SAVE_NAME);
    }

    public void buildCommand() throws IllegalAccessException, FileException {
        command = commandInput.inputCommand();
        for (Field fieldCommand : command.getClass().getDeclaredFields()){
            if (fieldCommand.isAnnotationPresent(SetInCommand.class)){
                for (Field fieldCommandBuilder : this.getClass().getDeclaredFields()){
                    if (fieldCommand.getName().equals(fieldCommandBuilder.getName())){
                        fieldCommand.setAccessible(true);
                        fieldCommand.set(command, fieldCommandBuilder.get(this)); //если имя поля из строителя == имя поля из команды, то в команду уст. знач. поля из билдера
                    }
                }
            }
        }
    }
}
