package Model;

/**
 * Abstract base class representing a vehicle in the valuation system.
 * This class contains common properties shared by all vehicle types.
 *
 * @author Vehicle Valuation System
 * @version 1.0
 */
public abstract class Vehicle {
    private String id;
    private Integer manufacturingYear;
    private MakeModel makeModel; // check the make and model here
    private Double msrp;

    /**
     * Constructs a new Vehicle with the specified properties.
     *
     * @param id The unique identifier for the vehicle
     * @param manufacturingYear The year the vehicle was manufactured
     * @param makeModel The make and model information
     * @param msrp The Manufacturer Suggested Retail Price
     */
    public Vehicle(String id, Integer manufacturingYear, MakeModel makeModel, Double msrp) {
        this.id = id;
        this.manufacturingYear = manufacturingYear;
        this.makeModel = makeModel;
        this.msrp = msrp;
    }

    /**
     * Gets the unique identifier of the vehicle.
     *
     * @return The vehicle's unique ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the vehicle.
     *
     * @param id The ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the manufacturing year of the vehicle.
     *
     * @return The year the vehicle was manufactured
     */
    public Integer getManufacturingYear() {
        return manufacturingYear;
    }

    /**
     * Sets the manufacturing year of the vehicle.
     *
     * @param manufacturingYear The year to set
     */
    public void setManufacturingYear(Integer manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    /**
     * Gets the make and model information of the vehicle.
     *
     * @return The MakeModel object containing make and model details
     */
    public MakeModel getMakeModel() {
        return makeModel;
    }

    /**
     * Sets the make and model information of the vehicle.
     *
     * @param makeModel The MakeModel object to set
     */
    public void setMakeModel(MakeModel makeModel) {
        this.makeModel = makeModel;
    }

    /**
     * Gets the Manufacturer Suggested Retail Price.
     *
     * @return The MSRP of the vehicle
     */
    public Double getMsrp() {
        return msrp;
    }

    /**
     * Sets the Manufacturer Suggested Retail Price.
     *
     * @param msrp The MSRP to set
     */
    public void setMsrp(Double msrp) {
        this.msrp = msrp;
    }

    /**
     * Abstract method to get the vehicle type.
     * Must be implemented by subclasses.
     *
     * @return A string representing the specific vehicle type
     */
    public abstract String getVehicleType();
}