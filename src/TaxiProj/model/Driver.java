package model;

import java.time.LocalDate;

public class Driver extends Person {
    private String driverLicense;
    private double salary;

    // НОВЫЕ ПОЛЯ
    private String carModel;       // Какая у него машина
    private int experienceYears;   // Стаж вождения
    private String activeOrder;    // Текущий заказ (если есть)
    private LocalDate nextPayDay;  // Когда зарплата

    public Driver(int id, String firstName, String lastName, LocalDate birthDate, String sex, String email, String phoneNumber, String passwordHash, String driverLicense, double salary, String carModel, int experienceYears) {
        super(id, firstName, lastName, birthDate, sex, email, phoneNumber, passwordHash);
        this.driverLicense = driverLicense;
        this.salary = salary;
        this.carModel = carModel;
        this.experienceYears = experienceYears;
        this.activeOrder = "Brak (Wolny)";
        // Зарплата всегда 10-го числа следующего месяца
        this.nextPayDay = LocalDate.now().plusMonths(1).withDayOfMonth(10);
    }

    // --- ГЕТТЕРЫ И СЕТТЕРЫ ---
    public String getDriverLicense() { return driverLicense; }
    public double getSalary() { return salary; }

    public String getActiveOrder() { return activeOrder; }
    public void setActiveOrder(String activeOrder) { this.activeOrder = activeOrder; }

    public LocalDate getNextPayDay() { return nextPayDay; }

    @Override
    public String getRole() {
        return "DRIVER";
    }

    @Override
    public String toString() {
        return super.toString() + " [KIEROWCA] \n" +
                "   -> Auto: " + carModel + "\n" +
                "   -> Staż: " + experienceYears + " lat\n" +
                "   -> Status: " + (activeOrder.equals("Brak (Wolny)") ? "Wolny" : "W TRASIE");
    }
}