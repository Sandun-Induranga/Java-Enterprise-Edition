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
        String operation = req.getParameter("operation");

        //Save Customer
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");

            System.out.println(operation);
            switch (operation) {
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
                    System.out.println(cusId+operation);
                    PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE customerId=?");

                    pstm.setString(1, cusId);
                    pstm.executeUpdate();
                    break;

                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("customer");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<CustomerDTO> customerDTOs = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer");
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                customerDTOs.add(new CustomerDTO(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        req.setAttribute("customers", customerDTOs); // Can add key value for a req object

        req.getRequestDispatcher("index.jsp").forward(req,resp);

    }
}
