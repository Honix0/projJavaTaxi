package model;

public class StandardCar extends Car {

    // Конструктор
    public StandardCar(int id, String brand, String plateNumber, String color, int year, double pricePerKm) {
        super(id, brand, plateNumber, color, year, pricePerKm);
    }

    // ЛОГИКА ТАКСИ: Считаем цену за километры
    @Override
    public double calculatePrice(double km) {
        double startFee = 5.0; // Цена за посадку (5 злотых)
        // Формула: Посадка + (Цена за км * расстояние)
        return startFee + (this.getPricePerKm() * km);
    }

    @Override
    public String toString() {
        return super.toString() + " -> [ECONOMY TAXI]";
    }
}