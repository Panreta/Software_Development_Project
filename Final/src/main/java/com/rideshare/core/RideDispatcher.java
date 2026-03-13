package com.rideshare.core;

import com.rideshare.events.*;
import com.rideshare.model.Driver;
import com.rideshare.model.RideRequest;
import com.rideshare.observer.RideEventObserver;
import com.rideshare.stats.RideStatistics;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Core dispatcher - handles ride assignments and event processing.
 */
public class RideDispatcher implements SimulationContext {
    private final List<Driver> drivers;
    private final PriorityQueue<RideRequest> pendingRequests;
    private final PriorityQueue<RideEvent> eventQueue;
    private final RideStatistics statistics;
    private final List<RideEventObserver> observers;

    public RideDispatcher(int numDrivers) {
        this.drivers = new CopyOnWriteArrayList<>();//protect thread
        for (int i = 1; i <= numDrivers; i++) {
            drivers.add(new Driver("Driver" + String.format("%03d", i)));
        }

        this.pendingRequests = new PriorityQueue<>();
        this.eventQueue = new PriorityQueue<>();
        this.statistics = new RideStatistics(drivers); // TODO: More?
        this.observers = new ArrayList<>();
    }

    public void addEvent(RideEvent event) {
        eventQueue.offer(event); // adds an element to a queue, return true or false
    }

    public void processAllEvents() throws InterruptedException {
        while (!eventQueue.isEmpty()) {
            RideEvent event = eventQueue.poll();
            if (event != null) {
                event.process(this);
            }
        }
    }

    @Override
    public void handleRideRequested(RideRequest request) {
        statistics.recordRideRequested(request, request.getRequestTimestamp());
        notifyObservers(obs -> obs.onRideRequested(request, request.getRequestTimestamp()));

        Driver availableDriver = findAvailableDriver();
        if (availableDriver != null) {
            assignRideToDriver(request, availableDriver, request.getRequestTimestamp());
        } else {
            pendingRequests.offer(request);
        }
    }

    @Override
    public void handleRideFinished(Driver driver, RideRequest completedRide) {
        driver.completeRide();
        LocalDateTime completionTime = completedRide.getRequestTimestamp()
                .plusMinutes((long) completedRide.getRideDuration());

        statistics.recordRideCompleted(completedRide, completionTime);
        notifyObservers(obs -> obs.onRideCompleted(completedRide, driver, completionTime));

        if (!pendingRequests.isEmpty()) {
            RideRequest nextRequest = pendingRequests.poll();
            assignRideToDriver(nextRequest, driver, completionTime);
        }
    }

    private void assignRideToDriver(RideRequest request, Driver driver, LocalDateTime assignmentTime) {
        driver.assignRide(request);
        statistics.recordRideAssigned(request, assignmentTime);
        notifyObservers(obs -> obs.onRideAssigned(request, driver, assignmentTime));

        LocalDateTime completionTime = assignmentTime.plusMinutes((long) request.getRideDuration());
        RideEvent completionEvent = RideEventFactory.createRideFinishedEvent(
                completionTime, driver, request);// give me the encapsulate completionTime here
        eventQueue.offer(completionEvent);
    }

    private Driver findAvailableDriver() {
        return drivers.stream()
                .filter(Driver::isAvailable)
                .findFirst()// why do I need this
                .orElse(null);
    }

    private void notifyObservers(java.util.function.Consumer<RideEventObserver> action) {
        observers.forEach(action);// go through all the action
    }

    public RideStatistics getStatistics() {
        return statistics;
    }

    public List<Driver> getDrivers() {
        return new ArrayList<>(drivers);
    }
}