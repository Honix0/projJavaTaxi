package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client extends Person { // Wymaganie: #2 (Hierarchia Person)

    private String address;
    private Order activeOrder; // Aktywne zamówienie klienta

    private List<String> rideHistory = new ArrayList<>(); // Wymaganie: #16 Kolekcje

    public Client(int id, String firstName, String lastName, LocalDate birthDate, String sex, String email, String phoneNumber, String passwordHash, String address) {
        super(id, firstName, lastName, birthDate, sex, email, phoneNumber, passwordHash);
        this.address = address;
        this.activeOrder = null;
    }

    public String getAddress() { return this.address; }
    public void setAddress(String address) { this.address = address; }

    public Order getActiveOrder() { return activeOrder; }
    public void setActiveOrder(Order activeOrder) { this.activeOrder = activeOrder; } // Wymaganie: #12

    public void addRideToHistory(String rideDetails) { // Wymaganie: #12 Metoda z funkcjonalnością
        rideHistory.add(rideDetails);
    }

    public void printHistory() { // Wymaganie: #12 Metoda z funkcjonalnością
        System.out.println("\nTWOJA HISTORIA PRZEJAZDÓW");
        if (rideHistory.isEmpty()) {
            System.out.println("Brak odbytych przejazdów.");
        } else {
            // Wymaganie: #4 Pętla
            for (String ride : rideHistory) {
                System.out.println(ride);
            }
        }
    }

    public String getRole() {
        return "CLIENT";
    }

    // Wymaganie: #10 Nadpisana metoda toString
    public String toString() {
        return super.toString() + " KLIENT (Adres: " + this.address + ")";
    }
}