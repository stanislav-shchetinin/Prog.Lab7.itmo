package connection;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import commands.executor.CommandExecutor;
import lombok.extern.java.Log;
import response.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
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

    private ReadingObject readingCommand;

    public Connection(ReadingObject readingCommand){
        this.port = FIRST_PORT;
        this.byteBuffer = ByteBuffer.allocate(65536);
        this.readingCommand = readingCommand;
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

    public void interaction() throws IOException {

            while (true) {

                if (selector.selectNow() == 0) {
                    continue;
                }

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                iterateSelectionKey(it);
            }

    }

    private void iterateSelectionKey(Iterator<SelectionKey> it){
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                if (key.isAcceptable()) {
                    clientAccept();
                } else if (key.isReadable()) {
                    takeCommand(key);
                }
                //break;
            }
    }

    private void clientAccept() {
        try {
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
            System.out.println("Connected: " + client.getRemoteAddress());
        } catch (IOException e){
            log.warning(e.getMessage());
        }

    }

    private void takeCommand(SelectionKey key){

        try {
            SocketChannel client = (SocketChannel) key.channel();
            byteBuffer.clear();

            if (key.isReadable()){
                int numBytesRead = client.read(byteBuffer);
                if (numBytesRead == -1) {
                    System.out.println("Disconnected: " + client.getRemoteAddress());
                    client.close();
                    return;
                }

                Response response = readingCommand.start(byteBuffer);
                SendResponse.send(response, client, byteBuffer);
            }

        } catch (SocketException e){
            //Глушу, потому что иначе пока не подключиться клиент, будет выполняться тело отловки этого иск.
            //Лучше найти способ как этой обойти без перехватывания исключений (возможно каким-то if'ом)
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
