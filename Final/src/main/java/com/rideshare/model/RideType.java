package com.rideshare.model;

/**
 * Enumeration of ride types with associated priority levels.
 */
public enum RideType {
    EXPRESS_PICKUP("Express Pick-up", 100),
    STANDARD_PICKUP("Standard Pick-up", 75),
    WAIT_AND_SAVE("Wait-and-Save Pick-up", 50),
    ECO_FRIENDLY("Environmentally Conscious Pick-up", 25);

    private final String displayName;
    private final int basePriority;

    RideType(String displayName, int basePriority) {
        this.displayName = displayName;
        this.basePriority = basePriority;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getBasePriority() {
        return basePriority;
    }

    @Override
    public String toString() {
        return displayName;
    }
}