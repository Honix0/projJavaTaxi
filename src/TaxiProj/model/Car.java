package model;

import java.util.Objects;
import enums.CarType;
import service.IPrintable; // Wymaganie: #13

// Klasa abstrakcyjna. Implementuje Comparable i IPrintable.
// Wymaganie: #3 Klasa abstrakcyjna, #2 (Hierarchia #2), #14 (Comparable), #13 (IPrintable)
public abstract class Car implements Comparable<Car>, IPrintable {
    private int id;
    private String brand;
    private String plateNumber;
    private String color;
    private int year;
    private double pricePerKm;
    private boolean isRented;
    private CarType type;

    public Car(int id, String brand, String plateNumber, String color, int year, double pricePerKm, CarType type) {
        this.id = id;
        this.brand = brand;
        this.plateNumber = plateNumber;
        this.color = color;
        this.year = year;
        this.pricePerKm = pricePerKm;
        this.type = type;
        this.isRented = false;
    }

    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getPlateNumber() { return plateNumber; }
    public String getColor() { return color; }
    public int getYear() { return year; }
    public double getPricePerKm() { return pricePerKm; }
    public boolean isRented() { return isRented; }
    public CarType getType() { return type; }

    public void setRented(boolean rented) { this.isRented = rented; }
    public void setPricePerKm(double pricePerKm) { this.pricePerKm = pricePerKm; } // Wymaganie: #12

    // Wymaganie: #3 Metoda abstrakcyjna #2
    public abstract double calculatePrice(double km);

    // Wymaganie: #6 Metoda statyczna #2
    public static void printCarInfo(Car car) { // Wymaganie: #12 Metoda z funkcjonalnością
        System.out.println("--- INFO O AUCIE (Metoda statyczna) ---");
        System.out.println("ID: " + car.getId());
        System.out.println("Marka: " + car.getBrand());
        System.out.println("Cena za km: " + car.getPricePerKm() + " PLN");
        System.out.println("--------------------------------------");
    }

    // Wymaganie: #14 Implementacja Comparable
    public int compareTo(Car otherCar) { // Wymaganie: #12
        return Double.compare(this.pricePerKm, otherCar.pricePerKm);
    }

    // Wymaganie: #10 Nadpisana metoda toString
    public String toString() {
        return id + ": " + brand + " " + plateNumber + " (" + color + ") | " + type.getDescription() + " | " + pricePerKm + " PLN/km";
    }

    // Wymaganie: #11 Nadpisana metoda equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o; // Wymaganie: #5 Downcasting
        return Objects.equals(plateNumber, car.plateNumber);
    }

    // Wymaganie: #11 Nadpisana metoda hashCode
    public int hashCode() {
        return Objects.hash(plateNumber);
    }

    // Wymaganie: #13 Implementacja metody z interfejsu
    public String getShortInfo() { // Wymaganie: #12
        return "ID: " + this.id + ", Typ: " + this.type.getDescription() + ", Cena: " + this.pricePerKm + " PLN/km";
    }
}