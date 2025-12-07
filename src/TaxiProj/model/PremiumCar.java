package model;

public class PremiumCar extends Car {
    private double luxuryTax;

    public PremiumCar(int id, String brand, String plateNumber, String color, int year, double pricePerDay, double luxuryTax) {
        super(id, brand, plateNumber, color, year, pricePerDay);
        this.luxuryTax = luxuryTax;
    }

    public double calculateFinalCost(int days) {
        return (this.getPricePerDay() + this.luxuryTax) * (double)days;
    }

    public String toString() {
        String var10000 = super.toString();
        return var10000 + " -> [PREMIUM] (Налог: " + this.luxuryTax + ")";
    }
}
