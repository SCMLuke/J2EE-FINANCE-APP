<%@ page import="dao.BudgetDao" %>
<%@ page import="dao.BudgetDaoImpl" %>
<%@ page import="model.Budget" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Budget</title>
</head>
<body>

<h1>Add Budget</h1>

<%
    String budgetName = request.getParameter("budgetName");
    double budgetMax = Double.parseDouble(request.getParameter("budgetMax"));
    double budgetAmount = Double.parseDouble(request.getParameter("budgetAmount"));

    BudgetDao budgetDao = new BudgetDaoImpl();
    Budget budget = new Budget(0, budgetName, budgetMax, budgetAmount);

    budgetDao.insertBudget(budget);
    response.sendRedirect("finance.jsp");
%>

</body>
</html>
