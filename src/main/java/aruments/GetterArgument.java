package aruments;

import java.lang.reflect.Field;

public interface GetterArgument {
    String getFieldArgument(Field field);
    String[] getCommandArgument();
}
