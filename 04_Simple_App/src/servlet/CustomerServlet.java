package servlet;

import db.DBConnection;
import model.CustomerDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author : Sandun Induranga
 * @since : 0.1.0
 **/

//@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cusId = req.getParameter("cusId");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        double cusSalary = Double.parseDouble(req.getParameter("cusSalary"));
        String option = req.getParameter("option");

        //Save Customer
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");

            System.out.println(option);
            switch (option) {
                case "add": {

                    PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?)");

                    pstm.setString(1, cusId);
                    pstm.setString(2, cusName);
                    pstm.setString(3, cusAddress);
                    pstm.setDouble(4, cusSalary);
                    pstm.executeUpdate();
                    break;

                }
                case "update": {

                    PreparedStatement pstm = connection.prepareStatement("UPDATE Customer SET customerName=?, address=?, salary=? WHERE customerId=?");

                    pstm.setString(1, cusName);
                    pstm.setString(2, cusAddress);
                    pstm.setDouble(3, cusSalary);
                    pstm.setString(4, cusId);
                    pstm.executeUpdate();
                    break;

                }
                case "delete": {
                    System.out.println(cusId+option);
                    PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE customerId=?");

                    pstm.setString(1, cusId);
                    pstm.executeUpdate();
                    break;

                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("index.jsp");

    }
//        PrintWriter writer = resp.getWriter();
//        writer.write("INSERT INTO Customer VALUES ("+cusId+","+cusName+","+cusAddress+","+cusSalary+")");
//        writer.write("<h1>Customers</h1><table><thead><tr><th>Customer Id</th><th>Customer Name</th><th>Address</th><th>Salary</th><tbody'></tr></thead><tr><td>" + cusId + "</td><td>" + cusName + "</td><td>" + cusAddress + "</td><td>" + cusSalary + "</td></tr></tbody></table> <style> body{ width: 97.5vw; height: 97.5vh; display: flex; justify-content: center; align-items: center; flex-direction: column;} h1{font-size: 3.5em; color: crimson;} table{border-collapse: collapse;} table,tr,th,td{border: 1px solid black;} thead,tbody{padding: 10px;font-size: 2em;} thead{background: purple; color: white} th,td{width: 250px; height: 50px}</style>");
//        System.out.println("INSERT INTO Customer VALUES ("+cusId+","+cusName+","+cusAddress+","+cusSalary+")");
//        System.out.println(cusId);
//    }


//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");
//            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer");
//            ResultSet resultSet = pstm.executeQuery();
//
//            while (resultSet.next()) {
//                PrintWriter writer = resp.getWriter();
//                writer.write("<h1>Customers</h1><table><thead><tr><th>Customer Id</th><th>Customer Name</th><th>Address</th><th>Salary</th><tbody'></tr></thead><tr><td>" + cusId + "</td><td>" + cusName + "</td><td>" + cusAddress + "</td><td>" + cusSalary + "</td></tr></tbody></table> <style> body{ width: 97.5vw; height: 97.5vh; display: flex; justify-content: center; align-items: center; flex-direction: column;} h1{font-size: 3.5em; color: crimson;} table{border-collapse: collapse;} table,tr,th,td{border: 1px solid black;} thead,tbody{padding: 10px;font-size: 2em;} thead{background: purple; color: white} th,td{width: 250px; height: 50px}</style>");
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
