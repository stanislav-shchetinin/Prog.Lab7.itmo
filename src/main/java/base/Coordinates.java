package base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import util.annatations.*;

import static util.constants.ConstantsForBase.MAX_VALUE_FOR_COORDINATE_X;
import static util.constants.ConstantsForBase.MIN_VALUE_FOR_COORDINATE_Y;
@ToString
public class Coordinates {

    @NotNull
    @Input
    @MaxValue(MAX_VALUE_FOR_COORDINATE_X)
    private Double x; //Максимальное значение поля: 455, Поле не может быть null

    @NotNull
    @Input
    @MinValue(MIN_VALUE_FOR_COORDINATE_Y)
    private Long y; //Значение поля должно быть больше -762
}