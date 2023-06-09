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

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_CLEAR;
import static util.constants.ConstantsForCommandsName.NAME_CLEAR;

@NoArgsConstructor
@AllArgsConstructor
public class Clear implements Command {
    @CollectionDirectorAnnotation
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;

    @Override
    public Response execute() {
        collectionDirector.clear();
        return new Response(Status.OK);
    }

    @Override
    public String name() {
        return NAME_CLEAR;
    }

    @Override
    public String description() {
        return DESCRIPTION_CLEAR;
    }
}
