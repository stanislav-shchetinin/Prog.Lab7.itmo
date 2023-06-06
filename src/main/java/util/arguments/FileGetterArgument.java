package util.arguments;

import exceptions.FileException;
import lombok.extern.java.Log;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Scanner;
@Log
public class FileGetterArgument implements GetterArgument{
    private Scanner scanner;
    public FileGetterArgument(Path path) throws IOException {
        this.scanner = new Scanner(path);
    }
    @Override
    public String getFieldArgument(Field field) throws FileException {
        if (scanner.hasNext()){
            return scanner.next();
        } else {
            throw new FileException("Файл прочитан");
        }
    }

    @Override
    public String[] getCommandArgument() throws FileException{
        String str = "";
        while (scanner.hasNext() && str.isEmpty()){
            str = scanner.nextLine();
        }
        if (str.isEmpty())
            throw new FileException("Файл прочитан");
        return str.split(" ");

    }
}
