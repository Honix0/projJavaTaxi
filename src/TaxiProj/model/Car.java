package model;

import java.util.Objects;

public abstract class Car implements Comparable<Car> {
    private int id;
    private String brand;
    private String plateNumber;
    private String color;
    private int year;
    private double pricePerDay;
    private boolean isRented;

    public Car(int id, String brand, String plateNumber, String color, int year, double pricePerDay) {
        this.id = id;
        this.brand = brand;
        this.plateNumber = plateNumber;
        this.color = color;
        this.year = year;
        this.pricePerDay = pricePerDay;
        this.isRented = false;
    }

    public int getId() {
        return this.id;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getPlateNumber() {
        return this.plateNumber;
    }

    public String getColor() {
        return this.color;
    }

    public int getYear() {
        return this.year;
    }

    public double getPricePerDay() {
        return this.pricePerDay;
    }

    public boolean isRented() {
        return this.isRented;
    }

    public void setRented(boolean rented) {
        this.isRented = rented;
    }

    public abstract double calculateFinalCost(int var1);

    public int compareTo(Car otherCar) {
        return Double.compare(this.pricePerDay, otherCar.pricePerDay);
    }

    public String toString() {
        return this.brand + " " + this.plateNumber + " (" + this.year + ") - " + this.pricePerDay + " PLN/день";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Car car = (Car)o;
            return Objects.equals(this.plateNumber, car.plateNumber);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.plateNumber});
    }
}
