public class ReservationSystem {
    public static void main(String[] args){
        Theater theater = new Theater("Roxy",15,10,5);//150 seats

        ReservationsService  service = new ReservationsService ();

        service.startReservationSystem(theater);
    }
}
