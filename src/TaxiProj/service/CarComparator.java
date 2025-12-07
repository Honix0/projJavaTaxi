package service;

import java.util.Comparator;
import model.Car;

public class CarComparator implements Comparator<Car> {
    public CarComparator() {
    }

    public int compare(Car car1, Car car2) {
        return car1.getBrand().compareTo(car2.getBrand());
    }
}
