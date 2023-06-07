package util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstantsForFieldBuilder {
    public static final String ERROR_HANDLER_UUID_TYPE = "Поле с аннотацией IdAutoGenerate должно реализовывать класс UUID";
    public static final String ERROR_HANDLER_DATE_TYPE = "Поле с аннотацией TimeCurrentGenerate должно реализовывать класс ZonedDateTime";
    public static final String ERROR_DOUBLE_TYPE = "Переданное значение не является double";
    public static final String ERROR_LONG_TYPE = "Переданное значение не является long";
    public static final String ERROR_VEHICLE_TYPE_TYPE = "Переданное значение не является VehicleType";
    public static final String ERROR_UUID_TYPE = "Переданное значение не является UUID";
    public static final String ERROR_ZONED_DATA_TIME_TYPE = "Переданное значение не является ZonedDataTime";
    public static final String ERROR_MAX_VALUE_ANNOTATION = "Аннотацию MaxValue могут иметь только Number";
    public static final String ERROR_MIN_VALUE_ANNOTATION = "Аннотацию MinValue могут иметь только Number";
    public static final String ERROR_NOT_ZERO_ANNOTATION = "Аннотацию NotZero могут иметь только Number";
    public static final String ERROR_NOT_POSITIVE_ANNOTATION = "Аннотацию NotPositive могут иметь только Number";
    public static final String ERROR_HANDLER_MAX_VALUE = "Значение больше максимального";
    public static final String ERROR_HANDLER_MIN_VALUE = "Значение меньше минимального";
    public static final String ERROR_HANDLER_NOT_ZERO = "Значение равно 0";
    public static final String ERROR_HANDLER_NOT_POSITIVE = "Значение меньше 0";
    public static final String ERROR_HANDLER_NOT_NULL = "Значение null";
    public static final String END_ARRAY = "Файл прочитан";
}
