package model;
import enums.OrderStatus;
import java.time.LocalDateTime;

/**
 * Klasa do zarządzania szczegółami zamówienia i jego statusem.
 * Wymaganie: #1 (Klasa #12). ZMIANA: Użycie statycznego licznika zamiast UUID
 */
public class Order {
    private static int nextOrderId = 1;

    private String orderId;
    private Client client;
    private Driver driver;
    private OrderStatus status;
    private String destination;
    private double distanceKm;
    private double finalPrice;
    private LocalDateTime orderTime;
    private Car carTariff; // Taryfa wybrana przez klienta

    public Order(Client client, String destination, double distanceKm, double finalPrice, Car carTariff) {
        this.orderId = "ORD-" + nextOrderId++; // Prostsza generacja ID
        this.client = client;
        this.destination = destination;
        this.distanceKm = distanceKm;
        this.finalPrice = finalPrice;
        this.carTariff = carTariff;
        this.status = OrderStatus.PENDING;
        this.orderTime = LocalDateTime.now();
        this.driver = null;
    }

    // Wymaganie: #12 Metoda z funkcjonalnością
    public void setStatus(OrderStatus status) {
        this.status = status;
        if (status == OrderStatus.ACCEPTED && this.driver != null) {
            this.driver.setActiveOrder(this);
        } else if (status == OrderStatus.COMPLETED && this.driver != null) {
            this.driver.setActiveOrder(null);
        }
    }

    // Wymaganie: #12 Metoda z funkcjonalnością
    public void assignDriver(Driver driver) {
        this.driver = driver;
        setStatus(OrderStatus.ACCEPTED);
    }

    // Wymaganie: #10 Nadpisana metoda toString
    public String toString() {
        String driverInfo;
        String carInfo;

        if (driver != null) {
            // DODANO: Wyświetlenie informacji o przypisanym kierowcy i jego aucie
            driverInfo = String.format("%s %s (Auto: %s)",
                    driver.getFirstName(), driver.getLastName(), driver.getAssignedCar().getPlateNumber());
            carInfo = driver.getAssignedCar().getBrand(); // Używamy marki auta kierowcy
        } else {
            driverInfo = "Brak";
            carInfo = carTariff.getBrand(); // Jeśli brak kierowcy, używamy wybranej taryfy
        }

        return String.format("[%s] Status: %s, Kierowca: %s, Do: %s (%.1f km), Taryfa: %s, Cena: %.2f PLN",
                orderId, status.getDescription(), driverInfo, destination, distanceKm, carInfo, finalPrice);
    }

    public String getOrderId() { return orderId; }
    public Client getClient() { return client; }
    public Driver getDriver() { return driver; }
    public OrderStatus getStatus() { return status; }
    public String getDestination() { return destination; }
    public double getDistanceKm() { return distanceKm; }
    public double getFinalPrice() { return finalPrice; }
    public Car getCarTariff() { return carTariff; }
}