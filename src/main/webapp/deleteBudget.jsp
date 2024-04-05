<%@ page import="dao.BudgetDao" %>
<%@ page import="dao.BudgetDaoImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Budget</title>
</head>
<body>

<h1>Delete Budget</h1>

<%
    int budgetId = Integer.parseInt(request.getParameter("budgetId"));

    BudgetDao budgetDao = new BudgetDaoImpl();
    boolean success = budgetDao.deleteBudget(budgetId);

    response.sendRedirect("finance.jsp");
%>

</body>
</html>
