package model;
import enums.CarType;
public class MinibusCar extends Car {
    private double largeGroupFee;
    public MinibusCar(int id, String brand, String plateNumber, String color, int year, double pricePerKm, double largeGroupFee) {
        super(id, brand, plateNumber, color, year, pricePerKm, CarType.MINIBUS);
        this.largeGroupFee = largeGroupFee;
    }
    public double calculatePrice(double km) { double startFee = 30.0; return startFee + (this.getPricePerKm() + this.largeGroupFee) * km; }
    public String toString() { return super.toString() + " [OPŁATA GRUPOWA: " + largeGroupFee + "]"; }
}