<%@ page import="dao.TransactionsDaoImpl" %>
<%@ page import="dao.BalanceDaoImpl" %>
<%@ page import="model.Transactions" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.Balance" %>

<%
    String transactionType = request.getParameter("transactionType");
    double transactionAmount = Double.parseDouble(request.getParameter("transactionAmount"));

    BalanceDaoImpl balanceDao = new BalanceDaoImpl();
    Balance balance = balanceDao.selectBalance(1);
    double balanceAmount = balance.getBalance();

    if (transactionAmount > balanceAmount) {
        response.sendRedirect("finance.jsp?error=Transaction amount exceeds available balance");
        return;
    }

    Date transactionDate = new Date();

    TransactionsDaoImpl transactionsDao = new TransactionsDaoImpl();
    int transactionNumber = transactionsDao.getNextTransactionNumber();

    double newBalanceAmount = balanceAmount - transactionAmount;
    balance.setBalance(newBalanceAmount);
    balanceDao.updateBalance(balance);

    Transactions transaction = new Transactions(transactionNumber, transactionType, transactionAmount, transactionDate);
    transactionsDao.insertTransaction(transaction);

    response.sendRedirect("finance.jsp");
%>
