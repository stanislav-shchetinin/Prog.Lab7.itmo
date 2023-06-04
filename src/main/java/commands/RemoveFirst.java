package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import exceptions.CollectionException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import util.annatations.command.CollectionDirectorAnnotation;
import util.annatations.command.SetInCommand;

import java.util.AbstractCollection;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_REMOVE_FIRST;
import static util.constants.ConstantsForCommandsName.NAME_REMOVE_FIRST;

/**
 * Класс команды: remove_first<p>
 * Реализует класс Command, чтобы можно было вызывать выполнение команды<p>
 * Реализует маркировочный интерфейс NoArgument, чтобы можно было проверить какие аргументы принимает команда (без аргументов)
 * */
@NoArgsConstructor
@AllArgsConstructor
@Log
public class RemoveFirst implements Command {
    @CollectionDirectorAnnotation
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;

    @Override
    public void execute() {
        try {
            collectionDirector.removeFirst();
        } catch (CollectionException e){
            log.warning(e.getMessage());
        }
    }

    @Override
    public String description() {
        return DESCRIPTION_REMOVE_FIRST;
    }

    @Override
    public String name() {
        return NAME_REMOVE_FIRST;
    }
}
