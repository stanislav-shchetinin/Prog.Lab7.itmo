package commands;

import base.Vehicle;
import collection.CollectionDirector;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import util.annatations.command.CollectionDirectorAnnotation;
import util.annatations.command.SetInCommand;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_SAVE;
import static util.constants.ConstantsForCommandsName.NAME_SAVE;

/**
 * Класс команды: remove_first<p>
 * Реализует класс Command, чтобы можно было вызывать выполнение команды<p>
 * Реализует маркировочный интерфейс NoArgument, чтобы можно было проверить какие аргументы принимает команда (без аргументов)<p>
 * Аннотация @Log создает поле логгера
 * */
@NoArgsConstructor
@AllArgsConstructor
@Log
public class Save implements Command {
    @CollectionDirectorAnnotation
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    @SetInCommand
    private Path fileSave;
    @Override
    public void execute() {
        CsvMapper mapper = CsvMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        List<String> names = new ArrayList<>();
        CsvSchema schema = CsvSchema.builder().setUseHeader(true)
                .addColumn("id")
                .addColumn("name")
                .addColumn("x")
                .addColumn("y")
                .addColumn("time")
                .build();

        ObjectWriter writer = mapper.writerFor(Vehicle.class).with(schema);

        try {
            writer.writeValues(fileSave.toFile()).writeAll(collectionDirector.getCollection().toArray());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String description() {
        return DESCRIPTION_SAVE;
    }

    @Override
    public String name() {
        return NAME_SAVE;
    }
}
