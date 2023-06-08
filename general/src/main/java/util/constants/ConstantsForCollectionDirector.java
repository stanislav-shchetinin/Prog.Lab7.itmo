package util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstantsForCollectionDirector {
    public static final String REMOVE_FROM_EMPTY_COLLECTION =
            "Попытка удалить элемент из пустой коллекции";
    public static final String NO_SUCH_ID =
            "Нет элемента с таким id в коллекции";

    public static final String NOT_UNIQUE_ID =
            "Уже существует Vehicle с таким id";

}
