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

    // --- ГЕТТЕРЫ (Чтение) ---
    public int getId() { return this.id; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public LocalDate getBirthDate() { return this.birthDate; }
    public String getSex() { return this.sex; }
    public String getEmail() { return this.email; }
    public String getPhoneNumber() { return this.phoneNumber; }
    public String getPasswordHash() { return this.passwordHash; }

    // --- СЕТТЕРЫ (Изменение данных - полезно для проекта) ---
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // --- АБСТРАКТНЫЙ МЕТОД ---
    public abstract String getRole();

    // --- TO STRING ---
    @Override
    public String toString() {
        return this.id + ": " + this.firstName + " " + this.lastName + " (" + this.email + ")";
    }
}