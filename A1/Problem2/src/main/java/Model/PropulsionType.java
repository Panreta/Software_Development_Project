package Model;

/**
 * Enumeration representing different types of boat propulsion systems.
 *
 * @author Vehicle Valuation System
 * @version 1.0
 */
public enum PropulsionType {
    /**
     * Sail-powered propulsion using wind
     */
    SAIL_POWER("Sail Power"),

    /**
     * Inboard engine mounted inside the hull
     */
    INBOARD_ENGINE("Inboard Engine"),

    /**
     * Outboard engine mounted outside the hull
     */
    OUTBOARD_ENGINE("Outboard Engine"),

    /**
     * Jet propulsion system
     */
    JET_PROPULSION("Jet Propulsion");

    private final String displayName;

    /**
     * Constructor for PropulsionType enum.
     *
     * @param displayName The human-readable name of the propulsion type
     */
    PropulsionType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the propulsion type.
     *
     * @return The human-readable propulsion type name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the display name when converting to string.
     *
     * @return The display name of the propulsion type
     */
    @Override
    public String toString() {
        return displayName;
    }
}