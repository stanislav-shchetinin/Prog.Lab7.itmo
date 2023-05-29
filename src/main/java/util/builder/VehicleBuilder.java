package util.builder;

import aruments.*;
import base.Vehicle;
import lombok.extern.java.Log;
import util.annatations.Input;
import util.annatations.NotInput;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Log
public class VehicleBuilder {
    Vehicle vehicle;
    GetterArgument getterArgument;

    public VehicleBuilder(WayGetArgument wayGetArgument){
        vehicle = new Vehicle();
        getterArgument = new FactoryGettersArgument(wayGetArgument).getGetterArgument();
    }

    public void buildVehicle() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Field field : Vehicle.class.getDeclaredFields()) {
            field.setAccessible(true);
            recursionInField(field, vehicle);
        }
    }

    private void recursionInField(Field mainField, Object parent) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        boolean hasNotSystemField = false;
        Object mainFieldObject = null;
        for (Field field : mainField.getType().getDeclaredFields()){
            if (field.isAnnotationPresent(NotInput.class) ||
                field.isAnnotationPresent(Input.class)){
                hasNotSystemField = true;
                if (mainFieldObject == null){
                    mainFieldObject = mainField.getType().getConstructor().newInstance();
                }
                recursionInField(field, mainFieldObject);
            }
        }
        if (!hasNotSystemField){
            FieldBuilder fieldBuilder = new FieldBuilder(getterArgument, parent, mainField);
            while (true){
                try {
                    fieldBuilder.buildField();
                    break;
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    log.warning(e.getMessage());
                }
            }
        } else {
            mainField.set(parent, mainFieldObject);
        }
    }

    public Vehicle getVehicle(){
        return vehicle;
    }
}
