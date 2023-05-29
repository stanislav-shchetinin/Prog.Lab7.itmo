package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import exceptions.CollectionException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.AbstractCollection;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_SHOW;
import static util.constants.ConstantsForCommandsName.NAME_SHOW;

/**
 * Класс команды: show<p>
 * Реализует класс Command, чтобы можно было вызывать выполнение команды<p>
 * Реализует маркировочный интерфейс NoArgument, чтобы можно было проверить какие аргументы принимает команда (без аргументов)<p>
 * */
@NoArgsConstructor
@AllArgsConstructor
public class Show implements Command {
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;

    @Override
    public void execute() {
        System.out.println(collectionDirector.show());
    }

    @Override
    public String description() {
        return NAME_SHOW;
    }

    @Override
    public String name() {
        return DESCRIPTION_SHOW;
    }
}
