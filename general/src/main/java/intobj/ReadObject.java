package intobj;

import commands.auxiliary.Command;
import commands.executor.CommandExecutor;
import response.Response;
import response.Status;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

public class ReadObject {
    public static Object read(ByteBuffer byteBuffer) throws IOException, ClassNotFoundException {
        byteBuffer.clear();
        ByteArrayInputStream bis = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(bis);
        Object object = objectInputStream.readObject();
        byteBuffer.clear();
        return object;
    }
}
