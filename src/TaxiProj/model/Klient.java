package model;

import java.time.LocalDate;

public class Klient extends Person {
    private String address;

    public Klient(int id, String firstName, String lastName, LocalDate birthDate, String sex, String email, String phoneNumber, String passwordHash, String address) {
        super(id, firstName, lastName, birthDate, sex, email, phoneNumber, passwordHash);
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return "CLIENT";
    }

    public String toString() {
        String var10000 = super.toString();
        return var10000 + " [CLIENT] (Адрес: " + this.address + ")";
    }
}