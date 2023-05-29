package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.AbstractCollection;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_UNIQUE_ENGINE_POWER;
import static util.constants.ConstantsForCommandsName.NAME_PRINT_UNIQUE_ENGINE_POWER;

/**
 * Класс команды: print_unique_engine_power<p>
 * Реализует класс Command, чтобы можно было вызывать выполнение команды<p>
 * Реализует маркировочный интерфейс NoArgument, чтобы можно было проверить какие аргументы принимает команда (без аргументов)
 * */
@NoArgsConstructor
@AllArgsConstructor
public class PrintUniqueEnginePower implements Command {
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;

    @Override
    public void execute() {
        System.out.println(collectionDirector.printUniqueEnginePower());
    }

    @Override
    public String description() {
        return NAME_PRINT_UNIQUE_ENGINE_POWER;
    }

    @Override
    public String name() {
        return DESCRIPTION_UNIQUE_ENGINE_POWER;
    }
}
