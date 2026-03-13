import java.util.ArrayList;
import java.util.List;

public class Row {
    private int rowNumber;
    private boolean wheelchairAccessible;
    private List<Seat> seats;

    public Row(int rowNumber, int numberOfSeats, boolean wheelchairAccessible) {
        if (rowNumber < 1) {
            throw new IllegalArgumentException("Row number must be at least 1");
        }
        if (numberOfSeats < 1 || numberOfSeats > 26) {
            throw new IllegalArgumentException("Number of seats must be between 1 and 26");
        }
        this.rowNumber = rowNumber;
        this.wheelchairAccessible = wheelchairAccessible;
        this.seats = new ArrayList<>();

        for (int i = 0; i < numberOfSeats; i++) {
            char seatLetter = (char) ('A' + i);
            seats.add(new Seat(String.valueOf(seatLetter), null));
        }
    }

    // Getters
    public int getRowNumber() {
        return rowNumber;
    }

    public boolean isWheelchairAccessible() {
        return wheelchairAccessible;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setWheelchairAccessible(boolean wheelchairAccessible) {
        this.wheelchairAccessible = wheelchairAccessible;
    }


    //Method to manage
    public Seat getSeat(String seatName) {
        for (Seat seat : seats) {
            if (seat.getSeatName().equals(seatName)) {
                return seat;
            }
        }
        throw new IllegalArgumentException("Seat " + seatName + " does not exist in this row.");
    }

}
