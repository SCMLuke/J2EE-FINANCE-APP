//package com.example.assignment1;
//
//import java.sql.SQLException;
//import java.util.List;
//
//public class DatabaseTest {
//    public static void main(String[] args) throws SQLException {
//        ToDoDao toDoDao = new ToDoDaoImpl();
//
//        // Test insert
//        Todo newTodo = new Todo("Task Name","Task Description");
//
//        try {
//            toDoDao.insertTodo(newTodo);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // Test selectAllTodos
//        List<Todo> allTodos = toDoDao.selectAllTodos();
//        System.out.println("All Todos:");
//        for (Todo todo : allTodos) {
//            System.out.println(todo.getTaskNumber() + " - " + todo.getTaskName() + " - " + todo.getTaskDescription());
//        }
//
//        // Test selectTodo
//        int todoId = 1;
//        Todo selectedTodo = toDoDao.selectTodo(todoId);
//        if (selectedTodo != null) {
//            System.out.println("\nSelected Todo:");
//            System.out.println(selectedTodo.getTaskNumber() + " - " + selectedTodo.getTaskName() + " - " + selectedTodo.getTaskDescription());
//        } else {
//            System.out.println("\nTodo not found.");
//        }
//
//        // Test delete
//        int taskNumberToDelete = 3;
//
//        try {
//            boolean isDeleted = toDoDao.deleteTodo(taskNumberToDelete);
//            if (isDeleted) {
//                System.out.println("Todo deleted successfully!");
//            } else {
//                System.out.println("Todo not found or unable to delete.");
//            }
//        } catch (SQLException e) {
//            // Handle SQL exceptions
//            e.printStackTrace();
//        }
//    }
//}
