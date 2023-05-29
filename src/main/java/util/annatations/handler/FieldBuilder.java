package util.annatations.handler;

import aruments.GetterArgument;
import base.Vehicle;
import base.VehicleType;
import util.annatations.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.ZonedDateTime;
import java.util.UUID;

import static util.constants.ConstantsForFieldBuilder.*;

public class FieldBuilder {
    Object parent;
    Field field;
    GetterArgument getterArgument;

    Object value;
    boolean isNotInput;

    public FieldBuilder(GetterArgument getterArgument, Object parent, Field field){
        this.getterArgument = getterArgument;
        this.parent = parent;
        this.field = field;
        field.setAccessible(true);
    }

    public void buildField() throws IllegalAccessException, IllegalArgumentException {
        setNotInput();
        handlerTimeCurrentGenerate();
        handlerIdAutoGenerate();
        inputField();
        toDouble();
        toLong();
        toVehicleType();
        handleMaxValue();
        handleMinValue();
        handleNotZero();
        handlePositiveNumber();
        //handleNotNull();
        setField();
    }

    private void setNotInput() {
        isNotInput = field.isAnnotationPresent(NotInput.class);
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

    private void inputField(){
        if (isNotInput) return;
        value = getterArgument.getArgument(field);
    }

    private void toDouble(){
        if (!field.getType().equals(Double.class)) return;
        try {
            value = Double.parseDouble((String) value);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException(ERROR_DOUBLE_TYPE);
        }
    }
    private void toLong(){
        if (!field.getType().equals(Long.class)) return;
        try {
            value = Long.parseLong((String) value);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException(ERROR_LONG_TYPE);
        }
    }
    private void toVehicleType(){
        if (!field.getType().equals(VehicleType.class)) return;
        try {
            value = VehicleType.valueOf((String) value);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException(ERROR_VEHICLE_TYPE);
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
    private void setField() throws IllegalAccessException {
        if (!isNotInput) //Делается для того чтобы не затирать значения установленные ранее, если поле без ввода
            field.set(parent, value);
    }

}
