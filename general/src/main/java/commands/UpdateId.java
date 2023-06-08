package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import response.Response;
import response.Status;
import util.annatations.command.CollectionDirectorAnnotation;
import util.annatations.command.Input;
import util.annatations.command.SetInCommand;

import java.util.AbstractCollection;
import java.util.UUID;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_UNIQUE_ENGINE_POWER;
import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_UPDATE_ID;
import static util.constants.ConstantsForCommandsName.NAME_UPDATE_ID;

/**
 * Класс команды: update_bu_id id {element}<p>
 * Реализует класс Command, чтобы можно было вызывать выполнение команды<p>
 * Реализует маркировочный интерфейс ElementArgument (т.к. один из аргументов - объект класса Vehicle) и OneArgument (id)<p>
 * Аннотация @Log создает поле логгера
 * */
@AllArgsConstructor
@NoArgsConstructor
@Log
public class UpdateId implements Command {
    @CollectionDirectorAnnotation
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    @Input
    private UUID id;
    @Input
    private Vehicle vehicle;

    @Override
    public Response execute() {
        try {
            collectionDirector.updateById(vehicle, id);
            return new Response(Status.OK);
        } catch (IllegalArgumentException e){
            return new Response(Status.ERROR, e.getMessage());
        }
    }

    @Override
    public String description() {
        return DESCRIPTION_UPDATE_ID;
    }

    @Override
    public String name() {
        return NAME_UPDATE_ID;
    }
}
