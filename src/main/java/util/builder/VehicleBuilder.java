package util.builder;

import aruments.*;
import base.Vehicle;
import lombok.extern.java.Log;
import util.annatations.vehicle.CheckIt;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Log
public class VehicleBuilder {
    Vehicle vehicle;
    GetterArgument getterArgument;
    WayGetArgument wayGetArgument;

    public VehicleBuilder(WayGetArgument wayGetArgument){
        this.getterArgument = new FactoryGettersArgument(wayGetArgument).getGetterArgument();
        this.wayGetArgument = wayGetArgument;
    }
    public VehicleBuilder(GetterArgument getterArgument){
        this.getterArgument = getterArgument;
    }

    public void createVehicle(){
        this.vehicle = new Vehicle();
    }

    public void buildVehicle() {
        for (Field field : Vehicle.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                recursionInField(field, vehicle);
            } catch (NoSuchMethodException |
                    InvocationTargetException |
                    InstantiationException |
                     IllegalAccessException e){
                log.warning(e.getMessage());
            }

        }
    }

    private void recursionInField(Field mainField, Object parent) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        boolean hasNotSystemField = false;
        Object mainFieldObject = null;
        for (Field field : mainField.getType().getDeclaredFields()){
            if (field.isAnnotationPresent(CheckIt.class)){
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
                    fieldBuilder
                            .setNotInput()
                            .inputField()
                            .toDouble()
                            .toLong()
                            .toVehicleType()
                            .handleAnnotationsOnField()
                            .setField();
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
