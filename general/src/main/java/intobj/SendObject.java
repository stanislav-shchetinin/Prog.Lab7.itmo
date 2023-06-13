package intobj;

import response.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SendObject {
    public static void send(Object object, SocketChannel client, ByteBuffer byteBuffer) throws IOException {
        byteBuffer.clear();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        byteBuffer.put(bytes);
        byteBuffer.flip();

        while (byteBuffer.hasRemaining()) {
            client.write(byteBuffer);
        }
        byteBuffer.clear();
    }
}
