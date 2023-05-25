package base;

import annatations.*;
import util.annatations.NotInput;
import util.annatations.TimeCurrentGenerate;

import java.util.UUID;

public class Vehicle {
    @Id
    @NotNull
    @IdAutoGenerate
    @NotInput
    private UUID id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotNull
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull
    private Coordinates coordinates; //Поле не может быть null

    @NotNull
    @NotInput
    @TimeCurrentGenerate
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @NotNull
    @PositiveNumber
    @NotZero
    private Double enginePower; //Поле может быть null, Значение поля должно быть больше 0

    @PositiveNumber
    @NotZero
    private Double capacity; //Значение поля должно быть больше 0

    @PositiveNumber
    @NotZero
    private Double distanceTravelled; //Значение поля должно быть больше 0

    @NotNull
    private VehicleType type; //Поле не может быть null
}