package util.builders;

import base.VehicleType;
import exceptions.FileException;
import lombok.Getter;
import util.annatations.vehicle.NotInput;
import util.annatations.vehicle.handler.HandlerVehicleAnnotations;
import util.arguments.filed.GetterFieldArgument;
import util.arguments.filed.RemoveAnnotations;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import static util.constants.ConstantsForFieldBuilder.*;

public class FieldBuilder {
    private final Object parent;
    @Getter
    private final Field field;
    private GetterFieldArgument getterArgument;

    private Object value;
    private boolean isNotInput;
    public FieldBuilder(GetterFieldArgument getterArgument, Object parent, Field field){
        this.parent = parent;
        this.field = field;
        this.getterArgument = getterArgument;
        field.setAccessible(true);
    }
    //Лучше разделить реализации где есть value и где его надо получить (в первом случае - поля Vehicle, во втором - Command)
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
        isNotInput = field.isAnnotationPresent(NotInput.class) && !(getterArgument instanceof RemoveAnnotations);
        return this;
    }

    public FieldBuilder inputField() throws FileException {
        if (!isNotInput)
            value = getterArgument.getFieldArgument(field);
        return this;
    }

    public FieldBuilder toDouble(){
        if (field.getType().equals(Double.class) && !isNotInput){
            try {
                value = Double.parseDouble((String) value);
            } catch (NumberFormatException e){
                throw new IllegalArgumentException(ERROR_DOUBLE_TYPE);
            }
        }
        return this;

    }
    public FieldBuilder toLong(){
        if (field.getType().equals(Long.class) && !isNotInput){
            try {
                value = Long.parseLong((String) value);
            } catch (NumberFormatException e){
                throw new IllegalArgumentException(ERROR_LONG_TYPE);
            }
        }
        return this;
    }
    public FieldBuilder toVehicleType(){
        if (field.getType().equals(VehicleType.class) && !isNotInput) {
            try {
                value = VehicleType.valueOf((String) value);
            } catch (IllegalArgumentException e){
                throw new IllegalArgumentException(ERROR_VEHICLE_TYPE_TYPE);
            }
        }
        return this;
    }
    public FieldBuilder toUUID(){
        if (field.getType().equals(UUID.class) && !isNotInput) {
            try {
                value = UUID.fromString((String) value);
            } catch (IllegalArgumentException e){
                throw new IllegalArgumentException(ERROR_UUID_TYPE);
            }
        }
        return this;
    }
    public FieldBuilder toZonedDataTime(){
        if (field.getType().equals(ZonedDateTime.class) && !isNotInput) {
            try {
                value = ZonedDateTime.parse((String) value);
            } catch (DateTimeParseException e){
                throw new IllegalArgumentException(ERROR_ZONED_DATA_TIME_TYPE);
            }
        }
        return this;
    }

    public FieldBuilder toPath(){
        if (field.getType().equals(Path.class) && !isNotInput) {
            try {
                value = Path.of((String) value);
            } catch (DateTimeParseException e){
                throw new IllegalArgumentException(ERROR_ZONED_DATA_TIME_TYPE);
            }
        }
        return this;
    }
    public void setField() throws IllegalAccessException {
        if (!isNotInput) //Делается для того чтобы не затирать значения установленные ранее, если поле без ввода
            field.set(parent, value);
    }

}
