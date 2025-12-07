package model;

import java.util.Objects;

public abstract class Car implements Comparable<Car> {
    private int id;
    private String brand;
    private String plateNumber;
    private String color;
    private int year;
    private double pricePerKm; // ИЗМЕНЕНИЕ: Цена за километр
    private boolean isRented;  // Статус: Занят (на заказе) или Свободен

    public Car(int id, String brand, String plateNumber, String color, int year, double pricePerKm) {
        this.id = id;
        this.brand = brand;
        this.plateNumber = plateNumber;
        this.color = color;
        this.year = year;
        this.pricePerKm = pricePerKm;
        this.isRented = false;
    }

    // --- ГЕТТЕРЫ ---
    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getPlateNumberl() { return plateNumber; }
    public String getColor() { return color; }
    public int getYear() { return year; }

    // Геттер для цены за км
    public double getPricePerKm() { return pricePerKm; }
    public void setPricePerKm(double pricePerKm) {
        this.pricePerKm = pricePerKm;
    }
    public boolean isRented() { return isRented; }
    public void setRented(boolean rented) { this.isRented = rented; }

    // --- ИЗМЕНЕНИЕ: АБСТРАКТНЫЙ МЕТОД ДЛЯ ТАКСИ ---
    // Теперь принимаем километры (double), а не дни
    public abstract double calculatePrice(double km);

    // --- Сортировка по цене за км ---
    @Override
    public int compareTo(Car otherCar) {
        return Double.compare(this.pricePerKm, otherCar.pricePerKm);
    }

    @Override
    public String toString() {
        // Выводим "PLN/km"
        return brand + " " + plateNumber + " (" + color + ") - " + pricePerKm + " PLN/km";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(plateNumber, car.plateNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plateNumber);
    }
}