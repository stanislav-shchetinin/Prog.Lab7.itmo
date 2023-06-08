package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import response.Response;
import response.Status;
import util.annatations.command.CollectionDirectorAnnotation;
import util.annatations.command.SetInCommand;

import java.util.AbstractCollection;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_UNIQUE_ENGINE_POWER;
import static util.constants.ConstantsForCommandsName.NAME_PRINT_UNIQUE_ENGINE_POWER;

/**
 * Класс команды: print_unique_engine_power<p>
 * Реализует класс Command, чтобы можно было вызывать выполнение команды<p>
 * Реализует маркировочный интерфейс NoArgument, чтобы можно было проверить какие аргументы принимает команда (без аргументов)
 * */
@NoArgsConstructor
@AllArgsConstructor
public class PrintUniqueEnginePower implements Command {
    @CollectionDirectorAnnotation
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;

    @Override
    public Response execute() {
        return new Response(Status.OK, collectionDirector.printUniqueEnginePower());
    }

    @Override
    public String description() {
        return DESCRIPTION_UNIQUE_ENGINE_POWER;
    }

    @Override
    public String name() {
        return NAME_PRINT_UNIQUE_ENGINE_POWER;
    }
}
