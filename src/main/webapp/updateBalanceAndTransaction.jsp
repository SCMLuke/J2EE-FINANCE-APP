<%@ page import="dao.BalanceDaoImpl" %>
<%@ page import="dao.TransactionsDaoImpl" %>
<%@ page import="model.Balance" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Date" %>
<%@ page import="dao.IncomeDaoImpl" %>
<%@ page import="model.Transactions" %>
<%@ page import="model.Income" %>

<%
    try {
        BalanceDaoImpl balanceDao = new BalanceDaoImpl();
        Balance balance = balanceDao.selectBalance(1);
        double currentBalance = balance.getBalance();

        IncomeDaoImpl incomeDao = new IncomeDaoImpl();
        Income income = incomeDao.selectIncome(1);
        double currentIncome = income.getIncome();

        double newBalance = currentBalance + currentIncome;
        balance.setBalance(newBalance);
        balanceDao.updateBalance(balance);

        TransactionsDaoImpl transactionsDao = new TransactionsDaoImpl();
        int transactionNumber = transactionsDao.getNextTransactionNumber();
        String transactionType = "pay";
        double transactionAmount = currentIncome;
        Date transactionDate = new Date();

        Transactions transaction = new Transactions(transactionNumber, transactionType, transactionAmount, transactionDate);
        transactionsDao.insertTransaction(transaction);

        response.sendRedirect("finance.jsp");
    } catch (SQLException e) {
        System.out.println("Error updating balance and creating transaction: " + e.getMessage());
        e.printStackTrace();
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
%>
