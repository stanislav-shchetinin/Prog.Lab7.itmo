package aruments;

import commands.auxiliary.Command;

import java.lang.reflect.Field;

public class FileGetterArgument implements GetterArgument{
    @Override
    public String getFieldArgument(Field field) {
        return null;
    }

    @Override
    public String[] getCommandArgument() {
        return new String[0];
    }
}
