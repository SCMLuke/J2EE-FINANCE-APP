<%@ page import="dao.BudgetDao" %>
<%@ page import="dao.BudgetDaoImpl" %>
<%@ page import="model.Budget" %>
<%@ page import="dao.BalanceDaoImpl" %>
<%@ page import="model.Balance" %>
<%@ page import="dao.TransactionsDaoImpl" %>
<%@ page import="model.Transactions" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Budget</title>
</head>
<body>

<h1>Edit Budget</h1>

<%
    int budgetId = Integer.parseInt(request.getParameter("budgetId"));
    String budgetName = request.getParameter("budgetName");
    double budgetMax = Double.parseDouble(request.getParameter("budgetMax"));
    double budgetAmount = Double.parseDouble(request.getParameter("budgetAmount"));

    BalanceDaoImpl balanceDao = new BalanceDaoImpl();
    Balance balance = balanceDao.selectBalance(1);
    double updatedBalanceAmount = balance.getBalance() - (budgetAmount - balance.getBalance());
    balance.setBalance(updatedBalanceAmount);
    balanceDao.updateBalance(balance);

    BudgetDao budgetDao = new BudgetDaoImpl();
    Budget budget = budgetDao.selectBudget(budgetId);
    double updatedBudgetAmount = budget.getBudgetAmount() + budgetAmount;
    budget.setBudgetAmount(updatedBudgetAmount);
    budgetDao.updateBudget(budget);

    TransactionsDaoImpl transactionsDao = new TransactionsDaoImpl();
    int transactionNumber = transactionsDao.getNextTransactionNumber();
    Date transactionDate = new Date();

    Transactions transaction = new Transactions(transactionNumber, "budget", budgetAmount, transactionDate);
    transactionsDao.insertTransaction(transaction);

    response.sendRedirect("finance.jsp");
%>
</body>
</html>
