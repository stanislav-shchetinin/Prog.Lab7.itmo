package util.arguments;

import exceptions.FileException;
import util.arguments.filed.GetterFieldArgument;

public interface GetterArgument extends GetterFieldArgument {
    String[] getCommandArgument() throws FileException;
}
