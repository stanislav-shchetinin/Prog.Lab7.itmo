package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.nio.file.Path;
import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.HashSet;

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
public class ExecuteScript implements Command {
    private Path script;
    private Path fileForSave;
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    private HashSet<String> namesFiles;
    private HashMap<String, Command> mapCommand;

    @Override
    public void execute() {
        if (namesFiles == null){
            namesFiles = new HashSet<>();
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
