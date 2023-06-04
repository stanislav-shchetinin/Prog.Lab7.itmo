package util.builders;

import base.Vehicle;
import lombok.Getter;
import lombok.extern.java.Log;
import util.annatations.vehicle.CheckIt;
import util.arguments.FactoryGettersArgument;
import util.arguments.GetterArgument;
import util.arguments.WayGetArgument;
import util.builders.FieldBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Log
public class VehicleBuilder {
    @Getter
    private Vehicle vehicle;
    private final GetterArgument getterArgument;
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
}
