package util.arguments;

public class FactoryGetterArgument {
    public static GetterArgument generateGetterArgument(WayGetArgument wayGetArgument){
        if (wayGetArgument.equals(WayGetArgument.CONSOLE)){
            return new ConsoleGetterArgument();
        } else {
            return new FileGetterArgument();
        }
    }
}
