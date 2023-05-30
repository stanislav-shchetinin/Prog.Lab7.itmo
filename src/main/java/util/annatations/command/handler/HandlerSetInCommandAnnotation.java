package util.annatations.command.handler;

import aruments.GetterArgument;
import lombok.AllArgsConstructor;
import util.annatations.command.Input;
import util.annatations.command.SetInCommand;

import java.lang.reflect.Field;

/**
 * Отлавливает аннотации метки
 * */

@AllArgsConstructor
public class HandlerSetInCommandAnnotation {

    Field field;

    public void handleInputAnnotation(){
        if (field.isAnnotationPresent(SetInCommand.class)){



        }
    }

}
