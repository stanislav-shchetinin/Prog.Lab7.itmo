package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.AbstractCollection;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_ADD;
import static util.constants.ConstantsForCommandsName.NAME_ADD;
@NoArgsConstructor
@AllArgsConstructor
public class Add implements Command {
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
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
