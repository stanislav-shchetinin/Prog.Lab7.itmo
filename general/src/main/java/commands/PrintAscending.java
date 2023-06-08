package commands;

import base.Vehicle;
import collection.CollectionDirector;
import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import util.annatations.command.CollectionDirectorAnnotation;
import util.annatations.command.SetInCommand;

import java.util.AbstractCollection;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_PRINT_ASCENDING;
import static util.constants.ConstantsForCommandsName.NAME_PRINT_ASCENDING;

/**
 * Класс команды: print_ascending<p>
 * Реализует класс Command, чтобы можно было вызывать выполнение команды<p>
 * Реализует маркировочный интерфейс NoArgument, чтобы можно было проверить какие аргументы принимает команда (без аргументов)
 * */
@NoArgsConstructor
@AllArgsConstructor
public class PrintAscending implements Command {
    @CollectionDirectorAnnotation
    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;

    @Override
    public void execute() {
        System.out.println(collectionDirector.printAscending());
    }

    @Override
    public String description() {
        return DESCRIPTION_PRINT_ASCENDING;
    }

    @Override
    public String name() {
        return NAME_PRINT_ASCENDING;
    }
}
