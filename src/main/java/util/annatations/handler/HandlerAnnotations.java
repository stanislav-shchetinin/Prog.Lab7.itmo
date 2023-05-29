package util.annatations.handler;

import lombok.AllArgsConstructor;
import util.annatations.*;

import javax.management.ObjectName;
import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.UUID;

import static util.constants.ConstantsForFieldBuilder.*;

@AllArgsConstructor
public class HandlerAnnotations {
    Field field;
    Object parent;
    boolean isNotInput;
    Object value;

    public HandlerAnnotations handlerIdAutoGenerate() throws IllegalArgumentException, IllegalAccessException {
        if (field.isAnnotationPresent(IdAutoGenerate.class) && isNotInput){
            if (field.getType().equals(UUID.class)){
                field.set(parent, UUID.randomUUID());
            } else {
                throw new IllegalArgumentException(ERROR_UUID_TYPE);
            }
        }
        return this;
    }

    public HandlerAnnotations handlerTimeCurrentGenerate() throws IllegalArgumentException, IllegalAccessException {
        if (field.isAnnotationPresent(TimeCurrentGenerate.class) && isNotInput){
            if (field.getType().equals(ZonedDateTime.class)){
                field.set(parent, ZonedDateTime.now());
            } else {
                throw new IllegalArgumentException(ERROR_DATE_TYPE);
            }
        }
        return this;
    }

    public HandlerAnnotations handleMaxValue() {
        if (!field.isAnnotationPresent(MaxValue.class)) return this;
        if (Number.class.isAssignableFrom(field.getType())){
            MaxValue maxValue = field.getAnnotation(MaxValue.class);
            Number number = (Number) value;
            if (number.doubleValue() > maxValue.value()){
                throw new IllegalArgumentException(ERROR_MAX_VALUE);
            }
        } else {
            throw new IllegalArgumentException(ERROR_MAX_VALUE_ANNOTATION);
        }
        return this;
    }
    public HandlerAnnotations handleMinValue() {
        if (!field.isAnnotationPresent(MinValue.class)) return this;
        if (Number.class.isAssignableFrom(field.getType())){
            MinValue minValue = field.getAnnotation(MinValue.class);
            Number number = (Number) value;
            if (number.doubleValue() < minValue.value()){
                throw new IllegalArgumentException(ERROR_MIN_VALUE);
            }
        } else {
            throw new IllegalArgumentException(ERROR_MIN_VALUE_ANNOTATION);
        }
        return this;
    }

    public HandlerAnnotations handleNotZero() {
        if (!field.isAnnotationPresent(NotZero.class)) return this;
        if (Number.class.isAssignableFrom(field.getType())){
            Number number = (Number) value;
            if (number.doubleValue() == 0){
                throw new IllegalArgumentException(ERROR_NOT_ZERO);
            }
        } else {
            throw new IllegalArgumentException(ERROR_NOT_ZERO_ANNOTATION);
        }
        return this;
    }

    public HandlerAnnotations handlePositiveNumber() {
        if (!field.isAnnotationPresent(PositiveNumber.class)) return this;
        if (Number.class.isAssignableFrom(field.getType())){
            Number number = (Number) value;
            if (number.doubleValue() < 0){
                throw new IllegalArgumentException(ERROR_NOT_POSITIVE);
            }
        } else {
            throw new IllegalArgumentException(ERROR_NOT_POSITIVE_ANNOTATION);
        }
        return this;
    }
    public HandlerAnnotations handleNotNull() {
        if (value == null && !field.isAnnotationPresent(NotNull.class)){
            throw  new IllegalArgumentException(ERROR_NOT_NULL);
        }
        return this;
    }

}

