package Model;

/**
 * Represents a used car in the valuation system.
 * Extends Car and includes mileage, ownership history, and accident information.
 *
 * @author Vehicle Valuation System
 * @version 1.0
 */
public class UsedCar extends Car {
    private Integer mileage;
    private Integer numberOfPreviousOwners;
    private Integer numberOfMinorAccidents;

    /**
     * Constructs a new UsedCar with the specified properties.
     *
     * @param id The unique identifier for the used car
     * @param manufacturingYear The year the car was manufactured
     * @param makeModel The make and model information
     * @param msrp The Manufacturer Suggested Retail Price
     * @param mileage The current mileage on the vehicle
     * @param numberOfPreviousOwners The number of previous owners
     * @param numberOfMinorAccidents The number of minor traffic accidents
     */
    public UsedCar(String id, Integer manufacturingYear, MakeModel makeModel,
                   Double msrp, Integer mileage, Integer numberOfPreviousOwners,
                   Integer numberOfMinorAccidents) {
        super(id, manufacturingYear, makeModel, msrp);
        this.mileage = mileage;
        this.numberOfPreviousOwners = numberOfPreviousOwners;
        this.numberOfMinorAccidents = numberOfMinorAccidents;
    }

    /**
     * Gets the mileage of the used car.
     *
     * @return The current mileage
     */
    public Integer getMileage() {
        return mileage;
    }

    /**
     * Sets the mileage of the used car.
     *
     * @param mileage The mileage to set
     */
    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    /**
     * Gets the number of previous owners.
     *
     * @return The number of previous owners
     */
    public Integer getNumberOfPreviousOwners() {
        return numberOfPreviousOwners;
    }

    /**
     * Sets the number of previous owners.
     *
     * @param numberOfPreviousOwners The number to set
     */
    public void setNumberOfPreviousOwners(Integer numberOfPreviousOwners) {
        this.numberOfPreviousOwners = numberOfPreviousOwners;
    }

    /**
     * Gets the number of minor traffic accidents the vehicle was involved in.
     *
     * @return The number of minor accidents
     */
    public Integer getNumberOfMinorAccidents() {
        return numberOfMinorAccidents;
    }

    /**
     * Sets the number of minor traffic accidents.
     *
     * @param numberOfMinorAccidents The number to set
     */
    public void setNumberOfMinorAccidents(Integer numberOfMinorAccidents) {
        this.numberOfMinorAccidents = numberOfMinorAccidents;
    }

    /**
     * Gets the specific vehicle type for used cars.
     *
     * @return "Used Car" as the specific vehicle type
     */
    @Override
    public String getVehicleType() {
        return "Used Car";
    }
}