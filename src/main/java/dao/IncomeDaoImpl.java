package dao;

import jdbc.JDBC;
import model.Income;
import model.Savings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncomeDaoImpl implements IncomeDao {
    private static final String INSERT_INCOME_SQL = "INSERT INTO income" +
            "(income_id, savings) VALUES " + "(?,?);";
    private static final String SELECT_INCOME_BY_ID = "select income_id, income from income where income_id = ?";
    private static final String SELECT_ALL_INCOME = "select * from income";
    private static final String DELETE_INCOME_BY_ID = "delete from income where income_id = ?;";
    private static final String UPDATE_INCOME = "UPDATE income SET income = ? WHERE income_id = ?";

    public IncomeDaoImpl(){}

    @Override
    public void insertIncome(Income income) throws SQLException {
        System.out.println(INSERT_INCOME_SQL);
        try (Connection connection = JDBC.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INCOME_SQL)) {
            preparedStatement.setInt(1, income.getIncomeId());
            preparedStatement.setDouble(2, income.getIncome());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            JDBC.printSQLException(exception);
        }
    }

    @Override
    public Income selectIncome(int incomeId) {
        Income income = null;
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INCOME_BY_ID);) {

            preparedStatement.setInt(1, incomeId);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int number = rs.getInt("income_id");
                Double incomeAmount = rs.getDouble("income");
                income = new Income(number,incomeAmount);
            }

        } catch (SQLException exception) {
            JDBC.printSQLException(exception);
        }
        return income;
    }

    @Override
    public List<Income> selectAllIncome() {
        List<Income> income = new ArrayList<>();
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_INCOME);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int number = rs.getInt("income_id");
                Double amount = rs.getDouble("income");
                income.add(new Income(number,amount));
            }
        } catch (SQLException exception) {
            JDBC.printSQLException(exception);
        }
        return income;
    }

    @Override
    public boolean deleteIncome(int number) throws SQLException {
        boolean delete;
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_INCOME_BY_ID);) {
            preparedStatement.setInt(1, number);
            delete = preparedStatement.executeUpdate() > 0;
        }
        return delete;
    }

    @Override
    public boolean updateIncome(Income income) throws SQLException {
        boolean update = false;
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_INCOME)) {
            preparedStatement.setDouble(1, income.getIncome());
            preparedStatement.setInt(2, income.getIncomeId());

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

    @Override
    public double selectIncomeByMonth(int month) throws SQLException {
        double incomeForMonth = 0;
        String selectIncomeByMonthQuery = "SELECT SUM(transaction_amount) FROM transactions WHERE MONTH(transaction_date) = ? AND transaction_type = 'pay'";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectIncomeByMonthQuery)) {
            preparedStatement.setInt(1, month);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                incomeForMonth = rs.getDouble(1);
            }
        }

        return incomeForMonth;
    }
    @Override
    public double selectExpensesByMonth(int month) throws SQLException {
        double expensesForMonth = 0;
        String selectExpensesByMonthQuery = "SELECT SUM(transaction_amount) FROM transactions WHERE MONTH(transaction_date) = ? AND transaction_type != 'pay'";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectExpensesByMonthQuery)) {
            preparedStatement.setInt(1, month);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                expensesForMonth = rs.getDouble(1);
            }
        }

        return expensesForMonth;
    }
}
