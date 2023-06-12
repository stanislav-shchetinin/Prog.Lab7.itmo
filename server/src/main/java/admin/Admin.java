package admin;

import lombok.extern.java.Log;

import java.util.NoSuchElementException;
import java.util.Scanner;

@Log
public class Admin extends Thread{
    @Override
    public void run(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            try {
                if (scanner.next().trim().equalsIgnoreCase("exit")){
                    exit();
                }
            } catch (NoSuchElementException e){
                exit();
            }
        }
    }
    private void exit(){
        log.info("Вызвана команда exit: сервер завершает работу");
        System.exit(0);
    }
}
