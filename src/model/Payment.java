package model;

import java.time.LocalDateTime;

public class Payment {
    private String paymentId;
    private double amount;
    private boolean isPaid;
    private LocalDateTime paymentDate;
    private String transactionId;

    public Payment(String paymentId, double amount, boolean isPaid, LocalDateTime paymentDate, String transactionId) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.isPaid = isPaid;
        this.paymentDate = paymentDate;
        this.transactionId = transactionId;
    }
}
