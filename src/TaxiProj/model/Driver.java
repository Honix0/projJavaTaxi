package model;

import java.time.LocalDate;
import enums.OrderStatus;

public class Driver extends Person {
    private String driverLicense;
    private double salary;
    private String carModel;
    private int experienceYears;
    private String activeOrder;
    private LocalDate nextPayDay;
    private OrderStatus status;

    public Driver(int id, String firstName, String lastName, LocalDate birthDate, String sex, String email, String phoneNumber, String passwordHash, String driverLicense, double salary, String carModel, int experienceYears) {
        super(id, firstName, lastName, birthDate, sex, email, phoneNumber, passwordHash);
        this.driverLicense = driverLicense;
        this.salary = salary;
        this.carModel = carModel;
        this.experienceYears = experienceYears;
        this.activeOrder = "Brak";
        this.nextPayDay = LocalDate.now().plusMonths(1).withDayOfMonth(10);
        this.status = OrderStatus.COMPLETED;
    }

    public String getDriverLicense() { return driverLicense; }
    public double getSalary() { return salary; }
    public String getCarModel() { return carModel; }
    public int getExperienceYears() { return experienceYears; }
    public String getActiveOrder() { return activeOrder; }
    public LocalDate getNextPayDay() { return nextPayDay; }
    public OrderStatus getStatus() { return status; }

    public void setSalary(double salary) { this.salary = salary; }
    public void setDriverLicense(String driverLicense) { this.driverLicense = driverLicense; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public void setActiveOrder(String activeOrder) {
        this.activeOrder = activeOrder;
        if (!activeOrder.equals("Brak")) {
            this.status = OrderStatus.IN_PROGRESS;
        } else {
            this.status = OrderStatus.COMPLETED;
        }
    }


    public String getRole() {
        return "DRIVER";
    }


    public String toString() {
        return super.toString() + " KIEROWCA Status: " + status.getDescription() + ", Auto: " + carModel;
    }
}