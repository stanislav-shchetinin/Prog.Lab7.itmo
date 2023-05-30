package commands;

import base.Vehicle;
import collection.CollectionDirector;
import util.annatations.command.Input;
import commands.auxiliary.Command;
import lombok.NoArgsConstructor;
import util.annatations.command.SetInCommand;

import java.util.AbstractCollection;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_ADD;
import static util.constants.ConstantsForCommandsName.NAME_ADD;
@NoArgsConstructor
public class Add implements Command {
    @SetInCommand
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    @Input
    private Vehicle vehicle;
    @Override
    public void execute() {
        collectionDirector.add(vehicle);
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
