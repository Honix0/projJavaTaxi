package service;

import model.Car;
import java.util.Comparator;

public class CarComparator implements Comparator<Car> {


    public int compare(Car car1, Car car2) {
        return Double.compare(car1.getPricePerKm(), car2.getPricePerKm());
    }
}