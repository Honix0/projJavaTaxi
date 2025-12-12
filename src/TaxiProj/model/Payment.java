package model;

import java.time.LocalDateTime;

// ZMIANA: Uproszczony model ID bez UUID
public class Payment {
    private static int nextPaymentId = 1;

    private String paymentId;
    private double amount;
    private boolean isPaid;
    private LocalDateTime paymentDate;
    private String transactionId;

    // ZMIANA: Uproszczony konstruktor, generujący unikalne ID
    public Payment(double amount) {
        this.paymentId = "PAY-" + nextPaymentId++;
        this.amount = amount;
        this.transactionId = "TX-" + this.paymentId;
        this.isPaid = false;
        this.paymentDate = null;
    }

    public String getPaymentId() { return this.paymentId; }
    public double getAmount() { return this.amount; }
    public boolean isPaid() { return this.isPaid; }
    public LocalDateTime getPaymentDate() { return this.paymentDate; }
    public String getTransactionId() { return this.transactionId; }


    public void processPayment() { // Wymaganie: #12 Metoda z funkcjonalnością (wywoływana w orderTaxi)
        this.isPaid = true;
        this.paymentDate = LocalDateTime.now();

        System.out.println("Płatność zakończona sukcesem! ID Transakcji: " + this.transactionId);
    }

    // Wymaganie: #10 Nadpisana metoda toString
    public String toString() {
        return "Płatność [" + "ID='" + paymentId + '\'' + ", Kwota=" + amount + ", Zapłacono=" + (isPaid ? "TAK" : "NIE") + ", Data=" + (paymentDate != null ? paymentDate : "Oczekiwanie") + "]";
    }
}