package com.rideshare.observer;

import com.rideshare.model.Driver;
import com.rideshare.model.RideRequest;

import java.time.LocalDateTime;

public class RideEventLogger implements RideEventObserver {

    @Override
    public void onRideRequested(RideRequest request, LocalDateTime eventTime) {
        System.out.printf("[%s] REQUESTED: Customer %s requested %s ride from %s to %s (%.2f mi)%n",
                eventTime, request.getCustomerId(), request.getRideType(),
                request.getStartLocation(), request.getDestination(),
                request.getAnticipatedDistance());
    }

    @Override
    public void onRideAssigned(RideRequest request, Driver driver, LocalDateTime eventTime) {
        System.out.printf("[%s] ASSIGNED: Customer %s assigned to Driver %s%n",
                eventTime, request.getCustomerId(), driver.getDriverId());
    }

    @Override
    public void onRideCompleted(RideRequest request, Driver driver, LocalDateTime eventTime) {
        System.out.printf("[%s] COMPLETED: Driver %s completed ride for Customer %s%n",
                eventTime, driver.getDriverId(), request.getCustomerId());
    }
}
