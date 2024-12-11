package cli;

/*
This is the Config class that is going to be used to update or load the json file
*/

public class Config {

    private int totalTicketsVendor;
    private String eventName;
    private double ticketPrice;
    private int vendorCount;
    private int ticketRetrivalRate;
    private int customerRetrivalRate;

    public Config(int totalTicketsVendor, String eventName, double ticketPrice,
                  int vendorCount, int ticketRetrivalRate, int customerRetrivalRate) {
        this.totalTicketsVendor = totalTicketsVendor;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
        this.vendorCount = vendorCount;
        this.ticketRetrivalRate = ticketRetrivalRate;
        this.customerRetrivalRate = customerRetrivalRate;
    }


    public int getTotalTickets() {
        return totalTicketsVendor;
    }

    public void setTotalTickets(int totalTicketsVendor) {
        this.totalTicketsVendor = totalTicketsVendor;
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
