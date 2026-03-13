package Model;

/**
 * Abstract class representing a car in the valuation system.
 * This class extends Vehicle and serves as a base for specific car types.
 *
 * @author Vehicle Valuation System
 * @version 1.0
 */
public abstract class Car extends Vehicle {

    /**
     * Constructs a new Car with the specified properties.
     *
     * @param id The unique identifier for the car
     * @param manufacturingYear The year the car was manufactured
     * @param makeModel The make and model information
     * @param msrp The Manufacturer Suggested Retail Price
     */
    public Car(String id, Integer manufacturingYear, MakeModel makeModel, Double msrp) {
        super(id, manufacturingYear, makeModel, msrp);
    }

    /**
     * Gets the vehicle type for cars.
     *
     * @return "Car" as the general vehicle type
     */
    @Override
    public String getVehicleType() {
        return "Car";
    }
}