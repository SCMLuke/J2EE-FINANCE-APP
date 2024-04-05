package dao;

import model.Balance;

import java.sql.SQLException;
import java.util.List;

public interface BalanceDao {
    void insertBalance(Balance balance) throws SQLException;
    Balance selectBalance(int balanceId);
    List<Balance>selectAllBalance();
    boolean deleteBalance(int balanceId) throws SQLException;
    boolean updateBalance(Balance balance) throws SQLException;
}
