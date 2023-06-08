package util.annatations.vehicle.handler;

import lombok.AllArgsConstructor;
import util.annatations.vehicle.*;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.UUID;

import static util.constants.ConstantsForFieldBuilder.*;
/**
 * Отлавливает валидационные аннотации, поэтому есть смысл сделать в формате builder
 * */
@AllArgsConstructor
public class HandlerVehicleAnnotations {
    private Field field;
    private Object parent;
    private boolean isNotInput;
    private Object value;

    public HandlerVehicleAnnotations handlerIdAutoGenerate() throws IllegalArgumentException, IllegalAccessException {
        if (field.isAnnotationPresent(IdAutoGenerate.class) && isNotInput){
            if (field.getType().equals(UUID.class)){
                field.set(parent, UUID.randomUUID());
            } else {
                throw new IllegalArgumentException(ERROR_HANDLER_UUID_TYPE);
            }
        }
        return this;
    }

    public HandlerVehicleAnnotations handlerTimeCurrentGenerate() throws IllegalArgumentException, IllegalAccessException {
        if (field.isAnnotationPresent(TimeCurrentGenerate.class) && isNotInput){
            if (field.getType().equals(ZonedDateTime.class)){
                field.set(parent, ZonedDateTime.now());
            } else {
                throw new IllegalArgumentException(ERROR_HANDLER_DATE_TYPE);
            }
        }
        return this;
    }

    public HandlerVehicleAnnotations handleMaxValue() {
        if (!field.isAnnotationPresent(MaxValue.class)) return this;
        if (Number.class.isAssignableFrom(field.getType())){
            MaxValue maxValue = field.getAnnotation(MaxValue.class);
            Number number = (Number) value;
            if (number.doubleValue() > maxValue.value()){
                throw new IllegalArgumentException(ERROR_HANDLER_MAX_VALUE);
            }
        } else {
            throw new IllegalArgumentException(ERROR_MAX_VALUE_ANNOTATION);
        }
        return this;
    }
    public HandlerVehicleAnnotations handleMinValue() {
        if (!field.isAnnotationPresent(MinValue.class)) return this;
        if (Number.class.isAssignableFrom(field.getType())){
            MinValue minValue = field.getAnnotation(MinValue.class);
            Number number = (Number) value;
            if (number.doubleValue() < minValue.value()){
                throw new IllegalArgumentException(ERROR_HANDLER_MIN_VALUE);
            }
        } else {
            throw new IllegalArgumentException(ERROR_MIN_VALUE_ANNOTATION);
        }
        return this;
    }

    public HandlerVehicleAnnotations handleNotZero() {
        if (!field.isAnnotationPresent(NotZero.class)) return this;
        if (Number.class.isAssignableFrom(field.getType())){
            Number number = (Number) value;
            if (number.doubleValue() == 0){
                throw new IllegalArgumentException(ERROR_HANDLER_NOT_ZERO);
            }
        } else {
            throw new IllegalArgumentException(ERROR_NOT_ZERO_ANNOTATION);
        }
        return this;
    }

    public HandlerVehicleAnnotations handlePositiveNumber() {
        if (!field.isAnnotationPresent(PositiveNumber.class)) return this;
        if (Number.class.isAssignableFrom(field.getType())){
            Number number = (Number) value;
            if (number.doubleValue() < 0){
                throw new IllegalArgumentException(ERROR_HANDLER_NOT_POSITIVE);
            }
        } else {
            throw new IllegalArgumentException(ERROR_NOT_POSITIVE_ANNOTATION);
        }
        return this;
    }
    public HandlerVehicleAnnotations handleNotNull() {
        if (value == null && !field.isAnnotationPresent(NotNull.class)){
            throw  new IllegalArgumentException(ERROR_HANDLER_NOT_NULL);
        }
        return this;
    }

}

