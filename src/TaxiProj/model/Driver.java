package model;

import java.time.LocalDate;
import enums.OrderStatus;

public class Driver extends Person { // Wymaganie: #2 (Hierarchia Person)
    private String driverLicense;
    private double salary;
    private Car assignedCar; // ZMIANA: Przechowuje obiekt Car (przypisany samochód)
    private int experienceYears;
    private Order activeOrder;
    private LocalDate nextPayDay;
    private OrderStatus status;

    public Driver(int id, String firstName, String lastName, LocalDate birthDate, String sex, String email, String phoneNumber, String passwordHash, String driverLicense, double salary, Car assignedCar, int experienceYears) {
        super(id, firstName, lastName, birthDate, sex, email, phoneNumber, passwordHash);
        this.driverLicense = driverLicense;
        this.salary = salary;
        this.assignedCar = assignedCar;
        this.experienceYears = experienceYears;
        this.activeOrder = null;
        this.nextPayDay = LocalDate.now().plusMonths(1).withDayOfMonth(10);
        this.status = OrderStatus.COMPLETED;
    }

    public String getDriverLicense() { return driverLicense; }
    public double getSalary() { return salary; }
    public Car getAssignedCar() { return assignedCar; } // DODANO: Getter dla przypisanego auta
    public int getExperienceYears() { return experienceYears; }
    public Order getActiveOrder() { return activeOrder; }
    public LocalDate getNextPayDay() { return nextPayDay; }
    public OrderStatus getStatus() { return status; }

    public void setSalary(double salary) { this.salary = salary; }
    public void setDriverLicense(String driverLicense) { this.driverLicense = driverLicense; }
    public void setStatus(OrderStatus status) { this.status = status; }

    // Wymaganie: #12 Metoda z funkcjonalnością
    public void setActiveOrder(Order order) {
        this.activeOrder = order;
        if (order != null) {
            this.status = OrderStatus.IN_PROGRESS;
        } else {
            this.status = OrderStatus.COMPLETED;
        }
    }

    public String getRole() {
        return "DRIVER";
    }

    // Wymaganie: #10 Nadpisana metoda toString
    public String toString() {
        // ZMIANA: Wyświetlanie marki i numeru rejestracyjnego przypisanego auta
        return super.toString() + " KIEROWCA Status: " + status.getDescription() + ", Auto: " + assignedCar.getBrand() + " (" + assignedCar.getPlateNumber() + ")";
    }
}