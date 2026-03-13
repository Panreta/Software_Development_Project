public class Seat {
    private String seatName;
    private String ownerName;
    public Seat(String SeatName, String OwnerName) {
        if (!isValidSeatName(SeatName)) {
            throw new IllegalArgumentException("SeatName is invalid");
        }
        this.seatName = SeatName;
        this.ownerName = OwnerName;
    }


    private boolean isValidSeatName(String SeatName){
        return SeatName.matches("[A-Z]"); // TODO: NO null check here
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        if (!isValidSeatName(seatName)) {  // Validate before setting
            throw new IllegalArgumentException("SeatName is invalid");
        }

        this.seatName = seatName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public boolean isReserved() {
        return this.ownerName != null;
    }
}
