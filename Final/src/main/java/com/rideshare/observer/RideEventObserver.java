package com.rideshare.observer;

import com.rideshare.model.Driver;
import com.rideshare.model.RideRequest;
import java.time.LocalDateTime;

/**
 * Observer Pattern: Interface for objects that want to be notified of ride events.
 */
public interface RideEventObserver {
    void onRideRequested(RideRequest request, LocalDateTime eventTime);
    void onRideAssigned(RideRequest request, Driver driver, LocalDateTime eventTime);
    void onRideCompleted(RideRequest request, Driver driver, LocalDateTime eventTime);
}

/**
 * Concrete observer that logs ride events.
 */
