package com.rideshare.events;

import com.rideshare.model.Driver;
import com.rideshare.model.RideRequest;

import java.time.LocalDateTime;

public class RideFinishedEvent extends RideEvent {
    private final Driver driver;
    private final RideRequest completedRide;

    public RideFinishedEvent(LocalDateTime eventTime, Driver driver, RideRequest completedRide) {
        super(eventTime, EventType.RIDE_FINISHED);
        this.driver = driver;
        this.completedRide = completedRide;
    }


    @Override
    public void process(SimulationContext context) {
        context.handleRideFinished(driver, completedRide);
    }

    @Override
    public String toString() {
        return String.format("RideFinishedEvent[time=%s, driver=%s, ride=%s]",
                eventTime, driver.getDriverId(), completedRide.getCustomerId());
    }
}
