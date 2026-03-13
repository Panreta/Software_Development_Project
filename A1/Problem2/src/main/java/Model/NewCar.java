package Model;

/**
 * Represents a new car in the valuation system.
 * Extends Car and includes information about availability within 50 miles.
 *
 * @author Vehicle Valuation System
 * @version 1.0
 */
public class NewCar extends Car {
    private Integer availableWithin50Miles;

    /**
     * Constructs a new NewCar with the specified properties.
     *
     * @param id The unique identifier for the new car
     * @param manufacturingYear The year the car was manufactured
     * @param makeModel The make and model information
     * @param msrp The Manufacturer Suggested Retail Price
     * @param availableWithin50Miles Number of vehicles available within 50 miles
     */
    public NewCar(String id, Integer manufacturingYear, MakeModel makeModel,
                  Double msrp, Integer availableWithin50Miles) {
        super(id, manufacturingYear, makeModel, msrp);
        this.availableWithin50Miles = availableWithin50Miles;
    }

    /**
     * Gets the number of available vehicles within 50 miles.
     *
     * @return The number of available vehicles in the local area
     */
    public Integer getAvailableWithin50Miles() {
        return availableWithin50Miles;
    }

    /**
     * Sets the number of available vehicles within 50 miles.
     *
     * @param availableWithin50Miles The number to set
     */
    public void setAvailableWithin50Miles(Integer availableWithin50Miles) {
        this.availableWithin50Miles = availableWithin50Miles;
    }

    /**
     * Gets the specific vehicle type for new cars.
     *
     * @return "New Car" as the specific vehicle type
     */
    @Override
    public String getVehicleType() {
        return "New Car";
    }
}