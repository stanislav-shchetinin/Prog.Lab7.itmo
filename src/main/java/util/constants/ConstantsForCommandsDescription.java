package util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstantsForCommandsDescription {
    public static final String DESCRIPTION_ADD =
            "add {element} : добавить новый элемент в коллекцию";
    public static final String DESCRIPTION_ADD_IF_MAX =
            "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    public static final String DESCRIPTION_ADD_IF_MIN =
            "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    public static final String DESCRIPTION_CLEAR =
            "clear : очистить коллекцию";
    public static final String DESCRIPTION_COUNT_BY_CAPACITY =
            "count_by_capacity capacity : вывести количество элементов, значение поля capacity которых равно заданному";
    public static final String DESCRIPTION_EXECUTE_SCRIPT =
            "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме";
    public static final String DESCRIPTION_EXIT =
            "exit : завершить программу (без сохранения в файл)";
    public static final String DESCRIPTION_HELP =
            "help : вывести справку по доступным командам";
    public static final String DESCRIPTION_INFO =
            "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    public static final String DESCRIPTION_PRINT_ASCENDING =
            "print_ascending : вывести элементы коллекции в порядке возрастания";
    public static final String DESCRIPTION_UNIQUE_ENGINE_POWER =
            "print_unique_engine_power : вывести уникальные значения поля enginePower всех элементов в коллекции";
    public static final String DESCRIPTION_REMOVE_BY_ID =
            "remove_by_id id : удалить элемент из коллекции по его id";
    public static final String DESCRIPTION_REMOVE_FIRST =
            "remove_first : удалить первый элемент из коллекции";
    public static final String DESCRIPTION_SAVE =
            "save : сохранить коллекцию в файл";
    public static final String DESCRIPTION_SHOW =
            "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    public static final String DESCRIPTION_UPDATE_ID =
            "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
}
