package cli;

import iit.lk.raj.model.Customer;
import iit.lk.raj.model.Event;
import iit.lk.raj.model.Ticket;

import java.util.Scanner;

public class Cli {
    private int totalTickets;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the total number of tickets: ");
        int totalTickets = Integer.parseInt(s.next());
        s.nextLine(); //To clear the buffer
        System.out.println("Enter the event name: ");
        String eventName=s.next(); //To clear the buffer
        Event event=new Event(eventName,totalTickets); //Creating an event object
        for(int i=0;i<10;i++){
            Customer customer = new Customer("Customer"+i);
            Ticket ticket = new Ticket(customer,event);
        }



    }


}
