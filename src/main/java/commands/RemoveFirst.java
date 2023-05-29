package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import exceptions.CollectionException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

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
        return NAME_REMOVE_FIRST;
    }

    @Override
    public String name() {
        return DESCRIPTION_REMOVE_FIRST;
    }
}
