package controller;

import dao.BalanceDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Balance;

import java.io.IOException;
import java.sql.SQLException;

public class BalanceController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BalanceDaoImpl balanceDao = new BalanceDaoImpl();

        int balanceId = 1;

        Balance balance = balanceDao.selectBalance(balanceId);

        request.setAttribute("balance", balance);

        request.getRequestDispatcher("/balance.jsp").forward(request, response);
    }
}
