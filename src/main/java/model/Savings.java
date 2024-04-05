package model;

public class Savings {
    private int savingsId;
    private double savings;

    public Savings(int savingsId, double savings) {
        this.savingsId = savingsId;
        this.savings = savings;
    }

    public int getSavingsId() {
        return savingsId;
    }
    public void setSavingsId(int savingsId) {
        this.savingsId = savingsId;
    }
    public double getSavings() {
        return savings;
    }
    public void setSavings(double savings) {
        this.savings = savings;
    }
}
