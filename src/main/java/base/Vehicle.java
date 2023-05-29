package base;

import lombok.*;
import org.apache.commons.lang3.builder.CompareToBuilder;
import util.annatations.*;

import java.util.AbstractCollection;
import java.util.UUID;
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class Vehicle implements Comparable<Vehicle>{
    @Id
    @NotNull
    @IdAutoGenerate
    @NotInput
    @Getter
    private UUID id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Input
    @NotNull
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Input
    @NotNull
    @Getter
    private Coordinates coordinates = new Coordinates(); //Поле не может быть null

    @NotNull
    @NotInput
    @TimeCurrentGenerate
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @NotNull
    @PositiveNumber
    @NotZero
    @Getter
    @Input
    private Double enginePower; //Поле может быть null, Значение поля должно быть больше 0

    @PositiveNumber
    @NotZero
    @Getter
    @Input
    private Double capacity; //Значение поля должно быть больше 0

    @PositiveNumber
    @NotZero
    @Input
    private Double distanceTravelled; //Значение поля должно быть больше 0

    @NotNull
    @Input
    private VehicleType type; //Поле не может быть null

    @Override
    public int compareTo(Vehicle o) {
        return new CompareToBuilder()
                .append(name, o.name)
                .append(capacity, o.capacity)
                .append(enginePower, o.enginePower)
                .toComparison();
    }
}