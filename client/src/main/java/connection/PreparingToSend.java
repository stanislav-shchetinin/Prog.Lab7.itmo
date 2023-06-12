package connection;

import commands.Add;
import commands.ExecuteScript;
import commands.auxiliary.Command;
import exceptions.FileException;
import lombok.extern.java.Log;
import response.Response;
import response.Status;
import util.arguments.ConsoleGetterArgument;
import util.builders.CommandBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
@Log
public class PreparingToSend {
    public static List<Command> getCommands(ArrayList<Command> listCommands, HashMap<String, Command> commandHashMap)
            throws FileException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        CommandBuilder commandBuilder = new CommandBuilder(new ConsoleGetterArgument(), commandHashMap, listCommands);
        commandBuilder.buildCommand();

        if (commandBuilder.getCommand() instanceof ExecuteScript){
            try {
                List<Command> list = ((ExecuteScript) commandBuilder.getCommand()).getListCommand();
                return list;
            } catch (IllegalArgumentException e){
                log.warning(e.getMessage());
                return new ArrayList<>();
            }
        } else {
            return Arrays.asList(commandBuilder.getCommand());
        }

    }
}
