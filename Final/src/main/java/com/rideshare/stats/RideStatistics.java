package com.rideshare.stats;

import com.rideshare.model.Driver;
import com.rideshare.model.RideRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Thread-safe statistics tracker for the simulation.
 * Uses concurrent collections to ensure thread safety.
 */
public class RideStatistics {
    private final CopyOnWriteArrayList<Double> waitTimes;
    private final CopyOnWriteArrayList<Double> rideDurations;
    private final ConcurrentHashMap<String, LocalDateTime> requestTimes;
    private final ConcurrentHashMap<String, LocalDateTime> assignmentTimes;
    private final List<Driver> drivers;

    public RideStatistics(List<Driver> drivers) {
        this.waitTimes = new CopyOnWriteArrayList<>();
        this.rideDurations = new CopyOnWriteArrayList<>();
        this.requestTimes = new ConcurrentHashMap<>();
        this.assignmentTimes = new ConcurrentHashMap<>();
        this.drivers = new ArrayList<>(drivers);
    }

    /**
     * Records when a ride was requested.
     */
    public void recordRideRequested(RideRequest request, LocalDateTime time) {
        requestTimes.put(request.getCustomerId(), time);
    }

    /**
     * Records when a ride was assigned to a driver.
     * Calculates and stores wait time.
     */
    public void recordRideAssigned(RideRequest request, LocalDateTime time) {
        assignmentTimes.put(request.getCustomerId(), time);

        LocalDateTime requestTime = requestTimes.get(request.getCustomerId());
        if (requestTime != null) {
            Duration waitDuration = Duration.between(requestTime, time);
            double waitMinutes = waitDuration.toMillis() / 60000.0;
            waitTimes.add(waitMinutes);
        }
    }

    /**
     * Records when a ride was completed.
     */
    public void recordRideCompleted(RideRequest request, LocalDateTime time) {
        rideDurations.add(request.getRideDuration());
    }

    /**
     * Calculates average wait time using functional programming.
     */
    public double getAverageWaitTime() {
        return waitTimes.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    /**
     * Calculates average ride duration using functional programming.
     */
    public double getAverageRideDuration() {
        return rideDurations.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    /**
     * Calculates average rides per driver using functional programming.
     */
    public double getAverageRidesPerDriver() {
        return drivers.stream()
                .mapToInt(Driver::getTotalRidesCompleted)
                .average()
                .orElse(0.0);
    }

    /**
     * Gets maximum wait time using functional programming.
     */
    public double getMaxWaitTime() {
        return waitTimes.stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0.0);
    }

    /**
     * Gets total rides completed.
     */
    public int getTotalRidesCompleted() {
        return rideDurations.size();
    }

    /**
     * Prints comprehensive statistics report.
     */
    public void printReport() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("SIMULATION STATISTICS REPORT");
        System.out.println("=".repeat(60));
        System.out.printf("Total Rides Completed: %d%n", getTotalRidesCompleted());
        System.out.printf("Average Wait Time: %.2f minutes%n", getAverageWaitTime());
        System.out.printf("Maximum Wait Time: %.2f minutes%n", getMaxWaitTime());
        System.out.printf("Average Ride Duration: %.2f minutes%n", getAverageRideDuration());
        System.out.printf("Average Rides per Driver: %.2f%n", getAverageRidesPerDriver());
        System.out.println("=".repeat(60) + "\n");
    }
}