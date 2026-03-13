package Model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class QuickTest {


    public static void main(String[] args) {


        // Create a used car
        MakeModel honda = new MakeModel("Honda", "Civic");
        UsedCar car1 = new UsedCar("UC001", 2020, honda, 25000.00, 50000, 2, 1);
        System.out.println("Created: " + honda.getMake() + " " + honda.getModel());
        System.out.println("Mileage: " + car1.getMileage());

        // Create a new car
        MakeModel toyota = new MakeModel("Toyota", "Corolla");
        NewCar car2 = new NewCar("NC001", 2024, toyota, 30000.00, 10);
        System.out.println("\nCreated: " + toyota.toString());
        System.out.println("Available: " + car2.getAvailableWithin50Miles());

        // Create a boat
        MakeModel yamaha = new MakeModel("Yamaha", "242X");
        Boat boat = new Boat("BT001", 2023, yamaha, 75000.00, 24.0f, 8, PropulsionType.JET_PROPULSION);
        System.out.println("\nCreated: " + yamaha.toString());
        System.out.println("Propulsion: " + boat.getPropulsionType());

        System.out.println("\n✅ Everything works!");
    }
}