package aruments;

import base.Coordinates;
import base.VehicleType;

import java.lang.reflect.Field;
import java.util.Scanner;

import static util.constants.ConstantsForConsoleFormatInput.*;

public class ConsoleGetterArgument implements GetterArgument{

    @Override
    public String getArgument(Field field) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(formatInput(field) + inviteToInput(field));
        return scanner.next();
    }

    private String formatInput(Field field){
        if (field.getType().equals(Double.class)){
            return DOUBLE_FORMAT_INPUT;
        } else if (field.getType().equals(VehicleType.class)){
            return VEHICLE_TYPE_FORMAT_INPUT;
        } else if (field.getType().equals(Long.class)) {
            return LONG_FORMAT_INPUT;
        } else {
            return ANOTHER_FORMAT_INPUT;
        }
    }

    private String inviteToInput(Field field){
        return String.format("Введите %s: ", field.getName());
    }

}