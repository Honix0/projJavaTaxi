package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client extends Person {

    private String address;

    private List<String> rideHistory = new ArrayList<>();

    public Client(int id, String firstName, String lastName, LocalDate birthDate, String sex, String email, String phoneNumber, String passwordHash, String address) {
        super(id, firstName, lastName, birthDate, sex, email, phoneNumber, passwordHash);
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addRideToHistory(String rideDetails) {
        rideHistory.add(rideDetails);
    }

    public void printHistory() {
        System.out.println("\nTWOJA HISTORIA PRZEJAZDÓW");
        if (rideHistory.isEmpty()) {
            System.out.println("Brak odbytych przejazdów.");
        } else {
            for (String ride : rideHistory) {
                System.out.println(ride);
            }
        }
    }

    public String getRole() {
        return "CLIENT";
    }

    public String toString() {
        return super.toString() + " KLIENT (Adres: " + this.address + ")";
    }
}