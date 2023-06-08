package util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstantsForCommands {
    public static final String ERROR_NOT_FILE = "Нет файла по указанному пути";
    public static final String ERROR_NOT_READABLE_FILE = "Не получается считать файл";
    public static final String ERROR_CYCLE = "Файлы зациклились";
    public static final String INFO_EXIT = "Вызвана команда exit. Программа завершает выполнение";
    public static final String ERROR_NOT_WRITABLE = "Запись в этот файл запрещена";
}
