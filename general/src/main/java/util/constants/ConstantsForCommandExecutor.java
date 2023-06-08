package util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstantsForCommandExecutor {
    public static final String NOT_FOUND_COMMAND = "Не существует команды с таким именем";
    public static final String NOT_ENOUGH_ARGUMENTS = "Недостаточно аргументов";
}
