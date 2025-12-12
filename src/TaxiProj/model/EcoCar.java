package model;

import enums.CarType;

/**
 * Klasa dla typu pojazdu ECO/Hybrid.
 * Wymaganie: Nowa klasa dla CarType.ECO. #1 (Nowa klasa)
 */
public class EcoCar extends Car {

    private double ecoDiscount; // Dodatkowy rabat za bycie Eco

    public EcoCar(int id, String brand, String plateNumber, String color, int year, double pricePerKm, double ecoDiscount) {
        super(id, brand, plateNumber, color, year, pricePerKm, CarType.ECO);
        this.ecoDiscount = ecoDiscount;
    }

    // Nadpisanie metody abstrakcyjnej z Car. Wymaganie: #3 (Metoda abstrakcyjna)
    public double calculatePrice(double km) { // Wymaganie: #12 (Wywołana w orderTaxi)
        // Niska opłata startowa (3.0 PLN) + (cena za km - rabat) * km
        double startFee = 3.0;
        return startFee + (this.getPricePerKm() - this.ecoDiscount) * km;
    }

    // Wymaganie: #10 Nadpisana metoda toString
    public String toString() {
        return super.toString() + " [RABAT ECO: " + ecoDiscount + "]";
    }
}