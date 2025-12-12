package enums;

/**
 * Enum definiujący typy samochodów i ich podstawowe parametry.
 * Wymaganie: #19 Użycie enum
 */
public enum CarType {
    STANDARD("Standard", 4, "Ekonomiczny samochód osobowy", 300, true),
    PREMIUM("Premium", 4, "Luksusowy samochód z dodatkowymi udogodnieniami", 400, true),
    VAN("Van/Bus", 8, "Pojazd dla większych grup (6-8 osób)", 600, true),
    MINIBUS("Minibus", 15, "Minibus dla dużych grup (do 15 osób)", 800, false),
    ECO("Eco/Hybrid", 4, "Pojazd ekologiczny (hybrid/elektryczny)", 300, true);

    private final String description;
    private final int maxPassengers;
    private final String detailedDescription;
    private final int minTrunkCapacity;
    private final boolean availableForShortTrips;

    CarType(String description, int maxPassengers, String detailedDescription, int minTrunkCapacity, boolean availableForShortTrips) {
        this.description = description;
        this.maxPassengers = maxPassengers;
        this.detailedDescription = detailedDescription;
        this.minTrunkCapacity = minTrunkCapacity;
        this.availableForShortTrips = availableForShortTrips;
    }

    public String getDescription() {
        return description;
    }

    // Wymaganie: #12 Metoda z funkcjonalnością (wywoływana w Menu.orderTaxi)
    public int getMaxPassengers() {
        return maxPassengers;
    }

    // Wymaganie: #12 Metoda z funkcjonalnością (wywoływana w Menu.showCars)
    public String getDetailedDescription() {
        return detailedDescription;
    }

    // Wymaganie: #12 Metoda z funkcjonalnością (wywoływana w Menu.orderTaxi)
    public int getMinTrunkCapacity() {
        return minTrunkCapacity;
    }

    // Wymaganie: #12 Metoda z funkcjonalnością (wywoływana w Menu.orderTaxi)
    public boolean isAvailableForShortTrips() {
        return availableForShortTrips;
    }
}