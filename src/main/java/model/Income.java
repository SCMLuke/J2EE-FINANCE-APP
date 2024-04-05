package model;

public class Income {
    private int incomeId;
    private double income;

    public Income(int incomeId, double income) {
        this.incomeId = incomeId;
        this.income = income;
    }

    public int getIncomeId() {
        return incomeId;
    }
    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public double getIncome() {
        return income;
    }
    public void setIncome(double income) {
        this.income = income;
    }
}
