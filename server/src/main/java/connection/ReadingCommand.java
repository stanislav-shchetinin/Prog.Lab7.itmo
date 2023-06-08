package connection;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import commands.executor.CommandExecutor;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.AbstractCollection;
import java.util.Arrays;

@Log
@AllArgsConstructor
public class ReadingCommand implements ReadingObject{

    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    @Override
    public void start(ByteBuffer byteBuffer, ObjectInputStream objectInputStream) {
        while (true){
            try {
                Command command = (Command) objectInputStream.readObject();
                CommandExecutor commandExecutor = new CommandExecutor(collectionDirector, command);
                commandExecutor.executeCommand();
            } catch (Exception e) {
                System.out.println(Arrays.stream(e.getStackTrace()).toList());
                log.warning(e.getMessage());
                break;
            }
        }
    }
}
