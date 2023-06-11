package connection;

import commands.auxiliary.Command;
import exceptions.FileException;
import util.arguments.ConsoleGetterArgument;
import util.builders.CommandBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;

public class SendCommand {
    private ArrayList<Command> listCommand;
    private HashMap<String, Command> commandHashMap;

    public SendCommand(ArrayList<Command> listCommand, HashMap<String, Command> commandHashMap){
        this.listCommand = listCommand;
        this.commandHashMap = commandHashMap;
    }
    public void send(ByteBuffer byteBuffer, SocketChannel client) throws FileException, IllegalAccessException, IOException {
        byteBuffer.clear();
        CommandBuilder commandBuilder = new CommandBuilder(new ConsoleGetterArgument(), commandHashMap, listCommand);
        commandBuilder.buildCommand();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(commandBuilder.getCommand());
        byte[] bytes = byteArrayOutputStream.toByteArray();
        byteBuffer.put(bytes);
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            client.write(byteBuffer);
        }
    }
}
