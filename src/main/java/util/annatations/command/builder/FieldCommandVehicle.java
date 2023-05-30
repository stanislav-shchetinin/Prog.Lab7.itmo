package util.annatations.command.builder;

import aruments.GetterArgument;
import aruments.WayGetArgument;
import util.builder.FieldBuilder;
import util.builder.HandleAnnotations;

import java.lang.reflect.Field;

public class FieldCommandVehicle<T extends HandleAnnotations> extends FieldBuilder {
    T handleAnnotation;

    public FieldCommandVehicle(String value, Object parent, Field field) {
        super(value, parent, field);
    }

}
