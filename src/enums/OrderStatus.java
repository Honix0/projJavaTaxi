package enums;

public enum OrderStatus {
    PENDING("Oczekujące", "Zamówienie oczekuje na akceptację kierowcy"),
    ACCEPTED("Zaakceptowane", "Kierowca zaakceptował zamówienie"),
    IN_PROGRESS("W trakcie", "Przejazd jest w trakcie realizacji"),
    COMPLETED("Zakończone", "Przejazd został zakończony"),
    CANCELLED("Anulowane", "Zamówienie zostało anulowane");

    private final String description;
    private final String detailedDescription;

    OrderStatus(String description, String detailedDescription) {
        this.description = description;
        this.detailedDescription = detailedDescription;
    }
}
