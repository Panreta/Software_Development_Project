package com.rideshare.events;

import com.rideshare.model.RideRequest;

import java.time.LocalDateTime;

public class RideRequestedEvent extends RideEvent {
    private final RideRequest request;

    public RideRequestedEvent(LocalDateTime eventTime, RideRequest request) {
        super(eventTime, EventType.RIDE_REQUESTED);
        this.request = request;
    }

    public RideRequest getRequest() {
        return request;
    }

    @Override
    public void process(SimulationContext context) {
        context.handleRideRequested(request); // really part do deal with
    }

    @Override
    public String toString() {
        return String.format("RideRequestedEvent[time=%s, request=%s]", eventTime, request);
    }
}
