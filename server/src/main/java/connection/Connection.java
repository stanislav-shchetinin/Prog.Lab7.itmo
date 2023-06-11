package connection;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import commands.executor.CommandExecutor;
import lombok.extern.java.Log;
import response.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.AbstractCollection;
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
        this.byteBuffer = ByteBuffer.allocate(1024);
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

    public void interaction(ReadingObject readingCommand) throws IOException {
        while (true) {
            int numSelectedKeys = selector.selectNow();
            if (numSelectedKeys == 0) {
                continue;
            }

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectedKeys.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();

                if (key.isAcceptable()) {
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    System.out.println("Connected: " + client.getRemoteAddress());
                } else if (key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    System.out.println(client.isConnected());
                    byteBuffer.clear();
                    int numBytesRead = client.read(byteBuffer);
                    if (numBytesRead == -1) {
                        System.out.println("Disconnected: " + client.getRemoteAddress());
                        client.close();
                        continue;
                    }

                    Response response = readingCommand.start(byteBuffer);

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

                }
                break;
            }
        }

    }

}
