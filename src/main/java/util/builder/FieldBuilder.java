package util.builder;

import aruments.GetterArgument;
import base.VehicleType;
import util.annatations.*;
import util.annatations.handler.HandlerAnnotations;

import java.io.FileReader;
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

    public FieldBuilder handleAnnotationsOnField() throws IllegalAccessException, IllegalArgumentException {
        HandlerAnnotations handlerAnnotations = new HandlerAnnotations(field, parent, isNotInput, value);
        handlerAnnotations
                .handlerIdAutoGenerate()
                .handlerTimeCurrentGenerate()
                .handleMaxValue()
                .handleMinValue()
                .handleNotZero()
                .handlePositiveNumber();
        return this;
    }

    public FieldBuilder setNotInput() {
        isNotInput = field.isAnnotationPresent(NotInput.class);
        return this;
    }

    public FieldBuilder inputField(){
        if (!isNotInput)
            value = getterArgument.getArgument(field);
        return this;
    }

    public FieldBuilder toDouble(){
        if (field.getType().equals(Double.class)){
            try {
                value = Double.parseDouble((String) value);
            } catch (NumberFormatException e){
                throw new IllegalArgumentException(ERROR_DOUBLE_TYPE);
            }
        }
        return this;

    }
    public FieldBuilder toLong(){
        if (field.getType().equals(Long.class)){
            try {
                value = Long.parseLong((String) value);
            } catch (NumberFormatException e){
                throw new IllegalArgumentException(ERROR_LONG_TYPE);
            }
        }
        return this;
    }
    public FieldBuilder toVehicleType(){
        if (field.getType().equals(VehicleType.class)) {
            try {
                value = VehicleType.valueOf((String) value);
            } catch (IllegalArgumentException e){
                throw new IllegalArgumentException(ERROR_VEHICLE_TYPE);
            }
        }
        return this;
    }
    public void setField() throws IllegalAccessException {
        if (!isNotInput) //Делается для того чтобы не затирать значения установленные ранее, если поле без ввода
            field.set(parent, value);
    }

}
