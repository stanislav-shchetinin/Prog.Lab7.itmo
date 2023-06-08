package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import exceptions.CollectionException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import response.Response;
import response.Status;
import util.annatations.command.CollectionDirectorAnnotation;
import util.annatations.command.SetInCommand;

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
    @CollectionDirectorAnnotation
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;

    @Override
    public Response execute() {
        return new Response(Status.OK, collectionDirector.show());
    }

    @Override
    public String description() {
        return DESCRIPTION_SHOW;
    }

    @Override
    public String name() {
        return NAME_SHOW;
    }
}
