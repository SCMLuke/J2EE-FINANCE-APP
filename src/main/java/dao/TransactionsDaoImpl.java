package dao;

import jdbc.JDBC;
import model.Transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionsDaoImpl implements TransactionsDao {
    private static final String INSERT_TRANSACTION_SQL = "INSERT INTO transactions" +
            "(transaction_number, transaction_type, transaction_amount, transaction_date) VALUES " + "(?,?,?,?);";
    private static final String SELECT_TRANSACTION_BY_ID = "select transaction_number, transaction_type, transaction_amount, transaction_date from transactions where transaction_number = ?";
    private static final String SELECT_ALL_TRANSACTIONS = "select * from transactions";
    private static final String DELETE_TRANSACTION_BY_ID = "delete from transactions where transaction_number = ?;";
    private static final String UPDATE_TRANSACTION = "UPDATE transactions SET transaction_type = ?, transaction_amount = ?, transaction_date = ? WHERE transaction_number = ?";

    public TransactionsDaoImpl(){}

    @Override
    public void insertTransaction(Transactions transactions) throws SQLException {
        System.out.println(INSERT_TRANSACTION_SQL);
        try (Connection connection = JDBC.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRANSACTION_SQL)) {
            preparedStatement.setInt(1, transactions.getTransactionNumber());
            preparedStatement.setString(2, transactions.getTransactionType());
            preparedStatement.setDouble(3, transactions.getTransactionAmount());

            // Convert java.util.Date to java.sql.Date
            java.util.Date utilDate = transactions.getTransactionDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            preparedStatement.setDate(4, sqlDate);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            JDBC.printSQLException(exception);
        }
    }

    @Override
    public Transactions selectTransaction(int transactionNumber) {
        Transactions transactions = null;
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TRANSACTION_BY_ID);) {

            preparedStatement.setInt(1, transactionNumber);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int number = rs.getInt("transaction_number");
                String type = rs.getString("transaction_type");
                Double amount = rs.getDouble("transaction_amount");
                java.sql.Date date = rs.getDate("transaction_date");
                transactions = new Transactions(number,type,amount,date);
            }

        } catch (SQLException exception) {
            JDBC.printSQLException(exception);
        }
        return transactions;
    }

    @Override
    public List<Transactions> selectAllTransactions() {
        List<Transactions> transactions = new ArrayList<>();
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TRANSACTIONS);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int number = rs.getInt("transaction_number");
                String type = rs.getString("transaction_type");
                Double amount = rs.getDouble("transaction_amount");
                java.sql.Date date = rs.getDate("transaction_date");
                transactions.add(new Transactions(number,type,amount,date));
            }
        } catch (SQLException exception) {
            JDBC.printSQLException(exception);
        }
        return transactions;
    }

    @Override
    public boolean deleteTransaction(int number) throws SQLException {
        boolean delete;
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TRANSACTION_BY_ID);) {
            preparedStatement.setInt(1, number);
            delete = preparedStatement.executeUpdate() > 0;
        }
        return delete;
    }

    @Override
    public boolean updateTransaction(Transactions transactions) throws SQLException {
        boolean update = false;
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TRANSACTION)) {
            preparedStatement.setInt(1, transactions.getTransactionNumber());
            preparedStatement.setString(2, transactions.getTransactionType());
            preparedStatement.setDouble(3, transactions.getTransactionAmount());
            preparedStatement.setDate(4, transactions.getTransactionDate());

            System.out.println("Executing SQL update query: " + preparedStatement);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                update = true;
                System.out.println("Item updated successfully.");
            } else {
                System.out.println("Failed to update item. No rows affected.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating item: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return update;
    }

    public int getNextTransactionNumber() throws SQLException {
        int nextTransactionNumber = 1;

        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(transaction_number) FROM transactions")) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int maxTransactionNumber = rs.getInt(1);
                nextTransactionNumber = maxTransactionNumber + 1;
            }
        } catch (SQLException e) {
            JDBC.printSQLException(e);
        }

        return nextTransactionNumber;
    }

    @Override
    public double selectTotalExpensesBetweenDates(String startDate, String endDate) {
        double totalExpenses = 0;

        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT SUM(transaction_amount) FROM transactions WHERE DATE(transaction_date) BETWEEN ? AND ? AND transaction_type != 'pay'")) {
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                totalExpenses = rs.getDouble(1);
            }
        } catch (SQLException e) {
            JDBC.printSQLException(e);
        }

        return totalExpenses;
    }


}
