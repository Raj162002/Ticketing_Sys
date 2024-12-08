package cli;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import iit.lk.raj.RajApplication;
import iit.lk.raj.model.Customer;
import iit.lk.raj.model.Event;
import iit.lk.raj.model.Vendor;
import iit.lk.raj.model.multithreaded.CustomerThreaded;
import iit.lk.raj.model.multithreaded.VendorThreaded;
import iit.lk.raj.service.CustomerService;
import iit.lk.raj.service.EventService;
import iit.lk.raj.service.TicketService;
import iit.lk.raj.service.VendorService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cli {

    private static final String configFilePath = "CLI_simulation_config.json";

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(RajApplication.class);
        Scanner s = new Scanner(System.in);

        System.out.println("Welcome to the event management system");

        Config config = null;

        // Check if config.json exists and is valid
        if (new File(configFilePath).exists()) {
            System.out.println("Do you want to use the previous configuration? (y/n)");
            String choice = s.nextLine().trim().toLowerCase();

            if (choice.equals("y")) {
                config = loadConfigFromFile();
            }
        }

        // If the config is still null, we need to create a new config
        if (config == null) {
            System.out.println("Creating a new configuration.");
            int totalTickets = getIntInput(s, "Enter the total number of tickets per vendor for the event:");
            String eventName = getStringInput(s, "Enter the event name: ");
            double eventTicketPrice = getDoubleInput(s, "Enter the event normal ticket price: ");
            int vendorCount = getIntInput(s, "Enter the number of vendors: ");
            int ticketRetrivalRate = getIntInput(s, "Enter the ticket retrieval rate:");
            int customerRetrivalRate = getIntInput(s, "Enter the customer retrieval rate:");

            // Create Config object from user input
            config = new Config(totalTickets, eventName, eventTicketPrice, vendorCount, ticketRetrivalRate, customerRetrivalRate);

            // Save this new config to file
            saveConfigToFile(config);
        }

        // Use the config for event setup
        Event event = new Event(config.getEventName(), config.getTotalTickets(), config.getTicketPrice());
        EventService eventService = context.getBean(EventService.class);
        eventService.createEvent(event);

        VendorService vendorService = context.getBean(VendorService.class);
        TicketService ticketService = context.getBean(TicketService.class);
        ticketService.setTicketRetrivalRate(config.getTicketRetrivalRate());
        ticketService.setCustomerRetrivalRate(config.getCustomerRetrivalRate());

        CustomerService customerService = context.getBean(CustomerService.class);

        // List to store references to threads
        List<Thread> vendorThreads = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();

        // Simulate vendor threads
        for (int i = 0; i < config.getVendorCount(); i++) {
            Vendor vendor = new Vendor("Simulator Vendor " + i, "Test@gmail.com", 0771234567L, "1234");
            VendorThreaded vendorThreaded = new VendorThreaded(vendor, vendorService, event, config.getTotalTickets(), ticketService);
            Thread t1 = new Thread(vendorThreaded);
            t1.setName("Vendor Thread " + i);
            vendorThreads.add(t1);  // Add thread to the list
            t1.start();
        }
        Thread.sleep(2000);

        // Simulate customer threads
        for (int i = 0; i < config.getTotalTickets() * config.getVendorCount(); i++) {
            Customer customer = new Customer("Simulator Customer " + i, "TestM@gmail.com", 23L, "1234");
            CustomerThreaded customerThreaded = new CustomerThreaded(ticketService, customer, customerService,event);
            Thread t2 = new Thread(customerThreaded);
            t2.setName("Customer Thread " + i);
            customerThreads.add(t2);
            t2.start();
        }

        // Join all threads to wait for them to finish
        for (Thread t1 : vendorThreads) {
            t1.join();
            Thread.sleep(1000); // Wait for each thread to finish
        }
        System.out.println("All vendor threads have finished.");

        for (Thread t2 : customerThreads) {
            t2.join();
            Thread.sleep(1000); // Wait for each thread to finish
        }

        SpringApplication.exit(context);
    }

    public static String getStringInput(Scanner s, String message) {
        System.out.println(message);
        return s.nextLine();
    }

    public static int getIntInput(Scanner s, String message) {
        while (true) {
            try {
                System.out.println(message);
                int input = s.nextInt();
                s.nextLine(); // Consume newline character
                return input;
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                s.nextLine(); // Consume invalid input
            }
        }
    }

    public static double getDoubleInput(Scanner s, String message) {
        while (true) {
            try {
                System.out.println(message);
                double input = s.nextDouble();
                s.nextLine(); // Consume newline character
                return input;
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Save the Config object to a JSON file
    private static void saveConfigToFile(Config config) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(configFilePath)) {
            gson.toJson(config, writer);
            System.out.println("Config data saved to " + configFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the Config object from a JSON file
    private static Config loadConfigFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(configFilePath)) {
            return gson.fromJson(reader, Config.class);
        } catch (IOException | JsonSyntaxException e) {
            System.out.println("Error reading or parsing the config file. Creating a new config.");
            return null; // Return null if the file is empty or corrupted
        }
    }
}
