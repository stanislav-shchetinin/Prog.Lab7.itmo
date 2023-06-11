package connection;

import java.nio.ByteBuffer;

public class Connection {
    private int port;
    private ByteBuffer byteBuffer;

    public Connection(){
        this.byteBuffer = ByteBuffer.allocate(655);
    }

    public void connect(){

    }

}
