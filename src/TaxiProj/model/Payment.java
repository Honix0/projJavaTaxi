package model;

import java.time.LocalDateTime;

public class Payment {
    private String paymentId;
    private double amount;
    private boolean isPaid;
    private LocalDateTime paymentDate;
    private String transactionId;

    public Payment(String paymentId, double amount, String transactionId) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.transactionId = transactionId;
        this.isPaid = false;
        this.paymentDate = null;
    }

    // --- Геттеры (оставляем без изменений) ---
    public String getPaymentId() {
        return this.paymentId;
    }

    public double getAmount() {
        return this.amount;
    }

    public boolean isPaid() {
        return this.isPaid;
    }

    public LocalDateTime getPaymentDate() {
        return this.paymentDate;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    // --- Логика оплаты (Сообщения на польском) ---
    public void processPayment() {
        this.isPaid = true;
        this.paymentDate = LocalDateTime.now();
        // Было: "Оплата прошла успешно..."
        // Стало: "Płatność zakończona sukcesem..."
        System.out.println("Płatność zakończona sukcesem! ID Transakcji: " + this.transactionId);
    }

    // --- Вывод информации (Сообщения на польском) ---
    @Override
    public String toString() {
        return "Płatność [" +
                "ID='" + paymentId + '\'' +
                ", Kwota=" + amount +
                ", Zapłacono=" + (isPaid ? "TAK" : "NIE") +
                ", Data=" + (paymentDate != null ? paymentDate : "Oczekiwanie") +
                "]";
    }
}