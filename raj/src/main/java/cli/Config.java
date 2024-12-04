package cli;

public class Config {

    private int totalTickets;
    private String eventName;
    private double ticketPrice;
    private int vendorCount;
    private int ticketRetrivalRate;
    private int customerRetrivalRate;

    // Constructor
    public Config(int totalTickets, String eventName, double ticketPrice,
                  int vendorCount, int ticketRetrivalRate, int customerRetrivalRate) {
        this.totalTickets = totalTickets;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
        this.vendorCount = vendorCount;
        this.ticketRetrivalRate = ticketRetrivalRate;
        this.customerRetrivalRate = customerRetrivalRate;
    }

    // Getters and Setters
    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public void setVendorCount(int vendorCount) {
        this.vendorCount = vendorCount;
    }

    public int getTicketRetrivalRate() {
        return ticketRetrivalRate;
    }

    public void setTicketRetrivalRate(int ticketRetrivalRate) {
        this.ticketRetrivalRate = ticketRetrivalRate;
    }

    public int getCustomerRetrivalRate() {
        return customerRetrivalRate;
    }

    public void setCustomerRetrivalRate(int customerRetrivalRate) {
        this.customerRetrivalRate = customerRetrivalRate;
    }
}
