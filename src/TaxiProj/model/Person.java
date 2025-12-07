package model;

import java.time.LocalDate;

public abstract class Person {
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

    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public String getSex() {
        return this.sex;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public abstract String getRole();

    public String toString() {
        return this.id + ": " + this.firstName + " " + this.lastName + " (" + this.email + ")";
    }
}
