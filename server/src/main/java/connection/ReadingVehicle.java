package connection;

import base.Vehicle;
import collection.CollectionDirector;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.AbstractCollection;

@Log
@AllArgsConstructor
public class ReadingVehicle implements ReadingObject{

    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    @Override
    public void start(ByteBuffer byteBuffer, ObjectInputStream objectInputStream) {
        Vehicle vehicle = new Vehicle();
        while (vehicle != null){
            try {
                vehicle = (Vehicle) objectInputStream.readObject();
                collectionDirector.add(vehicle);
                System.out.println("Recive: " + vehicle);
            } catch (IOException | ClassNotFoundException e) {
                log.warning(e.getMessage());
            }
        }
    }
}
