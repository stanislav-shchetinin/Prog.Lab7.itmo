package connection;

import base.Vehicle;
import collection.CollectionDirector;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import response.Response;
import response.Status;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.AbstractCollection;

@Log
@AllArgsConstructor
public class ReadingVehicle implements ReadingObject{

    private CollectionDirector<? extends AbstractCollection<Vehicle>> collectionDirector;
    @Override
    public Response start(ByteBuffer byteBuffer) {
        /*Vehicle vehicle = new Vehicle();
        while (vehicle != null){
            try {
                vehicle = (Vehicle) objectInputStream.readObject();
                collectionDirector.add(vehicle);
            } catch (IOException | ClassNotFoundException e) {
                return new Response(Status.ERROR, e.getMessage());
            }
        }*/
        return new Response(Status.OK);
    }
}
