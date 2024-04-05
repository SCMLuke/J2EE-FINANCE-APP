package model;

import java.sql.Date;

public class Transactions {
    private int transactionNumber;
    private String transactionType;
    private double transactionAmount;
    private java.sql.Date transactionDate;

    public Transactions(int transactionNumber, String transactionType, double transactionAmount, java.util.Date transactionDate) {
        this.transactionNumber = transactionNumber;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionDate = new java.sql.Date(transactionDate.getTime()); // Convert java.util.Date to java.sql.Date
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }
    public void setTransactionNumber(int transactionNumber) {
        this.transactionNumber = transactionNumber;
    }
    public String getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    public double getTransactionAmount() {
        return transactionAmount;
    }
    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    public java.sql.Date getTransactionDate() {
        return (java.sql.Date) transactionDate;
    }
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
