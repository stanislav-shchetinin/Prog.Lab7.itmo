package util.arguments.filed;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import exceptions.FileException;
import lombok.Setter;
import lombok.extern.java.Log;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.constants.ConstantsForFieldBuilder.END_ARRAY;

public class CSVGetterFieldArgument implements GetterFieldArgument, RemoveAnnotations {
    private Path path;
    private ArrayList<String> args;
    private int num_word = 0;

    public CSVGetterFieldArgument(Path path){
        this.path = path;
    }
    @Override
    public String getFieldArgument(Field field) throws FileException {
        if (num_word == 0){
            setList();
        }
        if (args.size() > num_word){
            return args.get(num_word++);
        } else {
            throw new FileException(END_ARRAY);
        }
    }

    private void setList() throws FileException {
        try (CSVReader reader = new CSVReader(new FileReader(path.toFile()))) {
            List<String[]> list = reader.readAll();
            if (!list.isEmpty()){
                list.remove(0); //удаление заголовка
            }
            args = new ArrayList<>();
            list.forEach(x -> args.addAll(Arrays.asList(x)));
        } catch (CsvException | IOException e) {
            throw new FileException(e.getMessage());
        }
    }

}
