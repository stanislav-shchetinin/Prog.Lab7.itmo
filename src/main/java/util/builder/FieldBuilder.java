package util.builder;

import aruments.GetterArgument;
import base.VehicleType;
import util.annatations.*;
import util.annatations.handler.HandlerAnnotations;

import java.lang.reflect.Field;

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
        inputField();
        toDouble();
        toLong();
        toVehicleType();
        HandlerAnnotations handlerAnnotations = new HandlerAnnotations(field, parent, isNotInput, value);
        handlerAnnotations.handleAllAnnotations();
        setField();
    }

    private void setNotInput() {
        isNotInput = field.isAnnotationPresent(NotInput.class);
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
    private void setField() throws IllegalAccessException {
        if (!isNotInput) //Делается для того чтобы не затирать значения установленные ранее, если поле без ввода
            field.set(parent, value);
    }

}
