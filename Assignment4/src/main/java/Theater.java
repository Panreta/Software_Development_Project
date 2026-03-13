import java.util.ArrayList;
import java.util.List;

public class Theater {
    private String name;
    private List<Row> rows;
    private int wheelChairLocation;

    public Theater(String name, int numRows, int seatsPerRow, int wheelChairLocation) {
        this.name = name;
        this.rows = new ArrayList<>();
        this.wheelChairLocation = wheelChairLocation;

        if (wheelChairLocation < 1 || wheelChairLocation > numRows){
            throw new IllegalArgumentException("Wheelchair location must be between 1 and " + numRows);
        }
        // Create all rows
        for (int i = 1; i <= numRows; i++) {
            if(i == wheelChairLocation){rows.add(new Row(i, seatsPerRow, true));}     // wheelChair location
            else{rows.add(new Row(i, seatsPerRow, false));} // Still from the 0 to inplement, not 1

        }

    }

    public String getName() {
        return name;
    }

    // Reserve consecutive seats in the first available row
    public String reserveSeats(int numSeats, String name) {
        int totalRows = rows.size(); // Total number of rows in the theater
        int centerSeats = totalRows / 2; // 0-based if it is
        // if it is odd, let's say 15, centerseats == 7, offset is from 0 - 7, which makes chosenseat from 0 - 14 but it should be 1-15;
        // if it is even, let's say 14, centerseats == 7, offest is from 0 - 7, which makes chosenseat from 0 - 14, but row is only 0 - 13 in index, so

        // Doing this without the wheel-chair
        for (int offset  = 0; offset  <= centerSeats; offset ++) {
            int chosenSeat = centerSeats + offset;
            if(chosenSeat <= totalRows){
                Row row = rows.get(chosenSeat); // use the number to get the row you want
                if(!row.isWheelchairAccessible() && hasConsecutiveSeats(row,numSeats)){
                    reserveConsecutiveSeats(row,numSeats,name);
                    return "I've reserved " + numSeats + " seats for you at the " +
                            this.name + " in row " + row.getRowNumber() + ", " + name + ".";
                }
            }

            if(offset > 0){
                chosenSeat = centerSeats - offset;
                if(chosenSeat >= 0){
                    Row row = rows.get(chosenSeat);
                    if (!row.isWheelchairAccessible() && hasConsecutiveSeats(row,numSeats)) {
                        reserveConsecutiveSeats(row,numSeats,name);
                        return "I've reserved " + numSeats + " seats for you at the " +
                                this.name + " in row " + row.getRowNumber() + ", " + name + ".";
                    }

                }

            }
        }

        // do it on the wheelchair row
        for(Row row : rows){
            if(row.isWheelchairAccessible() && hasConsecutiveSeats(row,numSeats)){
                reserveConsecutiveSeats(row,numSeats,name);
                return "I've reserved " + numSeats + " seats for you at the " +
                        this.name + " in row " + row.getRowNumber() + ", " + name + ".";
            }
        }



        return "Sorry, we don't have " + numSeats + " seats together.";
    }

    public String reserveSeats(int numSeats, String name,String wantWheelChair) {
        for (Row row : rows) { //row.isWheelchairAccessible()
            if(row.isWheelchairAccessible()){
                if (hasConsecutiveSeats(row, numSeats)) {
                    reserveConsecutiveSeats(row, numSeats, name);
                    return "I've reserved " + numSeats + " seats for you at the " +
                            this.name + " in row " + row.getRowNumber() + ", " + name + ".";
                }
            }
        }
        return "Sorry, we don't have " + numSeats + " seats together.";
    }

    // Check if row has enough consecutive available seats
    private boolean hasConsecutiveSeats(Row row, int numSeats) {
        int consecutive = 0;
        for (Seat seat : row.getSeats()) {
            if (!seat.isReserved()) {
                consecutive++;
                if (consecutive >= numSeats) {
                    return true;
                }
            } else {
                consecutive = 0;
            }
        }
        return false;
    }

    // Reserve the first available consecutive seats
    private void reserveConsecutiveSeats(Row row, int numSeats, String name) {
        int reserved = 0;
        for (Seat seat : row.getSeats()) {
            if (!seat.isReserved() && reserved < numSeats) {
                seat.setOwnerName(name);
                reserved++;
            }
        }
    }

    // Display the theater layout
    public void show() {
        for (Row row : rows) {
            System.out.print(row.getRowNumber() + " ");

            for (Seat seat : row.getSeats()) {
                if (seat.isReserved()) {
                    System.out.print("X ");
                } else if (row.isWheelchairAccessible()) {
                    System.out.print("= ");
                } else {
                    System.out.print("_ ");
                }
            }

            System.out.println();
        }
    }
}