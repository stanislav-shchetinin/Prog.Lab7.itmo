package base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import util.annatations.vehicle.CheckIt;
import util.annatations.vehicle.MaxValue;
import util.annatations.vehicle.MinValue;
import util.annatations.vehicle.NotNull;

import java.io.Serializable;

import static util.constants.ConstantsForBase.MAX_VALUE_FOR_COORDINATE_X;
import static util.constants.ConstantsForBase.MIN_VALUE_FOR_COORDINATE_Y;
@ToString
public class Coordinates implements Serializable {

    @NotNull
    @CheckIt
    @MaxValue(MAX_VALUE_FOR_COORDINATE_X)
    private Double x; //Максимальное значение поля: 455, Поле не может быть null

    @NotNull
    @CheckIt
    @MinValue(MIN_VALUE_FOR_COORDINATE_Y)
    private Long y; //Значение поля должно быть больше -762
}