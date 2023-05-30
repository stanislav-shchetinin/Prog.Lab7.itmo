package commands.executor;

import base.Vehicle;
import collection.CollectionDirector;

import java.util.AbstractCollection;

public class CommandExecutor {
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
}
