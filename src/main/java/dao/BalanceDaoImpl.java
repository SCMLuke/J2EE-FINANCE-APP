package dao;

import jdbc.JDBC;
import model.Balance;
import model.Income;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BalanceDaoImpl implements BalanceDao {
    private static final String INSERT_BALANCE_SQL = "INSERT INTO balance" +
            "(balance_id, balance) VALUES " + "(?,?);";
    private static final String SELECT_BALANCE_BY_ID = "select balance_id, balance from balance where balance_id = ?";
    private static final String SELECT_ALL_BALANCE = "select * from balance";
    private static final String DELETE_BALANCE_BY_ID = "delete from balance where balance_id = ?;";
    private static final String UPDATE_BALANCE = "UPDATE balance SET balance = ? WHERE balance_id = ?";

    public BalanceDaoImpl(){}

    @Override
    public void insertBalance(Balance balance) throws SQLException {
        System.out.println(INSERT_BALANCE_SQL);
        try (Connection connection = JDBC.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BALANCE_SQL)) {
            preparedStatement.setInt(1, balance.getBalanceId());
            preparedStatement.setDouble(2, balance.getBalance());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            JDBC.printSQLException(exception);
        }
    }

    @Override
    public Balance selectBalance(int balanceId) {
        Balance balance = null;
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BALANCE_BY_ID);) {

            preparedStatement.setInt(1, balanceId);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int number = rs.getInt("balance_id");
                Double balanceAmount = rs.getDouble("balance");
                balance = new Balance(number,balanceAmount);
            }

        } catch (SQLException exception) {
            JDBC.printSQLException(exception);
        }
        return balance;
    }

    @Override
    public List<Balance> selectAllBalance() {
        List<Balance> balance = new ArrayList<>();
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BALANCE);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int number = rs.getInt("balance_id");
                Double amount = rs.getDouble("balance");
                balance.add(new Balance(number,amount));
            }
        } catch (SQLException exception) {
            JDBC.printSQLException(exception);
        }
        return balance;
    }

    @Override
    public boolean deleteBalance(int number) throws SQLException {
        boolean delete;
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BALANCE_BY_ID);) {
            preparedStatement.setInt(1, number);
            delete = preparedStatement.executeUpdate() > 0;
        }
        return delete;
    }

    @Override
    public boolean updateBalance(Balance balance) throws SQLException {
        boolean update = false;
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BALANCE)) {
            preparedStatement.setInt(2, balance.getBalanceId());
            preparedStatement.setDouble(1, balance.getBalance());

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
}
