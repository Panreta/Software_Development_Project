package com.rideshare.model;

import java.time.LocalDateTime;
import com.rideshare.model.PriorityStrategy;


/**
 * Represents a ride request from a customer.
 * Immutable class following functional programming principles.
 */
public class RideRequest implements Comparable<RideRequest> { // compare by prioty
    private final String customerId;
    private final String startLocation;
    private final String destination;
    private final double anticipatedDistance;
    private final LocalDateTime requestTimestamp;
    private final RideType rideType;
    private final int priority;
    private final int arrivalOrder;

    public RideRequest(String customerId, String startLocation, String destination,
                       double anticipatedDistance, LocalDateTime requestTimestamp,
                       RideType rideType, int arrivalOrder) {
        this.customerId = customerId;
        this.startLocation = startLocation;
        this.destination = destination;
        this.anticipatedDistance = anticipatedDistance;
        this.requestTimestamp = requestTimestamp;
        this.rideType = rideType;
        this.arrivalOrder = arrivalOrder;
        this.priority = calculatePriority();
    }

    /**
     * Calculates priority based on ride type and distance.
     * Pure function for priority computation.
     */
    private static final PriorityStrategy priorityStrategy = new PriorityStrategy();

    private int calculatePriority() {
        return priorityStrategy.calculatePriority(rideType, anticipatedDistance);
    }

    /**
     * Calculates ride duration in minutes based on 60 mph average speed.
     */
    public double getRideDuration() {
        return (anticipatedDistance / 60.0) * 60.0; // Convert to minutes, math.floor
    }

    @Override
    public int compareTo(RideRequest other) {
        // Higher priority values come first (reverse natural ordering)
        int priorityComparison = Integer.compare(other.priority, this.priority);
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        // Same priority: FIFO by arrival order
        return Integer.compare(this.arrivalOrder, other.arrivalOrder);
    }

    // Getters
    public String getCustomerId() { return customerId; }
    public String getStartLocation() { return startLocation; }
    public String getDestination() { return destination; }
    public double getAnticipatedDistance() { return anticipatedDistance; }
    public LocalDateTime getRequestTimestamp() { return requestTimestamp; }
    public RideType getRideType() { return rideType; }
    public int getPriority() { return priority; }
    public int getArrivalOrder() { return arrivalOrder; }

    @Override
    public String toString() {
        return String.format("RideRequest[customer=%s, type=%s, priority=%d, distance=%.2f miles]",
                customerId, rideType, priority, anticipatedDistance);
    }
}