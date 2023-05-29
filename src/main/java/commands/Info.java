package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.AbstractCollection;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_INFO;
import static util.constants.ConstantsForCommandsName.NAME_INFO;

/**
 * Класс команды информации о коллекции: info<p>
 * Реализует класс Command, чтобы можно было вызывать выполнение команды<p>
 * Реализует маркировочный интерфейс NoArgument, чтобы можно было проверить какие аргументы принимает команда (без аргументов)
 * */
@NoArgsConstructor
@AllArgsConstructor
public class Info implements Command {

    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;

    @Override
    public void execute() {
        System.out.println(collectionDirector.info());
    }

    @Override
    public String name() {
        return NAME_INFO;
    }

    @Override
    public String description() {
        return DESCRIPTION_INFO;
    }
}
