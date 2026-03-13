package Model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class for the Vehicle Valuation System.
 * Tests the creation and functionality of different vehicle types.
 *
 * @author Vehicle Valuation System Test Suite
 * @version 1.0
 */
  public class VehicleSystemTest {

    private UsedCar usedCar;
    private NewCar newCar;
    private Boat boat;

    /**
     * Sets up test fixtures before each test method.
     * Initializes sample vehicles for testing.# Look for test results
     * type build\reports\tests\test\index.html
     */
    @BeforeEach
      void setUp() {
        // Create a used car
        MakeModel usedCarMakeModel = new MakeModel("Honda", "Accord");
        usedCar = new UsedCar(
                "UC2021001",           // ID
                2021,                  // Manufacturing year
                usedCarMakeModel,      // Make and model
                28000.00,              // MSRP
                45000,                 // Mileage
                2,                     // Number of previous owners
                1                      // Number of minor accidents
        );

        // Create a new car
        MakeModel newCarMakeModel = new MakeModel("Toyota", "Camry");
        newCar = new NewCar(
                "NC2024001",           // ID
                2024,                  // Manufacturing year
                newCarMakeModel,       // Make and model
                35000.00,              // MSRP
                15                     // Available within 50 miles
        );

        // Create a boat
        MakeModel boatMakeModel = new MakeModel("Sea Ray", "Sundancer 320");
        boat = new Boat(
                "BT2023001",           // ID
                2023,                  // Manufacturing year
                boatMakeModel,         // Make and model
                150000.00,             // MSRP
                32.5f,                 // Length in feet
                10,                    // Number of passengers
                PropulsionType.INBOARD_ENGINE  // Propulsion type
        );
    }

    /**
     * Tests the MakeModel class functionality.
     */
    @Test
      void testMakeModel() {
        MakeModel makeModel = new MakeModel("Ford", "F-150");

        assertEquals("Ford", makeModel.getMake());
        assertEquals("F-150", makeModel.getModel());
        assertEquals("Ford F-150", makeModel.toString());

        // Test setters
        makeModel.setMake("Chevrolet");
        makeModel.setModel("Silverado");
        assertEquals("Chevrolet", makeModel.getMake());
        assertEquals("Silverado", makeModel.getModel());
    }

    /**
     * Tests the PropulsionType enumeration.
     */
    @Test
      void testPropulsionType() {
        assertEquals("Sail Power", PropulsionType.SAIL_POWER.getDisplayName());
        assertEquals("Inboard Engine", PropulsionType.INBOARD_ENGINE.getDisplayName());
        assertEquals("Outboard Engine", PropulsionType.OUTBOARD_ENGINE.getDisplayName());
        assertEquals("Jet Propulsion", PropulsionType.JET_PROPULSION.getDisplayName());

        // Test toString
        assertEquals("Inboard Engine", PropulsionType.INBOARD_ENGINE.toString());
    }

    /**
     * Tests UsedCar creation and all its properties.
     */
    @Test
      void testUsedCar() {
        // Test inherited Vehicle properties
        assertEquals("UC2021001", usedCar.getId());
        assertEquals(Integer.valueOf(2021), usedCar.getManufacturingYear());
        assertEquals("Honda", usedCar.getMakeModel().getMake());
        assertEquals("Accord", usedCar.getMakeModel().getModel());
        assertEquals(Double.valueOf(28000.00), usedCar.getMsrp());

        // Test UsedCar specific properties
        assertEquals(Integer.valueOf(45000), usedCar.getMileage());
        assertEquals(Integer.valueOf(2), usedCar.getNumberOfPreviousOwners());
        assertEquals(Integer.valueOf(1), usedCar.getNumberOfMinorAccidents());
        assertEquals("Used Car", usedCar.getVehicleType());

        // Test setters
        usedCar.setMileage(50000);
        usedCar.setNumberOfPreviousOwners(3);
        usedCar.setNumberOfMinorAccidents(2);

        assertEquals(Integer.valueOf(50000), usedCar.getMileage());
        assertEquals(Integer.valueOf(3), usedCar.getNumberOfPreviousOwners());
        assertEquals(Integer.valueOf(2), usedCar.getNumberOfMinorAccidents());
    }

    /**
     * Tests NewCar creation and all its properties.
     */
    @Test
      void testNewCar() {
        // Test inherited Vehicle properties
        assertEquals("NC2024001", newCar.getId());
        assertEquals(Integer.valueOf(2024), newCar.getManufacturingYear());
        assertEquals("Toyota", newCar.getMakeModel().getMake());
        assertEquals("Camry", newCar.getMakeModel().getModel());
        assertEquals(Double.valueOf(35000.00), newCar.getMsrp());

        // Test NewCar specific properties
        assertEquals(Integer.valueOf(15), newCar.getAvailableWithin50Miles());
        assertEquals("New Car", newCar.getVehicleType());

        // Test setters
        newCar.setAvailableWithin50Miles(20);
        assertEquals(Integer.valueOf(20), newCar.getAvailableWithin50Miles());

        // Test inherited setters
        newCar.setId("NC2024002");
        newCar.setManufacturingYear(2025);
        newCar.setMsrp(36000.00);

        assertEquals("NC2024002", newCar.getId());
        assertEquals(Integer.valueOf(2025), newCar.getManufacturingYear());
        assertEquals(Double.valueOf(36000.00), newCar.getMsrp());
    }

    /**
     * Tests Boat creation and all its properties.
     */
    @Test
      void testBoat() {
        // Test inherited Vehicle properties
        assertEquals("BT2023001", boat.getId());
        assertEquals(Integer.valueOf(2023), boat.getManufacturingYear());
        assertEquals("Sea Ray", boat.getMakeModel().getMake());
        assertEquals("Sundancer 320", boat.getMakeModel().getModel());
        assertEquals(Double.valueOf(150000.00), boat.getMsrp());

        // Test Boat specific properties
        assertEquals(Float.valueOf(32.5f), boat.getLength());
        assertEquals(Integer.valueOf(10), boat.getNumberOfPassengers());
        assertEquals(PropulsionType.INBOARD_ENGINE, boat.getPropulsionType());
        assertEquals("Boat", boat.getVehicleType());

        // Test setters
        boat.setLength(35.0f);
        boat.setNumberOfPassengers(12);
        boat.setPropulsionType(PropulsionType.OUTBOARD_ENGINE);

        assertEquals(Float.valueOf(35.0f), boat.getLength());
        assertEquals(Integer.valueOf(12), boat.getNumberOfPassengers());
        assertEquals(PropulsionType.OUTBOARD_ENGINE, boat.getPropulsionType());
    }

    /**
     * Tests polymorphism - treating different vehicles as Vehicle type.
     */
    @Test
      void testPolymorphism() {
        Vehicle[] vehicles = {usedCar, newCar, boat};

        // All should be instances of Vehicle
        for (Vehicle vehicle : vehicles) {
            assertNotNull(vehicle.getId());
            assertNotNull(vehicle.getManufacturingYear());
            assertNotNull(vehicle.getMakeModel());
            assertNotNull(vehicle.getMsrp());
            assertNotNull(vehicle.getVehicleType());
        }

//        // Test instanceof checks
//        assertTrue(usedCar instanceof Car);
//        assertTrue(usedCar instanceof Vehicle);
//        assertTrue(newCar instanceof Car);
//        assertTrue(newCar instanceof Vehicle);
//        assertTrue(boat instanceof Vessel);
//        assertTrue(boat instanceof Vehicle);


    }

    /**
     * Tests edge cases and boundary conditions.
     */
    @Test
      void testEdgeCases() {
        // Test with zero values
        UsedCar zeroMileageCar = new UsedCar(
                "UC2024002",
                2024,
                new MakeModel("Tesla", "Model 3"),
                45000.00,
                0,      // Zero mileage
                0,      // Zero previous owners (brand new but being sold as used)
                0       // Zero accidents
        );

        assertEquals(Integer.valueOf(0), zeroMileageCar.getMileage());
        assertEquals(Integer.valueOf(0), zeroMileageCar.getNumberOfPreviousOwners());
        assertEquals(Integer.valueOf(0), zeroMileageCar.getNumberOfMinorAccidents());

        // Test with different propulsion types
        Boat sailBoat = new Boat(
                "BT2023002",
                2023,
                new MakeModel("Catalina", "385"),
                200000.00,
                38.5f,
                8,
                PropulsionType.SAIL_POWER
        );

        assertEquals(PropulsionType.SAIL_POWER, sailBoat.getPropulsionType());
        assertEquals("Sail Power", sailBoat.getPropulsionType().toString());
    }

    /**
     * Tests updating vehicle information.
     */
    @Test
      void testVehicleUpdates() {
        // Update MakeModel
        MakeModel newMakeModel = new MakeModel("Lexus", "ES 350");
        usedCar.setMakeModel(newMakeModel);

        assertEquals("Lexus", usedCar.getMakeModel().getMake());
        assertEquals("ES 350", usedCar.getMakeModel().getModel());

        // Update multiple properties
        usedCar.setId("UC2021002");
        usedCar.setManufacturingYear(2020);
        usedCar.setMsrp(26000.00);
        usedCar.setMileage(55000);

        assertEquals("UC2021002", usedCar.getId());
        assertEquals(Integer.valueOf(2020), usedCar.getManufacturingYear());
        assertEquals(Double.valueOf(26000.00), usedCar.getMsrp());
        assertEquals(Integer.valueOf(55000), usedCar.getMileage());
    }
}