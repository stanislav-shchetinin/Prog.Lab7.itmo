package util.builder;

import aruments.FactoryGettersArgument;
import aruments.GetterArgument;
import aruments.WayGetArgument;
import base.VehicleType;
import util.annatations.vehicle.handler.HandlerVehicleAnnotations;
import util.annatations.vehicle.NotInput;

import java.lang.reflect.Field;
import java.util.UUID;

import static util.constants.ConstantsForFieldBuilder.*;

public class FieldBuilder {
    Object parent;
    Field field;
    GetterArgument getterArgument;
    Object value;
    boolean isNotInput;

    public FieldBuilder(WayGetArgument wayGetArgument, Object parent, Field field){
        this.parent = parent;
        this.field = field;
        this.getterArgument = new FactoryGettersArgument(wayGetArgument).getGetterArgument();
        field.setAccessible(true);
    }

    public FieldBuilder(GetterArgument getterArgument, Object parent, Field field){
        this.parent = parent;
        this.field = field;
        this.getterArgument = getterArgument;
        field.setAccessible(true);
    }
    //Лучше разделить реализации где есть value и где его надо получить
    public FieldBuilder(String value, Object parent, Field field){
        this.parent = parent;
        this.field = field;
        this.value = value;
        field.setAccessible(true);
    }

    public FieldBuilder handleAnnotationsOnField() throws IllegalAccessException, IllegalArgumentException {
        HandlerVehicleAnnotations handlerAnnotations = new HandlerVehicleAnnotations(field, parent, isNotInput, value);
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
            value = getterArgument.getFieldArgument(field);
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
                throw new IllegalArgumentException(ERROR_VEHICLE_TYPE_TYPE);
            }
        }
        return this;
    }
    public FieldBuilder toUUID(){
        if (field.getType().equals(UUID.class)) {
            try {
                value = UUID.fromString((String) value);
            } catch (IllegalArgumentException e){
                throw new IllegalArgumentException(ERROR_UUID_TYPE);
            }
        }
        return this;
    }
    public void setField() throws IllegalAccessException {
        if (!isNotInput) //Делается для того чтобы не затирать значения установленные ранее, если поле без ввода
            field.set(parent, value);
    }

}
