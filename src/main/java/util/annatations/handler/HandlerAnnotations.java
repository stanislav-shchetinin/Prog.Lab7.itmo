package util.annatations.handler;

import lombok.AllArgsConstructor;
import util.annatations.*;

import javax.management.ObjectName;
import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.UUID;

import static util.constants.ConstantsForFieldBuilder.*;

@AllArgsConstructor
public class HandlerAnnotations {
    Field field;
    Object parent;
    boolean isNotInput;
    Object value;

    public void handleAllAnnotations() throws IllegalAccessException {
        handlerTimeCurrentGenerate();
        handlerIdAutoGenerate();
        handleMaxValue();
        handleMinValue();
        handleNotZero();
        handlePositiveNumber();
        handleNotNull();
    }

    private void handlerIdAutoGenerate() throws IllegalArgumentException, IllegalAccessException {
        if (field.isAnnotationPresent(IdAutoGenerate.class) && isNotInput){
            if (field.getType().equals(UUID.class)){
                field.set(parent, UUID.randomUUID());
            } else {
                throw new IllegalArgumentException(ERROR_UUID_TYPE);
            }

        }
    }

    private void handlerTimeCurrentGenerate() throws IllegalArgumentException, IllegalAccessException {
        if (field.isAnnotationPresent(TimeCurrentGenerate.class) && isNotInput){
            if (field.getType().equals(ZonedDateTime.class)){
                field.set(parent, ZonedDateTime.now());
            } else {
                throw new IllegalArgumentException(ERROR_DATE_TYPE);
            }

        }
    }

    private void handleMaxValue() {
        if (!field.isAnnotationPresent(MaxValue.class)) return;
        if (Number.class.isAssignableFrom(field.getType())){
            MaxValue maxValue = field.getAnnotation(MaxValue.class);
            Number number = (Number) value;
            if (number.doubleValue() > maxValue.value()){
                throw new IllegalArgumentException(ERROR_MAX_VALUE);
            }
        } else {
            throw new IllegalArgumentException(ERROR_MAX_VALUE_ANNOTATION);
        }
    }
    private void handleMinValue() {
        if (!field.isAnnotationPresent(MinValue.class)) return;
        if (Number.class.isAssignableFrom(field.getType())){
            MinValue minValue = field.getAnnotation(MinValue.class);
            Number number = (Number) value;
            if (number.doubleValue() < minValue.value()){
                throw new IllegalArgumentException(ERROR_MIN_VALUE);
            }
        } else {
            throw new IllegalArgumentException(ERROR_MIN_VALUE_ANNOTATION);
        }
    }

    private void handleNotZero() {
        if (!field.isAnnotationPresent(NotZero.class)) return;
        if (Number.class.isAssignableFrom(field.getType())){
            Number number = (Number) value;
            if (number.doubleValue() == 0){
                throw new IllegalArgumentException(ERROR_NOT_ZERO);
            }
        } else {
            throw new IllegalArgumentException(ERROR_NOT_ZERO_ANNOTATION);
        }
    }

    private void handlePositiveNumber() {
        if (!field.isAnnotationPresent(PositiveNumber.class)) return;
        if (Number.class.isAssignableFrom(field.getType())){
            Number number = (Number) value;
            if (number.doubleValue() < 0){
                throw new IllegalArgumentException(ERROR_NOT_POSITIVE);
            }
        } else {
            throw new IllegalArgumentException(ERROR_NOT_POSITIVE_ANNOTATION);
        }
    }
    private void handleNotNull() {
        if (!field.isAnnotationPresent(NotNull.class)) return;
        if (value == null){
            throw  new IllegalArgumentException(ERROR_NOT_NULL);
        }
    }

}

