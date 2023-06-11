package connection;

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

        ByteArrayInputStream bis = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream ois = new ObjectInputStream(bis);
        Response response = (Response) ois.readObject();

        if (response.getMessage() != null && !response.getMessage().equals("")){
            System.out.println(response.getMessage());
        }
    }
}
