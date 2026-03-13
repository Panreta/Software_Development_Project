package com.rideshare.events;

import com.rideshare.model.Driver;
import com.rideshare.model.RideRequest;

/**
 * Interface for simulation context that processes events.
 * Part of Command Pattern implementation.
 */
public interface SimulationContext {
    void handleRideRequested(RideRequest request);
    void handleRideFinished(Driver driver, RideRequest completedRide);
}