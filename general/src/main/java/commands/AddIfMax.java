package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import response.Response;
import response.Status;
import util.annatations.command.CollectionDirectorAnnotation;
import util.annatations.command.Input;
import util.annatations.command.SetInCommand;

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
    @CollectionDirectorAnnotation
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    @Input
    private Vehicle vehicle;

    @Override
    public Response execute() {
        collectionDirector.addIfMax(vehicle);
        return new Response(Status.OK);
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
