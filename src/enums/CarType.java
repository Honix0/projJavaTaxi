package enums;

public enum CarType {
    STANDARD("Standard", 4, "Ekonomiczny samochód osobowy", 300, true),
    PREMIUM("Premium",4, "Luksusowy samochód z dodatkowymi udogodnieniami", 400, true),
    VAN("Van/Bus", 8, "Pojazd dla większych grup (6-8 osób)", 600, true),
    MINIBUS("Minibus", 15, "Minibus dla dużych grup (do 15 osób)", 800, false),
    ECO("Eco/Hybrid", 4, "Pojazd ekologiczny (hybrid/elektryczny)", 300, true);


    private final String description;
    private final int maxPassengers;
    private final String detailedDescription;
    private final int minTrunkCapacity; // Minimalna pojemność bagażnika w litrach
    private final boolean availableForShortTrips; // Czy dostępny dla krótkich tras

    CarType(String description,
            int maxPassengers, String detailedDescription, int minTrunkCapacity,
                boolean availableForShortTrips) {
        this.description = description;
        this.maxPassengers = maxPassengers;
        this.detailedDescription = detailedDescription;
        this.minTrunkCapacity = minTrunkCapacity;
        this.availableForShortTrips = availableForShortTrips;
    }
}
