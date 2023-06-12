package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import commands.executor.CommandExecutor;
import exceptions.FileException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import response.Response;
import response.Status;
import util.annatations.command.CollectionDirectorAnnotation;
import util.annatations.command.Input;
import util.annatations.command.SetInCommand;
import util.arguments.ConsoleGetterArgument;
import util.arguments.FileGetterArgument;
import util.builders.CommandBuilder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static util.constants.ConstantsForCommands.*;
import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_EXECUTE_SCRIPT;
import static util.constants.ConstantsForCommandsName.NAME_EXECUTE_SCRIPT;

/**
 * Класс команды: execute_script имя_файла<p>
 * Реализует класс Command, чтобы можно было вызывать выполнение команды<p>
 * Реализует маркировочный интерфейс OneArgument, чтобы можно было проверить какие аргументы принимает команда (один аргумент - имя файла скрипта)<p>
 * Аннотация @Log создает поле логгера
 * */
@NoArgsConstructor
@AllArgsConstructor
@Log
public class ExecuteScript implements Command {
    @CollectionDirectorAnnotation
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    @Setter
    private transient HashSet<Path> namesFiles;
    @SetInCommand
    private HashMap<String, Command> commandHashMap;
    @SetInCommand
    private ArrayList<Command> listCommands;
    @Input
    private transient Path script;

    @Getter
    private ArrayList<Command> listCommandsFromExecuteScript;

    @Override
    public Response execute() {
        return new Response(Status.OK);
    }

    public List<Command> getListCommand() throws IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {

        if (namesFiles == null){
            namesFiles = new HashSet<>();
        }
        if (listCommandsFromExecuteScript == null){
            listCommandsFromExecuteScript = new ArrayList<>();
        }

        try {
            FileGetterArgument fileGetterArgument = new FileGetterArgument(script);
            while (true){
                try {
                    CommandBuilder commandBuilder = new CommandBuilder(fileGetterArgument, commandHashMap, listCommands);
                    commandBuilder.buildCommand();
                    if (commandBuilder.getCommand() instanceof ExecuteScript){
                        if (namesFiles.contains(script)){
                            throw new IllegalArgumentException(ERROR_CYCLE);
                        }
                        namesFiles.add(script);
                        ((ExecuteScript) commandBuilder.getCommand()).setNamesFiles(namesFiles);
                        ((ExecuteScript) commandBuilder.getCommand()).getListCommand(); //сгенерировать лист
                    } else {

                        listCommandsFromExecuteScript.add(commandBuilder.getCommand());
                    }
                } catch (IllegalAccessException | IllegalArgumentException e) { //Недостаточное кол-во арг и несущ. команда обрабат. тут
                    log.warning(e.getMessage());
                } catch (NoSuchElementException e){
                    //Заглушается т.к. message = null и это просто попытка считать конец документа
                } catch (FileException e){
                    log.warning(e.getMessage());
                    break;
                }
            }
        } catch (IOException e){
            log.warning(e.getMessage());
        }
        return listCommandsFromExecuteScript;
    }

    @Override
    public String name() {
        return NAME_EXECUTE_SCRIPT;
    }

    @Override
    public String description() {
        return DESCRIPTION_EXECUTE_SCRIPT;
    }
}
