package Model;

/**
 * Abstract class representing a vessel in the valuation system.
 * This class extends Vehicle and serves as a base for specific vessel types.
 *
 * @author Vehicle Valuation System
 * @version 1.0
 */
public abstract class Vessel extends Vehicle {

    /**
     * Constructs a new Vessel with the specified properties.
     *
     * @param id The unique identifier for the vessel
     * @param manufacturingYear The year the vessel was manufactured
     * @param makeModel The make and model information
     * @param msrp The Manufacturer Suggested Retail Price
     */
    public Vessel(String id, Integer manufacturingYear, MakeModel makeModel, Double msrp) {
        super(id, manufacturingYear, makeModel, msrp);
    }

    /**
     * Gets the vehicle type for vessels.
     *
     * @return "Vessel" as the general vehicle type
     */
    @Override
    public String getVehicleType() {
        return "Vessel";
    }
}