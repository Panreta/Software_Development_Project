package com.rideshare.factory;

import com.rideshare.model.RideRequest;
import com.rideshare.model.RideType;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Factory Pattern: Creates RideRequest objects with randomized data.
 * Simplifies creation of test data for simulation.
 */
public class RideRequestFactory {
    private static final String[] LOCATIONS = {
            "Downtown", "Tacoma","Ballrid","Airport","University", "Mall", "Beach",
            "Park", "Station", "Harbor", "Stadium", "Hospital"
    };

    private static final Random random = new Random();
    private static int customerCounter = 1;

    /**
     * Creates a random ride request with specified timestamp and arrival order.
     */
    public static RideRequest createRandomRideRequest(LocalDateTime timestamp, int arrivalOrder) {
        String customerId = "Customer" + String.format("%04d", customerCounter++);
        String startLocation = LOCATIONS[random.nextInt(LOCATIONS.length)];
        String destination;

        // Ensure destination is different from start
        do {
            destination = LOCATIONS[random.nextInt(LOCATIONS.length)];
        } while (destination.equals(startLocation));

        double distance = 1.0 + (random.nextDouble() * 49.0);//linear mapping to 1-50 miles

        // Random ride type
        RideType rideType = RideType.values()[random.nextInt(RideType.values().length)];

        return new RideRequest(customerId, startLocation, destination,
                distance, timestamp, rideType, arrivalOrder);
    }



    /**
     * Resets the customer counter for new simulations.
     */
    public static void resetCounter() {
        customerCounter = 1;
    }
}