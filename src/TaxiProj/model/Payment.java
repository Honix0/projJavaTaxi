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

    public void processPayment() {
        this.isPaid = true;
        this.paymentDate = LocalDateTime.now();
        System.out.println("Оплата прошла успешно! Транзакция: " + this.transactionId);
    }

    public String toString() {
        String var10000 = this.paymentId;
        return "Квитанция [ID='" + var10000 + "', Сумма=" + this.amount + ", Оплачено=" + (this.isPaid ? "ДА" : "НЕТ") + ", Дата=" + String.valueOf(this.paymentDate != null ? this.paymentDate : "Ожидание") + "]";
    }
}
