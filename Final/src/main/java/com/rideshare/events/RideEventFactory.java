package com.rideshare.events;

import com.rideshare.model.Driver;
import com.rideshare.model.RideRequest;
import java.time.LocalDateTime;

/**
 * Factory Pattern implementation for creating ride events.
 * Encapsulates event creation logic and provides a clean interface.
 */
public class RideEventFactory {

    /**
     * Creates a RideRequestedEvent.
     */
    public static RideEvent createRideRequestedEvent(LocalDateTime eventTime, RideRequest request) {
        return new RideRequestedEvent(eventTime, request);
    }

    /**
     * Creates a RideFinishedEvent.
     */
    public static RideEvent createRideFinishedEvent(LocalDateTime eventTime, Driver driver,
                                                    RideRequest completedRide) {
        return new RideFinishedEvent(eventTime, driver, completedRide);
    }
}