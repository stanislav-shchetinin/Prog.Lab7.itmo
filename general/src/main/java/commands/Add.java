package commands;

import base.Vehicle;
import collection.CollectionDirector;
import lombok.Getter;
import response.Response;
import response.Status;
import util.annatations.command.CollectionDirectorAnnotation;
import util.annatations.command.Input;
import commands.auxiliary.Command;
import lombok.NoArgsConstructor;

import java.util.AbstractCollection;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_ADD;
import static util.constants.ConstantsForCommandsName.NAME_ADD;
@NoArgsConstructor
public class Add implements Command {
    @CollectionDirectorAnnotation
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    @Input
    @Getter
    private Vehicle vehicle;
    @Override
    public Response execute() {
        collectionDirector.add(vehicle);
        return new Response(Status.OK);
    }

    @Override
    public String name() {
        return NAME_ADD;
    }

    @Override
    public String description() {
        return DESCRIPTION_ADD;
    }
}
