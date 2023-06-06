package commands;

import base.Vehicle;
import collection.CollectionDirector;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import util.annatations.command.CollectionDirectorAnnotation;
import util.annatations.command.SetInCommand;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

        if (!Files.isWritable(fileSave)){
            log.warning("В этот файл нельзя записывать");
        } else {
            Charset charset = StandardCharsets.US_ASCII;
            try (BufferedWriter writer = Files.newBufferedWriter(fileSave, charset)) {
                writer.write(String.format("%s\n",collectionDirector.getHeadCSV()));
                for (Vehicle vehicle : collectionDirector.getCollection()){
                    writer.write(String.format("%s\n", vehicle.formatCSV));
                }
            } catch (IOException e) {
                log.warning(e.getMessage());
            }
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
