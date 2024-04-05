package model;

public class Balance {
    private int balanceId;
    private double balance;

    public Balance(int balanceId, double balance) {
        this.balanceId = balanceId;
        this.balance = balance;
    }

    public int getBalanceId() {
        return balanceId;
    }
    public void setBalanceId(int balanceId) {
        this.balanceId = balanceId;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
