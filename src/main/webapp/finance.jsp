<%@ page import="java.util.List" %>
<%@ page import="controller.BalanceController" %> <!-- Add this import -->
<%@ page import="java.util.Iterator" %>
<%@ page import="model.Balance" %>
<%@ page import="dao.*" %>
<%@ page import="model.Transactions" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="model.Budget" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Finance</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">

    <style>
        .info {
            margin: 64px;
            display: flex;
            flex-direction: row;
            padding: 8px;
            justify-content: space-between;
        }
        .moneys {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            box-shadow: 0 0 10px rgba(0,0,0,0.75);
            font-size: 32px;
            text-align: right;
            padding: 32px;
            max-height: 500px;
        }
        .newTransaction {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            margin-left: auto;
            box-shadow: 0 0 10px rgba(0,0,0,0.75);
            font-size: 32px;
            text-align: right;
            padding: 32px;
            max-height: 500px;
            align-items: center;
            text-align: center;
        }
        .form-group {
            text-align: center;
        }
        .newTransaction form {
            font-size: 16px;
        }
        .moneys form {
            font-size: 16px;
        }
        .table {
            box-shadow: 0 0 10px rgba(0,0,0,0.75);
            padding: 32px;
            font-size: 32px;
            width: auto;
            margin-left: auto;
            overflow-y: auto;
            max-height: 500px;
        }
        .totals {
            justify-content: center;
            display: flex;
            flex-direction: row;
            align-items: center;
            text-align: center;
        }
        .incomeTotal {
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.75);
            margin-right: 12px;
            align-items: center;
            text-align: center;
        }
        .incomeTotal h1 {
            color: #41bd57;
        }
        .expensesTotal {
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.75);
            margin-left: 12px;
            align-items: center;
            text-align: center;

        }
        .expensesTotal h1 {
            color: #a43434;
        }
        .averageExpenses {
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.75);
            margin-left: 24px;
            align-items: center;
            text-align: center;
        }
        .budgetTable {
            margin: 32px auto;
            width: fit-content;
            text-align: center;
        }
        .budgetTable form {
            display: flex;
            flex-direction: row;
            align-items: center;
            text-align: center;
        }
        .budgetTable form input,
        .budgetTable form button {
            margin-right: 5px;
        }

        .budgetTable form[action="deleteBudget.jsp"] {
            margin-top: 0;
        }
        .budgetEntry form {
            align-items: center;
            text-align: center;
        }
        .sectionTitle {
            text-align: center;
            margin: 64px;
        }
    </style>

</head>
<body>

<jsp:include page="header.jsp"></jsp:include>



<h1 class="sectionTitle">Transactions</h1>
<div class="info">
    <div class="moneys">
        <%
            BalanceDao balanceDao = new BalanceDaoImpl();
            double balanceAmount = balanceDao.selectBalance(1).getBalance();
        %>
        <h2>Balance Amount: <%= balanceAmount %></h2>
        <form action="updateBalanceAndTransaction.jsp" method="post">
            <button type="submit">Payday</button>
        </form>

        <%
            SavingsDao savingsDao = new SavingsDaoImpl();
            double savingsAmount = savingsDao.selectSavings(1).getSavings();
        %>
        <h2>Savings Amount: <%= savingsAmount %></h2>
        <form action="updateSavings.jsp" method="post">
            <input type="number" step="0.01" id="newSavings" name="newSavings" required max="<%= balanceAmount %>">
            <button type="submit">Add to Savings</button>
        </form>

        <%
            IncomeDao incomeDao = new IncomeDaoImpl();
            double incomeAmount = incomeDao.selectIncome(1).getIncome();
        %>
        <h2>Income Amount: <%= incomeAmount %></h2>
        <form action="updateIncome.jsp" method="post">
            <input type="number" step="0.01" id="newIncome" name="newIncome" required>
            <button type="submit">Update Income</button>
        </form>
    </div>
    <div class="newTransaction">
        <h2>New Transaction</h2>
        <form id="addTransactionForm" action="addTransaction.jsp" method="post">
            <div class="form-group">
                <h2>Transaction Type</h2>
                <input type="text" id="transactionType" name="transactionType" required>
            </div>
            <div class="form-group">
                <h2>Transaction Amount</h2>
                <input type="number" step="0.01" id="transactionAmount" name="transactionAmount" required required max="<%= balanceAmount %>">
            </div>
            <button class="submitButton" type="submit">Add Transaction</button>
        </form>
    </div>
    <div class="table">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Transaction Number</th>
                <th>Type</th>
                <th>Amount</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            <%
                TransactionsDao transactionsDao = new TransactionsDaoImpl();
                List<Transactions> allTransactions = transactionsDao.selectAllTransactions();
                if (!allTransactions.isEmpty()) {
                    for (int i = allTransactions.size() - 1; i >= 0; i--) {
                        Transactions transactions = allTransactions.get(i);
            %>
            <tr>
                <td><%= transactions.getTransactionNumber() %></td>
                <td><%= transactions.getTransactionType() %></td>
                <td><%= transactions.getTransactionAmount() %></td>
                <td><%= transactions.getTransactionDate() %></td>
            </tr>
            <% }
                if (allTransactions.isEmpty()) { %>
            <tr>
                <td colspan="4" class="text-center">No Transactions available</td>
            </tr>
            <% } }%>
            </tbody>
        </table>
    </div>
</div>



<h1 class="sectionTitle">Spending</h1>
<%
    int currentMonth = java.time.LocalDate.now().getMonthValue();
    double incomeForMonth = incomeDao.selectIncomeByMonth(currentMonth);
    double expensesForMonth = incomeDao.selectExpensesByMonth(currentMonth);

    LocalDate currentDate = LocalDate.now();

    LocalDate thirtyDaysAgo = currentDate.minusDays(30);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String currentDateStr = currentDate.format(formatter);
    String thirtyDaysAgoStr = thirtyDaysAgo.format(formatter);
    double totalExpensesForLast30Days = transactionsDao.selectTotalExpensesBetweenDates(thirtyDaysAgoStr, currentDateStr);

    double averageExpensesForLast30Days = totalExpensesForLast30Days / 30;
%>
<div class="totals">
    <div class="incomeTotal">
        <h2>Income for Current Month</h2>
        <h1><%= incomeForMonth %></h1>
    </div>
    <div class="expensesTotal">
        <h2>Expenses for Current Month</h2>
        <h1><%= expensesForMonth %></h1>
    </div>
    <div class="averageExpenses">
        <h2>Average Expenses Past 30 Days</h2>
        <h1><%= averageExpensesForLast30Days %></h1>
    </div>
</div>



<h1 class="sectionTitle">Budgets</h1>
<%
    BudgetDao budgetDao = new BudgetDaoImpl();
    List<Budget> budgets = budgetDao.selectAllBudgets();
%>
<div class="budgetTable">
    <table class="table table-bordered">
        <tr>
            <th>Budget Name</th>
            <th>Budget Max</th>
            <th>Budget Amount</th>
            <th>Update</th>
            <th>Delete</th>
        </tr>
        <% for (Budget budget : budgets) { %>
        <tr>
            <td><%= budget.getBudgetName() %></td>
            <td><%= budget.getBudgetMax() %></td>
            <td><%= budget.getBudgetAmount() %></td>
            <td class="td">
                <form action="editBudget.jsp" method="post">
                    <input type="hidden" name="budgetId" value="<%= budget.getBudgetId() %>">
                    <input type="text" name="budgetName" value="<%= budget.getBudgetName() %>">
                    <input type="number" name="budgetMax" value="<%= budget.getBudgetMax() %>">
                    <input type="number" name="budgetAmount" value="<%= budget.getBudgetAmount() %>">
                    <button type="submit">Update</button>
                </form>
            </td>
            <td>
                <form action="deleteBudget.jsp" method="post">
                    <input type="hidden" name="budgetId" value="<%= budget.getBudgetId() %>">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>

<div class="budgetEntry">
    <form action="addBudget.jsp" method="post">
        <input type="text" name="budgetName" placeholder="Budget Name">
        <input type="number" name="budgetMax" placeholder="Budget Max">
        <input type="number" name="budgetAmount" placeholder="Budget Amount">
        <button type="submit">Add New Budget</button>
    </form>
</div>

</div>

</body>
</html>
