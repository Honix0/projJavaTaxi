package exceptions;

/**
 * Zdefiniowany wyjątek użytkownika.
 * Wymaganie: #7 Wyjątek użytkownika (definicja)
 */
public class TaxiException extends RuntimeException {
    public TaxiException(String message) {
        super(message);
    }
}