<%@ page import="dao.IncomeDaoImpl" %>
<%@ page import="model.Income" %>
<%@ page import="java.sql.SQLException" %>

<%
    try {
        double newIncome = Double.parseDouble(request.getParameter("newIncome"));

        IncomeDaoImpl incomeDao = new IncomeDaoImpl();

        Income income = incomeDao.selectIncome(1);

        income.setIncome(newIncome);

        incomeDao.updateIncome(income);

        response.sendRedirect("finance.jsp");
    } catch (SQLException e) {
        System.out.println("Error updating income: " + e.getMessage());
        e.printStackTrace();
    } catch (NumberFormatException e) {
        System.out.println("Invalid income value. Please enter a valid number.");
        e.printStackTrace();
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
%>