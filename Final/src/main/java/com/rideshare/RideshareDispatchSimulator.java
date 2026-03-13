package com.rideshare;

import com.rideshare.core.RideDispatcher;
import com.rideshare.events.RideEvent;
import com.rideshare.events.RideEventFactory;
import com.rideshare.factory.RideRequestFactory;
import com.rideshare.model.RideRequest;
import com.rideshare.stats.RideStatistics;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

/**
 * Main simulation class for the Rideshare Dispatch Simulator.
 *
 * User Input: Number of drivers (integer)
 * Required Output:
 *   - Average wait time for a ride (in minutes)
 *   - Average number of rides per driver
 *   - Additional statistics
 */
public class RideshareDispatchSimulator {
    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=".repeat(60));
        System.out.println("RIDESHARE DISPATCH SIMULATOR");
        System.out.println("=".repeat(60));

        // User Input: Number of drivers
        System.out.print("\nEnter number of drivers (integer): ");
        int numDrivers = scanner.nextInt();

        System.out.println("\nRunning Test Scenarios with " + numDrivers + " drivers...\n");

        // Run the three required test scenarios
        runScenario(numDrivers, 25);
        runScenario(numDrivers, 100);
        runScenario(numDrivers, 250);

        scanner.close();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("ALL SCENARIOS COMPLETED");
        System.out.println("=".repeat(60));
    }

    /**
     * Runs a single simulation scenario.
     *
     * Required Output for each scenario:
     *   - Average wait time for a ride (in minutes)
     *   - Average number of rides per driver
     *   - Additional statistics
     */
    private static void runScenario(int numDrivers, int numRides) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("SCENARIO: " + numDrivers + " drivers and " + numRides + " rides");
        System.out.println("=".repeat(60) + "\n");

        try {
            // Reset factory counter for each scenario
            RideRequestFactory.resetCounter();


            // Create dispatcher
            RideDispatcher dispatcher = new RideDispatcher(numDrivers);// TODO: 里面的函数

            System.out.println("Using Poisson process with lambda = " + String.format("%.3f", (double)numRides/120.0) + " requests/min\n");


            // Generate ride requests over 2-hour period
            LocalDateTime startTime = LocalDateTime.now();


            double lambda =  numRides / 120.0; // requests in 2 hours, that's why 120min
            LocalDateTime currentTime = startTime;



            for (int i = 0; i < numRides; i++) {
                double interArrivalMinutes = -Math.log(1 - random.nextDouble()) / lambda; // interArrivalMinutes ~ expo(λ）, on minute

                currentTime = currentTime.plusSeconds((long)(interArrivalMinutes * 60));// lower-integer and add

                RideRequest request = RideRequestFactory.createRandomRideRequest(currentTime, i);

                // Factory Pattern: Create event
                RideEvent event = RideEventFactory.createRideRequestedEvent(currentTime, request); // encapsulate
                dispatcher.addEvent(event); // observe
            }

            System.out.println("Processing " + numRides + " ride requests...");
            long startSimulation = System.currentTimeMillis();//current time in milliseconds since the "epoch"

            // Process all events
            dispatcher.processAllEvents();

            long endSimulation = System.currentTimeMillis();
            System.out.printf("Simulation completed in %.2f seconds%n",
                    (endSimulation - startSimulation) / 1000.0);

            // Required Output: Display statistics
            RideStatistics stats = dispatcher.getStatistics();
            stats.printReport();// to string

        } catch (InterruptedException e) {
            System.err.println("Simulation interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}