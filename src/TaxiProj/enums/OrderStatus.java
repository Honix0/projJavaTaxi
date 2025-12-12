package enums;

/**
 * Enum statusów zamówienia.
 * Wymaganie: #19 Użycie enum
 */
public enum OrderStatus {
    PENDING("Oczekujące", "Zamówienie oczekuje na akceptację kierowcy"),
    ACCEPTED("Zaakceptowane", "Kierowca zaakceptował zamówienie"),
    IN_PROGRESS("W trakcie", "Przejazd jest w trakcie realizacji"),
    COMPLETED("Zakończone", "Przejazd został zakończony"),
    CANCELLED("Anulowane", "Zamówienie zostało anulowane");

    private final String description;
    private final String detailedDescription;

    private OrderStatus(String description, String detailedDescription) {
        this.description = description;
        this.detailedDescription = detailedDescription;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDetailedDescription() {
        return this.detailedDescription;
    }
}