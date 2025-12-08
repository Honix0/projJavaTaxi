package model;

import enums.CarType;

public class StandardCar extends Car {

    public StandardCar(int id, String brand, String plateNumber, String color, int year, double pricePerKm) {
        super(id, brand, plateNumber, color, year, pricePerKm, CarType.STANDARD);
    }

    public double calculatePrice(double km) {
        double startFee = 5.0;
        return startFee + (this.getPricePerKm() * km);
    }

    public String toString() {
        return super.toString();
    }
}