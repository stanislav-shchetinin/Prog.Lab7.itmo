package connection;

import intobj.ReadObject;
import response.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ReadingResponse {
    public static void read(ByteBuffer byteBuffer, SocketChannel client) throws IOException, ClassNotFoundException {
        byteBuffer.clear();
        int numBytesRead = client.read(byteBuffer);
        if (numBytesRead == -1) {
            return;
        }

        Response response = (Response) ReadObject.read(byteBuffer);

        if (response.getMessage() != null && !response.getMessage().equals("")){
            System.out.println(response.getMessage());
        }
    }
}
