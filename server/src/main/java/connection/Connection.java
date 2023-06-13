package connection;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import commands.executor.CommandExecutor;
import lombok.AllArgsConstructor;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.constants.ConstantsForConnection.*;

@Log
public class Connection {

    private int port;
    private ByteBuffer byteBuffer;
    private Selector selector;
    private ServerSocketChannel server;

    private ReadingCommand readingCommand;

    private ExecutorService executorService;

    public Connection(ReadingCommand readingCommand){
        this.port = FIRST_PORT;
        this.byteBuffer = ByteBuffer.allocate(CAPACITY_BYTE_BUFFER);
        this.readingCommand = readingCommand;
        this.executorService = Executors.newFixedThreadPool(10);
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

    public void interaction() {

            while (true) {
                try {
                    if (selector.selectNow() == 0) {
                        continue;
                    }
                } catch (IOException e){
                    log.warning(e.getMessage());
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                iterateSelectionKey(it);
            }

    }

    private void iterateSelectionKey(Iterator<SelectionKey> it){
            while (it.hasNext()) {
                //executorService.submit(() -> {
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isAcceptable()) {
                        clientAccept();
                    } else if (key.isReadable()) {
                        takeCommand(key);
                    }
                //});
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

                //executorService.submit(() -> {
                    Response response = readingCommand.start(byteBuffer);
                    SendResponse.send(response, client, byteBuffer);
                //});

            }

        } catch (SocketException e){
            //Глушу, потому что иначе пока не подключиться клиент, будет выполняться тело отловки этого иск.
            //Лучше найти способ как этой обойти без перехватывания исключений (возможно каким-то if'ом)
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
