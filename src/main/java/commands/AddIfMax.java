package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.AbstractCollection;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_ADD_IF_MAX;
import static util.constants.ConstantsForCommandsName.NAME_ADD_IF_MAX;

/**
 * Класс команды добавления элемента: add_if_max {element}<p>
 * Реализует класс Command, чтобы можно было вызывать выполнение команды<p>
 * Реализует маркировочный интерфейс ElementArgument, чтобы можно было проверить какие аргументы принимает команда
 * */
@NoArgsConstructor
@AllArgsConstructor
public class AddIfMax implements Command {
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    private Vehicle vehicle;

    @Override
    public void execute() {
        collectionDirector.addIfMax(vehicle);
    }

    @Override
    public String name() {
        return NAME_ADD_IF_MAX;
    }

    @Override
    public String description() {
        return DESCRIPTION_ADD_IF_MAX;
    }
}
