package model;

public class PremiumCar extends Car {
    private double luxuryTax; // Доплата за комфорт

    // Конструктор
    public PremiumCar(int id, String brand, String plateNumber, String color, int year, double pricePerKm, double luxuryTax) {
        super(id, brand, plateNumber, color, year, pricePerKm);
        this.luxuryTax = luxuryTax;
    }

    // ЛОГИКА ТАКСИ ПРЕМИУМ
    @Override
    public double calculatePrice(double km) {
        double startFee = 15.0; // Посадка в премиум дороже (15 злотых)
        // Формула: Посадка + ((Цена за км + наценка) * расстояние)
        return startFee + ((this.getPricePerKm() + this.luxuryTax) * km);
    }

    @Override
    public String toString() {
        return super.toString() + " -> [PREMIUM TAXI] (Доплата: " + this.luxuryTax + ")";
    }
}