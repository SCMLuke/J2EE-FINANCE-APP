package dao;

import model.Budget;

import java.sql.SQLException;
import java.util.List;

public interface BudgetDao {
    boolean insertBudget(Budget budget) throws SQLException;
    Budget selectBudget(int budgetNumber);
    List<Budget> selectAllBudgets();
    boolean deleteBudget(int budgetNumber) throws SQLException;
    boolean updateBudget(Budget budget) throws SQLException;
}
