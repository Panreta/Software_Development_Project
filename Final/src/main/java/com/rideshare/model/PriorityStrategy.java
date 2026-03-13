package com.rideshare.model;

import java.util.function.BiFunction;

/**
 * Strategy Pattern + Functional Programming for priority calculation.
 * Makes it easy to change priority rules without modifying RideRequest.
 */
public class PriorityStrategy {

    // Functional interface for distance bonus calculation
    private final BiFunction<Double, Integer, Integer> distanceBonusCalculator = (distance, basePriority) -> {
        if (distance < 5.0) return 5;
        if (distance < 10.0) return 3;
        return 0;
    };

    /**
     * Calculate total priority based on ride type and distance.
     */
    public int calculatePriority(RideType rideType, double distance) {
        int basePriority = rideType.getBasePriority();
        int bonus = distanceBonusCalculator.apply(distance, basePriority);
        return basePriority + bonus;
    }
}