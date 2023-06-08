package commands;

import commands.auxiliary.Command;
import lombok.extern.java.Log;
import response.Response;
import response.Status;

import static util.constants.ConstantsForCommands.INFO_EXIT;
import static util.constants.ConstantsForCommandsDescription.DESCRIPTION_EXIT;
import static util.constants.ConstantsForCommandsName.NAME_EXIT;

/**
 * Класс завершения программы: exit<p>
 * Реализует класс Command, чтобы можно было вызывать выполнение команды<p>
 * Реализует маркировочный интерфейс NoArgument, чтобы можно было проверить какие аргументы принимает команда (без аргументов)<p>
 * Аннотация @Log создает поле логгера
 * */
@Log
public class Exit implements Command {

    @Override
    public Response execute() {
        log.info(INFO_EXIT);
        System.exit(0);
        return new Response(Status.OK);
    }

    @Override
    public String name() {
        return NAME_EXIT;
    }

    @Override
    public String description() {
        return DESCRIPTION_EXIT;
    }
}
