package Model;

/**
 * Represents a boat in the valuation system.
 * Extends Vessel and includes length, passenger capacity, and propulsion information.
 *
 * @author Vehicle Valuation System
 * @version 1.0
 */
public class Boat extends Vessel {
    private Float length;
    private Integer numberOfPassengers;
    private PropulsionType propulsionType;

    /**
     * Constructs a new Boat with the specified properties.
     *
     * @param id The unique identifier for the boat
     * @param manufacturingYear The year the boat was manufactured
     * @param makeModel The make and model information
     * @param msrp The Manufacturer Suggested Retail Price
     * @param length The length of the boat
     * @param numberOfPassengers The maximum number of passengers
     * @param propulsionType The type of propulsion system
     */
    public Boat(String id, Integer manufacturingYear, MakeModel makeModel,
                Double msrp, Float length, Integer numberOfPassengers,
                PropulsionType propulsionType) {
        super(id, manufacturingYear, makeModel, msrp);
        this.length = length;
        this.numberOfPassengers = numberOfPassengers;
        this.propulsionType = propulsionType;
    }

    /**
     * Gets the length of the boat.
     *
     * @return The boat's length
     */
    public Float getLength() {
        return length;
    }

    /**
     * Sets the length of the boat.
     *
     * @param length The length to set
     */
    public void setLength(Float length) {
        this.length = length;
    }

    /**
     * Gets the maximum number of passengers.
     *
     * @return The passenger capacity
     */
    public Integer getNumberOfPassengers() {
        return numberOfPassengers;
    }

    /**
     * Sets the maximum number of passengers.
     *
     * @param numberOfPassengers The number to set
     */
    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    /**
     * Gets the propulsion type of the boat.
     *
     * @return The type of propulsion system
     */
    public PropulsionType getPropulsionType() {
        return propulsionType;
    }

    /**
     * Sets the propulsion type of the boat.
     *
     * @param propulsionType The propulsion type to set
     */
    public void setPropulsionType(PropulsionType propulsionType) {
        this.propulsionType = propulsionType;
    }

    /**
     * Gets the specific vehicle type for boats.
     *
     * @return "Boat" as the specific vehicle type
     */
    @Override
    public String getVehicleType() {
        return "Boat";
    }
}