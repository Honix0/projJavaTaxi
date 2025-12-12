package model;
import enums.CarType;
public class VanCar extends Car {
    private double vanFee;
    public VanCar(int id, String brand, String plateNumber, String color, int year, double pricePerKm, double vanFee) {
        super(id, brand, plateNumber, color, year, pricePerKm, CarType.VAN);
        this.vanFee = vanFee;
    }
    public double calculatePrice(double km) { double startFee = 20.0; return startFee + ((this.getPricePerKm() + this.vanFee) * km); }
    public String toString() { return super.toString() + " [VAN FEE: " + vanFee + "]"; }
}