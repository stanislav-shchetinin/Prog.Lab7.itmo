package util.arguments;

public class FactoryGettersArgument {
    private final GetterArgument getterArgument;

    public FactoryGettersArgument(WayGetArgument wayGetArgument){
        if (wayGetArgument.equals(WayGetArgument.CONSOLE)){
            getterArgument = new ConsoleGetterArgument();
        } else {
            getterArgument = new FileGetterArgument();
        }
    }

    public GetterArgument getGetterArgument() {
        return getterArgument;
    }
}
