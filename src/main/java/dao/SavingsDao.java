package dao;

import model.Balance;
import model.Savings;

import java.sql.SQLException;
import java.util.List;

public interface SavingsDao {
    void insertSavings(Savings savings) throws SQLException;
    Savings selectSavings(int savingsId);
    List<Savings> selectAllSavings();
    boolean deleteSavings(int savingsId) throws SQLException;
    boolean updateSavings(int savingsId, double updatedSavingsSavings) throws SQLException;
}
