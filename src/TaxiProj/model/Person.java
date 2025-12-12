package model;

import java.time.LocalDate;
import service.IPrintable; // Wymaganie: #13

// Klasa abstrakcyjna, implementuje IPrintable.
// Wymaganie: #3 Klasa abstrakcyjna, #2 (Hierarchia #1), #13 (IPrintable)
public abstract class Person implements IPrintable {

    // Wymaganie: #6 Dwa pola statyczne
    private static int nextId = 1; // Pole statyczne #1: Licznik ID
    public static final String DEFAULT_SEX = "N/A"; // Pole statyczne #2: Stała

    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String sex;
    private String email;
    private String phoneNumber;
    private String passwordHash;

    public Person(int id, String firstName, String lastName, LocalDate birthDate, String sex, String email, String phoneNumber, String passwordHash) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
    }

    // Wymaganie: #6 Metoda statyczna #1
    public static int generateNextId() { // Wymaganie: #12 Metoda z funkcjonalnością
        return nextId++;
    }

    public int getId() { return this.id; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public LocalDate getBirthDate() { return this.birthDate; }
    public String getSex() { return this.sex; }
    public String getEmail() { return this.email; }
    public String getPhoneNumber() { return this.phoneNumber; }
    public String getPasswordHash() { return this.passwordHash; }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Wymaganie: #3 Metoda abstrakcyjna #1
    public abstract String getRole();

    // Wymaganie: #10 Nadpisana metoda toString
    public String toString() {
        return this.id + ": " + this.firstName + " " + this.lastName + " (" + this.email + ")";
    }

    // Wymaganie: #13 Implementacja metody z interfejsu
    public String getShortInfo() { // Wymaganie: #12 Metoda z funkcjonalnością
        return "ID: " + this.id + ", Rola: " + getRole();
    }
}