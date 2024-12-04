package com.fetch.points.transactions;
import java.time.LocalDateTime;

/**
 * Contains information for points transactions as well as constructors, setters, and getters.
 */
public class Transaction {
    
    private String payer;
    private LocalDateTime timestamp;
    private Integer points;

    public Transaction() {}

    public Transaction(String payer, LocalDateTime timestamp, Integer points) {
        this.payer = payer;
        this.timestamp = timestamp;
        this.points = points;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Transaction {payer=" + payer + ", points=" + points + ", timestamp=" + timestamp + "}";
    }

}
