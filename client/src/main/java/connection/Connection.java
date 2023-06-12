package connection;

import commands.auxiliary.Command;
import exceptions.FileException;
import lombok.extern.java.Log;
import util.GlobalGenerate;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.*;

import static util.constants.ConstantsForConnection.*;
@Log
public class Connection {
    private int port = 0;
    private ByteBuffer byteBuffer;
    private ArrayList<Command> listCommands;
    private HashMap<String, Command> commandHashMap;
    private SocketChannel client;

    public Connection(){
        this.byteBuffer = ByteBuffer.allocate(CAPACITY_BYTE_BUFFER);
        this.listCommands = GlobalGenerate.getListCommands();
        this.commandHashMap = GlobalGenerate.getMapCommands(listCommands);
    }

    public void interaction() {
        while (true) {
            try {
                List<Command> commandList = PreparingToSend.getCommands(listCommands, commandHashMap);
                for (Command command : commandList){
                    SendCommand.send(byteBuffer, client, command);
                    ReadingResponse.read(byteBuffer, client);
                    //execute_script client/src/main/java/files/script_demp
                }
            } catch (FileException | IOException | ClassNotFoundException | IllegalAccessException |
                    IllegalArgumentException e) {
               log.warning(e.getMessage());
            }

        }
    }

    public void connect(){
        inputPort();
        while (true){
            try {
                Thread.sleep(1000);
                this.client = SocketChannel.open(new InetSocketAddress(port));
                client.configureBlocking(true);
                log.info("Успешное подключение к серверу! Great! Amazing! Awesome!");
                return;
            } catch (IOException e){
                System.out.println("Подключение...");
            } catch (InterruptedException e){
                log.warning(e.getMessage());
            }
        }

    }

    private void inputPort(){
        while (port == 0){
            System.out.println("Введите порт: ");
            Scanner scanner = new Scanner(System.in);

            try {
                port = scanner.nextInt();
            } catch (InputMismatchException e){
                log.warning("Номер порта является числом");
                continue;
            }

            if (port > MAX_NUMBER_PORT || port < MIN_NUMBER_PORT){
                System.out.printf("Порт должен принимать значение от %d до %d%n",
                        MIN_NUMBER_PORT, MAX_NUMBER_PORT);
                port = 0;
            }
        }
    }

}
