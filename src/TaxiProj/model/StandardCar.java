package model;

public class StandardCar extends Car {
    public StandardCar(int id, String brand, String plateNumber, String color, int year, double pricePerDay) {
        super(id, brand, plateNumber, color, year, pricePerDay);
    }

    public double calculateFinalCost(int days) {
        return this.getPricePerDay() * (double)days;
    }

    public String toString() {
        return super.toString() + " -> [STANDARD]";
    }
}