package connection;

import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

public interface ReadingObject {
    void start(ByteBuffer byteBuffer, ObjectInputStream objectInputStream);
}
