package base;

import lombok.*;
import org.apache.commons.lang3.builder.CompareToBuilder;
import util.annatations.vehicle.*;

import java.io.Serializable;
import java.util.UUID;
@EqualsAndHashCode
@ToString(exclude = {"formatCSV"})
public class Vehicle implements Comparable<Vehicle>, Serializable {
    @Id
    @NotNull
    @IdAutoGenerate
    @NotInput
    @CheckIt
    @Getter
    @Setter
    private UUID id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NotNull
    @CheckIt
    private String name; //Поле не может быть null, Строка не может быть пустой

    @CheckIt
    @NotNull
    @Getter
    private Coordinates coordinates; //Поле не может быть null

    @NotNull
    @NotInput
    @TimeCurrentGenerate
    @CheckIt
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @NotNull
    @PositiveNumber
    @NotZero
    @Getter
    @CheckIt
    private Double enginePower; //Поле может быть null, Значение поля должно быть больше 0

    @PositiveNumber
    @NotZero
    @Getter
    @CheckIt
    private Double capacity; //Значение поля должно быть больше 0

    @PositiveNumber
    @NotZero
    @CheckIt
    private Double distanceTravelled; //Значение поля должно быть больше 0

    @NotNull
    @CheckIt
    private VehicleType type; //Поле не может быть null

    public String formatCSV; //public т.к. строится поэтапно и использование геттеров и сеттеров стоило бы много

    public Vehicle(){
        formatCSV = "";
    }

    @Override
    public int compareTo(Vehicle o) {
        return new CompareToBuilder()
                .append(name, o.name)
                .append(capacity, o.capacity)
                .append(enginePower, o.enginePower)
                .toComparison();
    }
}