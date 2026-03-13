import java.util.Scanner;

public class ReservationsService {

    public void startReservationSystem(Theater theater){
        Scanner scanner = new Scanner(System.in);
        boolean running  = true;

        while (running) {
            System.out.print("What would you like to do? ");
            String input = scanner.nextLine().trim();

            if (input.startsWith("reserve")) {
                try{
                    int numSeats = Integer.parseInt(input.substring(8));


                    System.out.print("What's your name? ");
                    String name = scanner.nextLine().trim();
                    while (name.isEmpty()){ // if don't give a string to be the name
                        System.out.print("Sorry,what's your name? ");
                        name = scanner.nextLine().trim();
                    }

                    // Wheelchair
                    System.out.print("Do you need wheelchair accessible seats?" );
                    String wantWheelChair = scanner.nextLine().trim();

                    if (wantWheelChair.equalsIgnoreCase("yes")) {
                        String result = theater.reserveSeats(numSeats, name, wantWheelChair);
                        System.out.println(result);

                    }
                    else{
                        String result = theater.reserveSeats(numSeats, name);
                        System.out.println(result);
                    }

                }
                catch (NumberFormatException e){ // Comes from the default class
                    System.out.println("Invalid number of seats.");
                }
            } else if(input.equals("show")){theater.show();}
            else if(input.equals("done") || input.equals("Done") || input.equals("DONE")){
                System.out.println("Thank you for using the reservation system!");
                running = false;
            }
            else{System.out.println("Invalid command. Please try again.");};
            System.out.println();
        }
        scanner.close();
    }
}
