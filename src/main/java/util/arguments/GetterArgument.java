package util.arguments;

import util.arguments.filed.GetterFieldArgument;

public interface GetterArgument extends GetterFieldArgument {
    String[] getCommandArgument();
}
