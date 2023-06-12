package connection;

import lombok.extern.java.Log;
import response.Response;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Log
public class SendResponse {
    public static void send(Response response, SocketChannel client, ByteBuffer byteBuffer){
        try {
            byteBuffer.clear();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            byteBuffer.put(bytes);
            byteBuffer.flip();

            while (byteBuffer.hasRemaining()) {
                client.write(byteBuffer);
            }
            byteBuffer.clear();
        } catch (Exception e){
            log.warning(e.getMessage());
        }

    }
}
