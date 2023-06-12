package connection;

import commands.ExecuteScript;
import commands.auxiliary.Command;
import exceptions.FileException;
import lombok.extern.java.Log;
import response.Response;
import response.Status;
import util.arguments.ConsoleGetterArgument;
import util.builders.CommandBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
@Log
public class PreparingToSend {
    public static List<Command> getCommands(ArrayList<Command> listCommands, HashMap<String, Command> commandHashMap)
            throws FileException, IllegalAccessException {
        CommandBuilder commandBuilder = new CommandBuilder(new ConsoleGetterArgument(), commandHashMap, listCommands);
        commandBuilder.buildCommand();

        

        if (commandBuilder.getCommand() instanceof ExecuteScript){
            Response response = commandBuilder.getCommand().execute(); //execute_script не выполняется.
            // Он просто перебирает все команды в файле
            if (response.getStatus().equals(Status.ERROR)){
                log.warning(response.getMessage());
            }
            return ((ExecuteScript) commandBuilder.getCommand()).getListCommandsFromExecuteScript();
        } else {
            return Arrays.asList(commandBuilder.getCommand());
        }

    }
}
