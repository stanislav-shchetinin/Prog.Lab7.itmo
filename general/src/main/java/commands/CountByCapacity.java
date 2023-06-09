package commands;

import base.Vehicle;
import collection.CollectionDirector;
import response.Response;
import response.Status;
import util.annatations.command.CollectionDirectorAnnotation;
import util.annatations.command.Input;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import util.annatations.command.SetInCommand;

import java.util.AbstractCollection;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_COUNT_BY_CAPACITY;
import static util.constants.ConstantsForCommandsName.NAME_COUNT_BY_CAPACITY;

/**
 * Класс команды: count_by_capacity capacity<p>
 * Реализует класс Command, чтобы можно было вызывать выполнение команды<p>
 * Реализует маркировочный интерфейс OneArgument, чтобы можно было проверить какие аргументы принимает команда (один аргумент - capacity)<p>
 * Аннотация @Log создает поле логгера
 * */
@NoArgsConstructor
@AllArgsConstructor
public class CountByCapacity implements Command {
    @CollectionDirectorAnnotation
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    @Input
    private Double capacity;

    @Override
    public Response execute() {
        return new Response(Status.OK, collectionDirector.countByCapacity(capacity));
    }

    @Override
    public String name() {
        return NAME_COUNT_BY_CAPACITY;
    }

    @Override
    public String description() {
        return DESCRIPTION_COUNT_BY_CAPACITY;
    }
}
