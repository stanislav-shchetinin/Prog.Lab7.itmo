package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import util.annatations.command.Input;
import util.annatations.command.SetInCommand;

import java.util.AbstractCollection;
import java.util.UUID;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_REMOVE_BY_ID;
import static util.constants.ConstantsForCommandsName.NAME_REMOVE_BY_ID;

/**
 * Класс команды: remove_by_id id<p>
 * Реализует класс Command, чтобы можно было вызывать выполнение команды<p>
 * Реализует маркировочный интерфейс OneArgument, чтобы можно было проверить какие аргументы принимает команда (один аргумент - id)<p>
 * Аннотация @Log создает поле логгера
 * */
@NoArgsConstructor
@AllArgsConstructor
public class RemoveById implements Command {
    @SetInCommand
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    @Input
    private UUID id;

    @Override
    public void execute() {
        collectionDirector.removeById(id);
    }

    @Override
    public String description() {
        return NAME_REMOVE_BY_ID;
    }

    @Override
    public String name() {
        return DESCRIPTION_REMOVE_BY_ID;
    }

}
