package model;

public class Budget {
    private int budgetId;
    private String budgetName;
    private Double budgetMax;
    private Double budgetAmount;

    public Budget(int budgetId, String budgetName, Double budgetMax, Double budgetamount) {
        this.budgetId = budgetId;
        this.budgetName = budgetName;
        this.budgetMax = budgetMax;
        this.budgetAmount = budgetamount;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public Double getBudgetMax() {
        return budgetMax;
    }

    public void setBudgetMax(Double budgetMax) {
        this.budgetMax = budgetMax;
    }

    public Double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(Double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }
}
