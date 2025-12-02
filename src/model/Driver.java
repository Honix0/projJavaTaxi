package model;
import java.time.LocalDate;

public class Driver extends Person {

    public Driver(int id, String firstName, String lastName, LocalDate birthDate, String sex, String email, String phoneNumber, String passwordHash) {
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

    public String toString() {
        return "Driver{" + "id=" + getId() + ", firstName='" + getFirstName() + '\'' + ", lastName='" + getLastName() + '\'' + ", birthDate=" + getBirthDate() + ", sex='" + getSex() + '\'' + ", email='" + getEmail() + '\'' + ", phoneNumber='" + getPhoneNumber() + '\'' + '}';
    }
}
