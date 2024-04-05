package dao;

import jdbc.JDBC;
import model.Budget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BudgetDaoImpl implements BudgetDao {
    private static final String INSERT_BUDGET_SQL = "INSERT INTO budget" +
            "(budget_id, budget_name, budget_max, budget_amount) VALUES " + "(?,?,?,?);";
    private static final String SELECT_BUDGET_BY_ID = "select budget_id, budget_name, budget_max, budget_amount from budget where budget_id = ?";
    private static final String SELECT_ALL_BUDGETS = "select * from budget";
    private static final String DELETE_BUDGET_BY_ID = "delete from budget where budget_id = ?;";
    private static final String UPDATE_BUDGET = "UPDATE budget SET budget_name = ?, budget_max = ?, budget_amount = ? WHERE budget_id = ?";

    public BudgetDaoImpl(){}

    @Override
    public boolean insertBudget(Budget budget) throws SQLException {
        System.out.println(INSERT_BUDGET_SQL);
        try (Connection connection = JDBC.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BUDGET_SQL)) {
            preparedStatement.setInt(1, budget.getBudgetId());
            preparedStatement.setString(2, budget.getBudgetName());
            preparedStatement.setDouble(3, budget.getBudgetMax());
            preparedStatement.setDouble(4, budget.getBudgetAmount());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            JDBC.printSQLException(exception);
        }
        return false;
    }

    @Override
    public Budget selectBudget(int budgetNumber) {
        Budget budget = null;
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BUDGET_BY_ID);) {

            preparedStatement.setInt(1, budgetNumber);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int number = rs.getInt("budget_id");
                String type = rs.getString("budget_name");
                Double max = rs.getDouble("budget_max");
                Double amount = rs.getDouble("budget_amount");
                budget = new Budget(number,type,max,amount);
            }

        } catch (SQLException exception) {
            JDBC.printSQLException(exception);
        }
        return budget;
    }

    @Override
    public List<Budget> selectAllBudgets() {
        List<Budget> budgets = new ArrayList<>();
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BUDGETS);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int number = rs.getInt("budget_id");
                String type = rs.getString("budget_name");
                Double max = rs.getDouble("budget_max");
                Double amount = rs.getDouble("budget_amount");
                budgets.add(new Budget(number,type,max,amount));
            }
        } catch (SQLException exception) {
            JDBC.printSQLException(exception);
        }
        return budgets;
    }

    @Override
    public boolean deleteBudget(int number) throws SQLException {
        boolean delete;
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BUDGET_BY_ID);) {
            preparedStatement.setInt(1, number);
            delete = preparedStatement.executeUpdate() > 0;
        }
        return delete;
    }

    @Override
    public boolean updateBudget(Budget budget) throws SQLException {
        boolean update = false;
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BUDGET)) {
            preparedStatement.setString(1, budget.getBudgetName());
            preparedStatement.setDouble(2, budget.getBudgetMax());
            preparedStatement.setDouble(3, budget.getBudgetAmount());
            preparedStatement.setInt(4, budget.getBudgetId());

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
