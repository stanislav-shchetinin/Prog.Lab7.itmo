package commands;

import commands.auxiliary.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import response.Response;
import response.Status;
import util.annatations.command.SetInCommand;

import java.util.ArrayList;

import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_HELP;
import static util.constants.ConstantsForCommandsName.NAME_HELP;

/**
 * Класс команды вывода всех команд с описанием: help<p>
 * Реализует класс Command, чтобы можно было вызывать выполнение команды<p>
 * Реализует маркировочный интерфейс NoArgument, чтобы можно было проверить какие аргументы принимает команда (без аргументов)<p>
 * */
@NoArgsConstructor
@AllArgsConstructor
public class Help implements Command {
    @SetInCommand
    ArrayList<Command> listCommands;

    @Override
    public Response execute() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : listCommands){
            stringBuilder.append(command.description());
        }
        return new Response(Status.OK, stringBuilder.toString());
    }

    @Override
    public String name() {
        return NAME_HELP;
    }

    @Override
    public String description() {
        return DESCRIPTION_HELP;
    }
}
