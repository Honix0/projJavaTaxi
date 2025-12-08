package model;

import enums.CarType;

public class PremiumCar extends Car {
    private double luxuryTax;

    public PremiumCar(int id, String brand, String plateNumber, String color, int year, double pricePerKm, double luxuryTax) {
        super(id, brand, plateNumber, color, year, pricePerKm, CarType.PREMIUM);
        this.luxuryTax = luxuryTax;
    }

    public double calculatePrice(double km) {
        double startFee = 15.0;
        return startFee + ((this.getPricePerKm() + this.luxuryTax) * km);
    }

    public String toString() {
        return super.toString() + " [LUXURY TAX: " + luxuryTax + "]";
    }
}