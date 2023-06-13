package connection;

import commands.ExecuteScript;
import commands.Help;
import commands.auxiliary.Command;
import exceptions.FileException;
import intobj.SendObject;
import response.Response;
import response.Status;
import util.arguments.ConsoleGetterArgument;
import util.builders.CommandBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SendCommand {

    public static void send(ByteBuffer byteBuffer, SocketChannel client, Command command)
            throws IOException {

        SendObject.send(command, client, byteBuffer);

    }



}
