package dao;

import jdbc.JDBC;
import model.Savings;
import model.Transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SavingsDaoImpl implements SavingsDao {
    private static final String INSERT_SAVINGS_SQL = "INSERT INTO savings" +
            "(savings_id, savings) VALUES " + "(?,?);";
    private static final String SELECT_SAVINGS_BY_ID = "select savings_id, savings from savings where savings_id = ?";
    private static final String SELECT_ALL_SAVINGS = "select * from savings";
    private static final String DELETE_SAVING_BY_ID = "delete from savings where savings_id = ?;";
    private static final String UPDATE_SAVINGS = "UPDATE savings SET savings = ? WHERE savings_id = ?";

    public SavingsDaoImpl(){}

    @Override
    public void insertSavings(Savings savings) throws SQLException {
        System.out.println(INSERT_SAVINGS_SQL);
        try (Connection connection = JDBC.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SAVINGS_SQL)) {
            preparedStatement.setInt(1, savings.getSavingsId());
            preparedStatement.setDouble(2, savings.getSavings());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            JDBC.printSQLException(exception);
        }
    }

    @Override
    public Savings selectSavings(int savingsId) {
        Savings savings = null;
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SAVINGS_BY_ID);) {

            preparedStatement.setInt(1, savingsId);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int number = rs.getInt("savings_id");
                Double savingsAmount = rs.getDouble("savings");
                savings = new Savings(number,savingsAmount);
            }

        } catch (SQLException exception) {
            JDBC.printSQLException(exception);
        }
        return savings;
    }

    @Override
    public List<Savings> selectAllSavings() {
        List<Savings> savings = new ArrayList<>();
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SAVINGS);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int number = rs.getInt("savings_id");
                Double amount = rs.getDouble("savings");
                savings.add(new Savings(number,amount));
            }
        } catch (SQLException exception) {
            JDBC.printSQLException(exception);
        }
        return savings;
    }

    @Override
    public boolean deleteSavings(int number) throws SQLException {
        boolean delete;
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SAVING_BY_ID);) {
            preparedStatement.setInt(1, number);
            delete = preparedStatement.executeUpdate() > 0;
        }
        return delete;
    }

    @Override
    public boolean updateSavings(int savingsId, double updatedSavings) throws SQLException {
        boolean update = false;
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SAVINGS)) {
            preparedStatement.setDouble(1, updatedSavings);
            preparedStatement.setInt(2, savingsId);

            System.out.println("Executing SQL update query: " + preparedStatement);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                update = true;
                System.out.println("Savings updated successfully.");
            } else {
                System.out.println("Failed to update savings. No rows affected.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating savings: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return update;
    }
}
