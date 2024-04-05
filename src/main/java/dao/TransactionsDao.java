package dao;

import model.Balance;
import model.Transactions;

import java.sql.SQLException;
import java.util.List;

public interface TransactionsDao {
    void insertTransaction(Transactions transactions) throws SQLException;
    Transactions selectTransaction(int transactionNumber);
    List<Transactions> selectAllTransactions();
    boolean deleteTransaction(int transactionNumber) throws SQLException;
    boolean updateTransaction(Transactions transactions) throws SQLException;
    double selectTotalExpensesBetweenDates(String startDate, String endDate);
}
