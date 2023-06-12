package connection;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import commands.executor.CommandExecutor;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import response.Response;
import response.Status;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.AbstractCollection;
import java.util.Arrays;

@Log
@AllArgsConstructor
public class ReadingCommand implements ReadingObject{

    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    @Override
    public Response start(ByteBuffer byteBuffer) {
        while (true){
            try {
                byteBuffer.clear();
                ByteArrayInputStream bis = new ByteArrayInputStream(byteBuffer.array());
                ObjectInputStream objectInputStream = new ObjectInputStream(bis);
                Command command = (Command) objectInputStream.readObject();
                CommandExecutor commandExecutor = new CommandExecutor(collectionDirector, command);
                Response response = commandExecutor.executeCommand();
                byteBuffer.clear();
                return response;
            } catch (Exception e) {
                return new Response(Status.ERROR, e.getMessage());
            }
        }
    }
}
