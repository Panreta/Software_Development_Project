package com.rideshare.model;

/**
 * Represents a driver in the rideshare system.
 * Tracks driver state and ride count.
 */
public class Driver {
    private final String driverId;
    private boolean available;
    private int totalRidesCompleted;
    private RideRequest currentRide;

    public Driver(String driverId) {
        this.driverId = driverId;
        this.available = true;
        this.totalRidesCompleted = 0;
        this.currentRide = null;
    }

    /**
     * Assigns a ride to this driver.
     */
    public synchronized void assignRide(RideRequest request) {
        if (!available) {
            throw new IllegalStateException("Driver " + driverId + " is not available");
        }
        this.currentRide = request;
        this.available = false;
    }

    /**
     * Completes the current ride and makes driver available.
     */
    public synchronized void completeRide() {
        if (currentRide == null) {
            throw new IllegalStateException("Driver " + driverId + " has no active ride");
        }
        this.totalRidesCompleted++;
        this.currentRide = null;
        this.available = true;
    }

    public synchronized boolean isAvailable() {
        return available;
    }

    public String getDriverId() {
        return driverId;
    }

    public synchronized int getTotalRidesCompleted() {
        return totalRidesCompleted;
    }


    @Override
    public String toString() {
        return String.format("Driver[id=%s, available=%s, ridesCompleted=%d]",
                driverId, available, totalRidesCompleted);
    }
}