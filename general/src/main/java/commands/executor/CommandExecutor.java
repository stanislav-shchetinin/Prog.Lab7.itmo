package commands.executor;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import response.Response;
import util.annatations.command.CollectionDirectorAnnotation;

import java.lang.reflect.Field;
import java.util.AbstractCollection;

@AllArgsConstructor
public class CommandExecutor {

    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    private Command command;
    public Response executeCommand() throws IllegalAccessException {
        for (Field field : command.getClass().getDeclaredFields()){
            if (field.isAnnotationPresent(CollectionDirectorAnnotation.class)){
                field.setAccessible(true);
                field.set(command, collectionDirector);
            }
        }
        return command.execute();
    }

}
