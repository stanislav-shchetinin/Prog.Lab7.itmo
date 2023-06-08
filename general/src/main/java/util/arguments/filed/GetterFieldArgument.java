package util.arguments.filed;

import exceptions.FileException;

import java.lang.reflect.Field;

public interface GetterFieldArgument {
    String getFieldArgument(Field field) throws FileException;
}
