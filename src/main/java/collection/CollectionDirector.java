package collection;

import base.Vehicle;
import exceptions.CollectionException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static util.constants.ConstantsForCollectionDirector.*;

public class CollectionDirector<T extends AbstractCollection<Vehicle>> {
    private T collection;
    private HashSet<UUID> uuidHashSet;
    private final Date date;
    private final Supplier<T> TFactory = ()->{
        try {
            return (T) collection.getClass().getConstructor().newInstance(); //Кажется не лучшее решение, особенно смущает небезопасный каст
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    };
    public CollectionDirector (T collection) {
        this.collection = collection;
        this.uuidHashSet = new HashSet<>();
        this.date = new Date();
    }

    public String show(){
        String collectionToString = collection.toString();
        return collectionToString.substring(1, collectionToString.length() - 1);
    }

    public String info(){
        return String.format("Тип: %s\nДата инициализации: %s\nКоличество элементов: %d",
                collection.getClass().getSimpleName(),
                date,
                collection.size());
    }

    public void clear(){
        collection.clear();
        uuidHashSet.clear();
    }

    public void removeFirst() throws CollectionException {
        if (collection.size() == 0){
            throw new CollectionException(REMOVE_FROM_EMPTY_COLLECTION);
        }
        UUID id = collection.iterator().next().getId();
        collection.remove(collection.iterator().next());
        uuidHashSet.remove(id);
    }

    public String printAscending(){
        String answer = collection
                .stream()
                .toList()
                .toString();
        return answer.substring(1, answer.length() - 1);
    }

    public String printUniqueEnginePower(){
            return collection
                    .stream()
                    .map(Vehicle::getEnginePower)
                    .distinct().toList()
                    .toString();
    }

    public void removeById (UUID id){
        checkId(id);
        uuidHashSet.remove(id);
        collection = collection.stream().filter(x -> !x.getId().equals(id))
                .collect(Collectors.toCollection(TFactory));
    }

    public String countByCapacity(Double capacity){
            return Long.toString(collection
                    .stream()
                    .filter(x -> x.getCapacity().equals(capacity))
                    .count());
    }

    public void add(Vehicle vehicle){
        if (uuidHashSet.contains(vehicle.getId())){
            throw new IllegalArgumentException(NOT_UNIQUE_ID);
        }
        collection.add(vehicle);
        uuidHashSet.add(vehicle.getId());
    }

    public void updateById(Vehicle vehicle, UUID id){
        // Не требуется проверять id, т.к. я это делаю уже в removeById
        removeById(id);
        add(vehicle);
        vehicle.setId(id);
        uuidHashSet.add(id);
    }

    public void addIfMax(Vehicle vehicle){
        Vehicle vehicleFirst = collection.iterator().next();
        if (collection.size() == 0 || vehicle.compareTo(vehicleFirst) > 0){
            collection.add(vehicle);
            uuidHashSet.add(vehicle.getId());
        }
    }

    public void addIfMin(Vehicle vehicle){
        Vehicle vehicleFirst = collection.iterator().next();
        if (collection.size() == 0 || vehicle.compareTo(vehicleFirst) < 0){
            collection.add(vehicle);
            uuidHashSet.add(vehicle.getId());
        }
    }

    private void checkId (UUID id){
        if (!uuidHashSet.contains(id)){
            throw new IllegalArgumentException(NO_SUCH_ID);
        }
    }

}
