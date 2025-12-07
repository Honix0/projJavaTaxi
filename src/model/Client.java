package model;

import java.time.LocalDate;

public class Client extends Person {
    public Client(int id, String firstName, String lastName, LocalDate birthDate, String sex, String email, String phoneNumber, String passwordHash) {
        super(id, firstName, lastName, birthDate, sex, email, phoneNumber, passwordHash);
    }
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    // Сеттеры (если нужно изменять данные)
    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public String toString() {
        return "Klient{" + "id=" + getId() + ", firstName='" + getFirstName() + '\'' + ", lastName='" + getLastName() + '\'' + ", birthDate=" + getBirthDate() + ", sex='" + getSex() + '\'' + ", email='" + getEmail() + '\'' + ", phoneNumber='" + getPhoneNumber() + '\'' + '}';
    }
}
