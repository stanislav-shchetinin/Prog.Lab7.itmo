package exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CollectionException extends Exception{
    public CollectionException(String message){
        super(message);
    }
}
