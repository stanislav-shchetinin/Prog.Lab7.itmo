package util.arguments.filed;

import util.arguments.ConsoleGetterArgument;
import util.arguments.FileGetterArgument;

public class FactoryGetterFieldArgument {
    public static GetterFieldArgument generateGetterArgument(WayGetFieldArgument wayGetFieldArgument){
        if (wayGetFieldArgument.equals(WayGetFieldArgument.CONSOLE)){
            return new ConsoleGetterArgument();
        } else if (wayGetFieldArgument.equals(WayGetFieldArgument.FILE)){
            return new FileGetterArgument();
        } else {
            return new CSVGetterFieldArgument();
        }
    }
}
