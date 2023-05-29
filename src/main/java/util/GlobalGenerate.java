package util;

import commands.*;
import commands.auxiliary.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GlobalGenerate {
    public static ArrayList<Command> getListCommands(){
        return new ArrayList<Command>(List.of(
                new Add(), new AddIfMax(), new AddIfMin(),
                new Clear(), new CountByCapacity(), new ExecuteScript(),
                new CountByCapacity(), new Exit(), new Help()
        ));
    }
    public static HashMap<String, Command> getMapCommands(ArrayList<Command> listCommands){
        HashMap<String, Command> hashMap = new HashMap<>();
        listCommands.forEach(x -> hashMap.put(x.name(), x));
        return hashMap;
    }
}
