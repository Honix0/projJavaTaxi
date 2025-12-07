
package model;

import java.time.LocalDate;

public class Driver extends Person {
    private String driverLicense;
    private double salary;

    public Driver(int id, String firstName, String lastName, LocalDate birthDate, String sex, String email, String phoneNumber, String passwordHash, String driverLicense, double salary) {
        super(id, firstName, lastName, birthDate, sex, email, phoneNumber, passwordHash);
        this.driverLicense = driverLicense;
        this.salary = salary;
    }

    public String getDriverLicense() {
        return this.driverLicense;
    }

    public double getSalary() {
        return this.salary;
    }

    public String getRole() {
        return "DRIVER";
    }

    public String toString() {
        String var10000 = super.toString();
        return var10000 + ", Лицензия: " + this.driverLicense + ", Зарплата: " + this.salary;
    }
}
