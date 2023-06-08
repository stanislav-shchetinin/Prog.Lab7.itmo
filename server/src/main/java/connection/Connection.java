package connection;

import base.Vehicle;
import lombok.extern.java.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

@Log
public class Connection {

    private static int FIRST_PORT = 5432;
    private static int MAX_NUMBER_PORT = 64000;
    private static int MIN_NUMBER_PORT = 1001;
    private int port;
    private ByteBuffer byteBuffer;
    private Selector selector;
    private ServerSocketChannel server;

    public Connection(){
        this.port = FIRST_PORT;
        this.byteBuffer = ByteBuffer.allocate(32000);
    }

    public void connect(){
        while (true){
            try {
                if (selector == null)
                    selector = Selector.open();
                if (server == null)
                    server = ServerSocketChannel.open();
                server.bind(new InetSocketAddress(port));
                server.configureBlocking(false);
                server.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println(String.format("Port: %d", port));
                break;
            } catch (IOException e){
                log.info(String.format("Port %d занят", port));
                port = (port + 1)%MAX_NUMBER_PORT;
                port = Math.max(port, MIN_NUMBER_PORT);
            }
        }


    }

    public void interaction(ReadingObject readingObject){
        while (true) {
            int numSelectedKeys = 0;
            try {
                numSelectedKeys = selector.selectNow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (numSelectedKeys == 0) {
                continue;
            }

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectedKeys.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();

                if (key.isAcceptable()) {
                    clientConnect();
                } else if (key.isReadable()) {
                    clientReadable(key, readingObject);
                }
            }
        }
    }

    private void clientConnect(){
        try {
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
            System.out.println("Connected: " + client.getRemoteAddress());
        } catch (IOException e){
            log.warning(e.getMessage());
        }
    }

    private void clientReadable(SelectionKey key, ReadingObject readingObject){
        try {
            SocketChannel client = (SocketChannel) key.channel();
            byteBuffer.clear();
            int numBytesRead = client.read(byteBuffer);
            if (numBytesRead == -1) {
                System.out.println("Disconnected: " + client.getRemoteAddress());
                client.close();
                return;
            }
            byteBuffer.flip();

            int bytesRead = client.read(byteBuffer);
            byte[] data = byteBuffer.array();

            // Десериализуем объект
            ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
            readingObject.start(byteBuffer, objectStream);


            byteBuffer.clear();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

}
