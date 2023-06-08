package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import util.annatations.command.CollectionDirectorAnnotation;
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
@Log
public class RemoveById implements Command {
    @CollectionDirectorAnnotation
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    @Input
    private UUID id;

    @Override
    public void execute() {
        try {
            collectionDirector.removeById(id);
        } catch (IllegalArgumentException e){
            log.warning(e.getMessage());
        }
    }

    @Override
    public String description() {
        return DESCRIPTION_REMOVE_BY_ID;
    }

    @Override
    public String name() {
        return NAME_REMOVE_BY_ID;
    }

}
