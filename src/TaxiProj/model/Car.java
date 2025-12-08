package model;

import java.util.Objects;
import enums.CarType;

public abstract class Car implements Comparable<Car> {
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
    public void setPricePerKm(double pricePerKm) { this.pricePerKm = pricePerKm; }

    public abstract double calculatePrice(double km);


    public int compareTo(Car otherCar) {
        return Double.compare(this.pricePerKm, otherCar.pricePerKm);
    }


    public String toString() {
        return brand + " " + plateNumber + " (" + color + ") | " + type.getDescription() + " | " + pricePerKm + " PLN/km";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(plateNumber, car.plateNumber);
    }

    public int hashCode() {
        return Objects.hash(plateNumber);
    }
}