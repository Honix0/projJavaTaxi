package service;

import model.Car;
import java.util.Comparator;

/**
 * Zewnętrzny komparator do sortowania samochodów.
 * Wymaganie: #14 Implementacja Comparator (sortowanie po roczniku)
 */
public class CarComparator implements Comparator<Car> {

    // Wymaganie: #14 Implementacja metody compare
    public int compare(Car car1, Car car2) { // Wymaganie: #12 Metoda z funkcjonalnością
        // Sortowanie od najstarszego (niższy rok) do najnowszego (wyższy rok)
        return Integer.compare(car1.getYear(), car2.getYear());
    }
}