package Model;

/**
 * Represents the make and model information for a vehicle.
 * This class encapsulates the manufacturer (make) and specific model details.
 *
 * @author Vehicle Valuation System
 * @version 1.0
 */
public class MakeModel {
    private String make;
    private String model;

    /**
     * Constructs a new MakeModel object with specified make and model.
     *
     * @param make The manufacturer of the vehicle (e.g., "Toyota", "Ford")
     * @param model The specific model of the vehicle (e.g., "Camry", "F-150")
     */
    public MakeModel(String make, String model) {
        this.make = make;
        this.model = model;
    }

    /**
     * Gets the vehicle make.
     *
     * @return The manufacturer of the vehicle
     */
    public String getMake() {
        return make;
    }

    /**
     * Sets the vehicle make.
     *
     * @param make The manufacturer to set
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * Gets the vehicle model.
     *
     * @return The model of the vehicle
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the vehicle model.
     *
     * @param model The model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Returns a string representation of the MakeModel.
     *
     * @return A string in the format "Make Model"
     */
    @Override
    public String toString() {
        return make + " " + model;
    }
}