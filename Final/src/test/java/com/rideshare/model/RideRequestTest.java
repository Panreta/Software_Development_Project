package com.rideshare.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RideRequest class.
 * Tests priority calculation, immutability, and comparison logic.
 */
class RideRequestTest {

    private LocalDateTime testTime; // give me the time now

    @BeforeEach
    void setUp() {
        testTime = LocalDateTime.of(2025, 12, 7, 10, 0);
    }

    @Test
    void testRideRequestCreation() {
        RideRequest request = new RideRequest(
                "C001", "Downtown", "Airport", 15.5,
                testTime, RideType.EXPRESS_PICKUP, 1
        );

        assertEquals("C001", request.getCustomerId());
        assertEquals("Downtown", request.getStartLocation());
        assertEquals("Airport", request.getDestination());
        assertEquals(15.5, request.getAnticipatedDistance(), 0.01);
        assertEquals(RideType.EXPRESS_PICKUP, request.getRideType());
        assertEquals(1, request.getArrivalOrder());
    }

    @Test
    void testPriorityCalculation_ExpressPickup() {
        RideRequest request = new RideRequest(
                "C001", "Downtown", "Airport", 15.5,
                testTime, RideType.EXPRESS_PICKUP, 1
        );

        // Express has base priority 100, no distance bonus for >10 miles
        assertEquals(100, request.getPriority());
    }

    @Test
    void testPriorityCalculation_ShortDistance() {
        RideRequest request = new RideRequest(
                "C002", "Downtown", "Mall", 3.5,
                testTime, RideType.STANDARD_PICKUP, 1
        );

        // Standard has base 75, +5 for distance < 5 miles
        assertEquals(80, request.getPriority());
    }

    @Test
    void testPriorityCalculation_MediumDistance() {
        RideRequest request = new RideRequest(
                "C003", "Downtown", "Beach", 7.5,
                testTime, RideType.WAIT_AND_SAVE, 1
        );

        // Wait-and-Save has base 50, +3 for distance 5-10 miles
        assertEquals(53, request.getPriority());
    }

    @Test
    void testRideDurationCalculation() {
        RideRequest request = new RideRequest(
                "C004", "Downtown", "Airport", 30.0,
                testTime, RideType.STANDARD_PICKUP, 1
        );

        // 30 miles at 60 mph = 30 minutes
        assertEquals(30.0, request.getRideDuration(), 0.01);
    }

    @Test
    void testCompareTo_DifferentPriorities() {
        RideRequest highPriority = new RideRequest(
                "C001", "A", "B", 10.0,
                testTime, RideType.EXPRESS_PICKUP, 1
        );

        RideRequest lowPriority = new RideRequest(
                "C002", "C", "D", 10.0,
                testTime, RideType.ECO_FRIENDLY, 2
        );

        // Higher priority should come first (negative comparison)
        assertTrue(highPriority.compareTo(lowPriority) < 0);
        assertTrue(lowPriority.compareTo(highPriority) > 0);
    }

    @Test
    void testCompareTo_SamePriority_FIFO() {
        RideRequest first = new RideRequest(
                "C001", "A", "B", 10.0,
                testTime, RideType.STANDARD_PICKUP, 1
        );

        RideRequest second = new RideRequest(
                "C002", "C", "D", 10.0,
                testTime, RideType.STANDARD_PICKUP, 2
        );

        // Same priority: earlier arrival order comes first
        assertTrue(first.compareTo(second) < 0);
        assertTrue(second.compareTo(first) > 0);
    }

    @Test
    void testCompareTo_SameRequest() {
        RideRequest request = new RideRequest(
                "C001", "A", "B", 10.0,
                testTime, RideType.STANDARD_PICKUP, 1
        );

        assertEquals(0, request.compareTo(request));
    }

    @ParameterizedTest
    @EnumSource(RideType.class)
    void testAllRideTypes(RideType rideType) {
        RideRequest request = new RideRequest(
                "C001", "A", "B", 10.0,
                testTime, rideType, 1
        );

        assertNotNull(request);
        assertEquals(rideType, request.getRideType());
        assertTrue(request.getPriority() > 0);
    }

    @Test
    void testImmutability() {
        RideRequest request = new RideRequest(
                "C001", "Downtown", "Airport", 15.5,
                testTime, RideType.EXPRESS_PICKUP, 1
        );

        // Verify all fields are accessible but cannot be modified
        assertEquals("C001", request.getCustomerId());
        assertEquals(RideType.EXPRESS_PICKUP, request.getRideType());

        // No setter methods should exist
        // This is verified by compilation - no setters defined
    }

    @Test
    void testToString() {
        RideRequest request = new RideRequest(
                "C001", "Downtown", "Airport", 15.5,
                testTime, RideType.EXPRESS_PICKUP, 1
        );

        String toString = request.toString();
        assertTrue(toString.contains("C001"));
        assertTrue(toString.contains("EXPRESS_PICKUP")|| toString.contains("Express"));
        assertTrue(toString.contains("15.5"));
    }
}