package connection;

import response.Response;

import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

public interface ReadingObject {
    Response start(ByteBuffer byteBuffer);
}
