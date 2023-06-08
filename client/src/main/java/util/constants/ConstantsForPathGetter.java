package util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstantsForPathGetter {
    public static final String NOT_FOUND_VAR_ENV = "Нет такой переменной окружения";
    public static final String GETTING_FILE_SUCCESSFUL = "Файл получен успешно";
    public static final String NOT_FOUND_FILE = "Нет файла по данному пути";
    public static final String NOT_READABLE_FILE = "Невозможно прочитать файл";
    public static final String PROMPT_TO_ENTER = "Введите имя переменной окружения:";
}
