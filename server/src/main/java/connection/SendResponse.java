package connection;

import intobj.SendObject;
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
            SendObject.send(response, client, byteBuffer);
        } catch (Exception e){
            log.warning(e.getMessage());
        }

    }
}
