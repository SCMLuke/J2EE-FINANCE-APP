package dao;

import model.Balance;
import model.Income;

import java.sql.SQLException;
import java.util.List;

public interface IncomeDao {
    void insertIncome(Income income) throws SQLException;
    Income selectIncome(int incomeId);
    List<Income> selectAllIncome();
    boolean deleteIncome(int incomeId) throws SQLException;
    boolean updateIncome(Income income) throws SQLException;
    double selectIncomeByMonth(int month) throws SQLException;
    double selectExpensesByMonth(int month) throws SQLException;
}
