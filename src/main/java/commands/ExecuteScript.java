package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import commands.executor.CommandExecutor;
import exceptions.FileException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import util.annatations.command.CollectionDirectorAnnotation;
import util.annatations.command.Input;
import util.annatations.command.SetInCommand;
import util.arguments.ConsoleGetterArgument;
import util.arguments.FileGetterArgument;
import util.builders.CommandBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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
    private HashSet<Path> namesFiles;
    @SetInCommand
    private HashMap<String, Command> commandHashMap;
    @SetInCommand
    private ArrayList<Command> listCommands;
    @Input
    private Path script;
    @SetInCommand
    private Path fileSave;

    public static final String ERROR_NOT_FILE = "Нет файла по указанному пути";
    public static final String ERROR_NOT_READABLE_FILE = "Не получается считать файл";

    @Override
    public void execute() {
        if (!Files.isRegularFile(fileSave)){
            log.warning(ERROR_NOT_FILE);
        }
        if (!Files.isReadable(fileSave)){
            log.warning(ERROR_NOT_READABLE_FILE);
        }
        if (namesFiles == null){
            namesFiles = new HashSet<>();
        }
        try {
            FileGetterArgument fileGetterArgument = new FileGetterArgument(script);
            while (true){
                try {
                    CommandBuilder commandBuilder = new CommandBuilder(fileGetterArgument, commandHashMap, listCommands);
                    commandBuilder.buildCommand();
                    if (commandBuilder.getCommand() instanceof ExecuteScript){
                        if (namesFiles.contains(fileSave)){
                            log.warning("Цикл");
                            break;
                        }
                        namesFiles.add(fileSave);
                        ((ExecuteScript) commandBuilder.getCommand()).setNamesFiles(namesFiles);
                    }
                    CommandExecutor commandExecutor = new CommandExecutor(collectionDirector, commandBuilder.getCommand());
                    commandExecutor.executeCommand();
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
