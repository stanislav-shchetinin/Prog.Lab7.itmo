package util.builders;

import base.Vehicle;
import exceptions.FileException;
import lombok.Getter;
import lombok.extern.java.Log;
import util.annatations.vehicle.CheckIt;
import util.arguments.filed.CSVGetterFieldArgument;
import util.arguments.filed.GetterFieldArgument;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Log
public class VehicleBuilder {
    @Getter
    private Vehicle vehicle;
    private final GetterFieldArgument getterFieldArgument;
    private String formatCSV;
    private String formatCSV1;

    public VehicleBuilder(GetterFieldArgument getterFieldArgument){
        this.getterFieldArgument = getterFieldArgument;
    }

    public void createVehicle(){
        this.vehicle = new Vehicle();
    }

    public void buildVehicle() throws FileException, IllegalArgumentException {
        for (Field field : Vehicle.class.getDeclaredFields()) {
            try {
                if (!field.isAnnotationPresent(CheckIt.class)) continue;
                field.setAccessible(true);
                recursionInField(field, vehicle);
            } catch (NoSuchMethodException |
                    InvocationTargetException |
                    InstantiationException |
                     IllegalAccessException e){
                log.warning(e.getMessage());
                break;
            }
        }
        vehicle.formatCSV = vehicle.formatCSV.substring(0,vehicle.formatCSV.length() - 1); //удаляется последняя ,
    }

    private void recursionInField(Field mainField, Object parent) throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException, FileException, IllegalArgumentException {
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
            FieldBuilder fieldBuilder = new FieldBuilder(getterFieldArgument, parent, mainField);
            while (true){
                try {
                    fieldBuilder
                            .setNotInput()
                            .inputField()
                            .toDouble()
                            .toLong()
                            .toVehicleType()
                            .toUUID()
                            .toZonedDataTime()
                            .handleAnnotationsOnField()
                            .setField();
                    vehicle.formatCSV += String.format("\"%s\",", fieldBuilder.getField().get(parent).toString()); //строится csv
                    break;
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    if (getterFieldArgument instanceof CSVGetterFieldArgument){
                        throw e;
                    }
                    log.warning(e.getMessage());
                }
            }
        } else {
            mainField.set(parent, mainFieldObject);
        }
    }
}
