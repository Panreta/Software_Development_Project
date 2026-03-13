package com.rideshare.events;

import com.rideshare.model.Driver;
import com.rideshare.model.RideRequest;
import java.time.LocalDateTime;

/**
 * Represents an event in the ride simulation.
 * Implements Comparable for priority queue ordering by event time.
 */
public abstract class RideEvent implements Comparable<RideEvent> {
    protected final LocalDateTime eventTime;
    protected final EventType eventType;

    protected RideEvent(LocalDateTime eventTime, EventType eventType) {
        this.eventTime = eventTime;
        this.eventType = eventType;
    }


    /**
     * Process this event in the simulation.
     */
    public abstract void process(SimulationContext context);

    @Override
    public int compareTo(RideEvent other) {
        return this.eventTime.compareTo(other.eventTime);
    }
}

/**


/**
 * Event when a ride is completed.
 */
