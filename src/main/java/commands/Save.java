package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import util.annatations.command.CollectionDirectorAnnotation;
import util.annatations.command.SetInCommand;

import java.nio.file.Path;
import java.util.AbstractCollection;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_SAVE;
import static util.constants.ConstantsForCommandsName.NAME_SAVE;

/**
 * Класс команды: remove_first<p>
 * Реализует класс Command, чтобы можно было вызывать выполнение команды<p>
 * Реализует маркировочный интерфейс NoArgument, чтобы можно было проверить какие аргументы принимает команда (без аргументов)<p>
 * Аннотация @Log создает поле логгера
 * */
@NoArgsConstructor
@AllArgsConstructor
@Log
public class Save implements Command {
    @CollectionDirectorAnnotation
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    @SetInCommand
    private Path file;

    @Override
    public void execute() {

    }

    @Override
    public String description() {
        return DESCRIPTION_SAVE;
    }

    @Override
    public String name() {
        return NAME_SAVE;
    }
}
