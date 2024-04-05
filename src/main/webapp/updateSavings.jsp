<%@ page import="java.util.Date" %>
<%@ page import="dao.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="model.Balance" %>
<%@ page import="model.Income" %>
<%@ page import="model.Transactions" %>

<%
    double amountToAdd = Double.parseDouble(request.getParameter("newSavings"));

    // Get current balance and income
    BalanceDaoImpl balanceDao = new BalanceDaoImpl();
    double currentBalance = balanceDao.selectBalance(1).getBalance();

    if (amountToAdd <= currentBalance) {        // Update balance
        double newBalance = currentBalance - amountToAdd;
        Balance newBalanceObj = new Balance(1, newBalance);
        balanceDao.updateBalance(newBalanceObj);

        // Update savings
        SavingsDaoImpl savingsDao = new SavingsDaoImpl();
        double currentSavings = savingsDao.selectSavings(1).getSavings();
        double newSavings = currentSavings + amountToAdd;
        savingsDao.updateSavings(1, newSavings);

        // Add transaction record
        TransactionsDaoImpl transactionsDao = new TransactionsDaoImpl();
        int transactionNumber = transactionsDao.getNextTransactionNumber();
        String transactionType = "savings";
        Date transactionDate = new Date();
        Transactions transaction = new Transactions(transactionNumber, transactionType, amountToAdd, transactionDate);
        transactionsDao.insertTransaction(transaction);

        // Redirect back to the main page after updating
        response.sendRedirect("finance.jsp");
    } else {
        System.out.println("Error: Amount to add exceeds current balance.");
    }
%>
