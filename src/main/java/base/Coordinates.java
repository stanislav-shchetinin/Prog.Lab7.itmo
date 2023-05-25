package base;

import annatations.MaxValue;
import annatations.MinValue;
import annatations.NotNull;

import static util.constants.ConstantsForBase.MAX_VALUE_FOR_COORDINATE_X;
import static util.constants.ConstantsForBase.MIN_VALUE_FOR_COORDINATE_Y;

public class Coordinates {

    @NotNull
    @MaxValue(MAX_VALUE_FOR_COORDINATE_X)
    private Double x; //Максимальное значение поля: 455, Поле не может быть null

    @MinValue(MIN_VALUE_FOR_COORDINATE_Y)
    private Long y; //Значение поля должно быть больше -762
}