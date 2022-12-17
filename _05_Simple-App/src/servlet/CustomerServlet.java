package servlet;

import com.mysql.cj.xdevapi.JsonArray;
import db.DBConnection;
import model.CustomerDTO;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author : Sandun Induranga
 * @since : 0.1.0
 **/

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cusId = req.getParameter("cusId");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        double cusSalary = Double.parseDouble(req.getParameter("cusSalary"));
        System.out.println("come");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");

            PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?)");

            pstm.setString(1, cusId);
            pstm.setString(2, cusName);
            pstm.setString(3, cusAddress);
            pstm.setDouble(4, cusSalary);
            boolean b = pstm.executeUpdate() > 0;

            if (b) {

                JsonObjectBuilder obj = Json.createObjectBuilder();

                obj.add("state", "OK");
                obj.add("message", "Successfully Added");
                obj.add("data", "");
                resp.setStatus(200);

                resp.getWriter().print(obj.build());

            }

//            switch (operation) {
//                case "add": {
//
//                    double cusSalary = Double.parseDouble(req.getParameter("cusSalary"));
//
//                    PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?)");
//
//                    pstm.setString(1, cusId);
//                    pstm.setString(2, cusName);
//                    pstm.setString(3, cusAddress);
//                    pstm.setDouble(4, cusSalary);
//                    pstm.executeUpdate();
//                    break;
//
//                }
//                case "update": {
//
//                    double cusSalary = Double.parseDouble(req.getParameter("cusSalary"));
//
//                    PreparedStatement pstm = connection.prepareStatement("UPDATE Customer SET customerName=?, address=?, salary=? WHERE customerId=?");
//
//                    pstm.setString(1, cusName);
//                    pstm.setString(2, cusAddress);
//                    pstm.setDouble(3, cusSalary);
//                    pstm.setString(4, cusId);
//                    pstm.executeUpdate();
//                    break;
//
//                }
//                case "delete": {
//                    System.out.println(cusId);
//
//                    PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE customerId=?");
//
//                    pstm.setString(1, cusId);
//                    pstm.executeUpdate();
//                    break;
//
//                }
//            }

        } catch (ClassNotFoundException e) {

            JsonObjectBuilder obj = Json.createObjectBuilder();

            obj.add("state", "Error");
            obj.add("message", e.getLocalizedMessage());
            obj.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 // Server Side Errors

            resp.getWriter().print(obj.build());

        } catch (SQLException e) {
            JsonObjectBuilder obj = Json.createObjectBuilder();

            obj.add("state", "Error");
            obj.add("message", e.getLocalizedMessage());
            obj.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 // Client Side Errors

            resp.getWriter().print(obj.build());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        String json = "[";
//
//        try {
//
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");
//            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer");
//            ResultSet resultSet = pstm.executeQuery();
//
//            while (resultSet.next()) {
//
//                json += "{\"id\":\""+resultSet.getString(1)+"\",\"name\":\""+resultSet.getString(2)+"\",\"address\":\""+resultSet.getString(3)+"\",\"salary\":"+resultSet.getInt(4)+"},";
//
//            }
//
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//
//        String jsonArray = json.substring(0,json.length()-1);
//        jsonArray += "]";
//
//        resp.addHeader("Content-Type","application/json");
//        resp.getWriter().write(jsonArray);


        // ******************    By Using Json Library    *********************


        JsonArrayBuilder allCustomers = Json.createArrayBuilder();

        try {

//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");

            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer");
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {

                JsonObjectBuilder customer = Json.createObjectBuilder();

                customer.add("id", resultSet.getString(1));
                customer.add("name", resultSet.getString(2));
                customer.add("address", resultSet.getString(3));
                customer.add("salary", resultSet.getDouble(4));

                allCustomers.add(customer.build());

            }

            JsonObjectBuilder obj = Json.createObjectBuilder();

            obj.add("state", "OK");
            obj.add("message", "Successfully Loaded..!");
            obj.add("data", allCustomers);
            resp.setStatus(200);

            resp.getWriter().print(obj.build());

//        } catch (ClassNotFoundException e) {
//            JsonObjectBuilder obj = Json.createObjectBuilder();
//
//            obj.add("state", "Error");
//            obj.add("message", e.getLocalizedMessage());
//            obj.add("data", "");
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 // Server Side Errors
//
//            resp.getWriter().print(obj.build());
        } catch (SQLException e) {
            JsonObjectBuilder obj = Json.createObjectBuilder();

            obj.add("state", "Error");
            obj.add("message", e.getLocalizedMessage());
            obj.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 500 // Server Side Errors

            resp.getWriter().print(obj.build());
        }

//        resp.addHeader("Access-Control-Allow-Origin", "*");

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String cusId = req.getParameter("cusId");

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE customerId=?");

            pstm.setString(1, cusId);
            boolean b = pstm.executeUpdate() > 0;

            if (b) {

                JsonObjectBuilder obj = Json.createObjectBuilder();

                obj.add("state", "OK");
                obj.add("message", "Successfully Deleted");
                obj.add("data", "");
                resp.setStatus(200);

                resp.getWriter().print(obj.build());

            }else {
                throw new SQLException("No Such Customer ID");
            }

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder obj = Json.createObjectBuilder();

            obj.add("state", "Error");
            obj.add("message", e.getLocalizedMessage());
            obj.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 // Server Side Errors

            resp.getWriter().print(obj.build());
        } catch (SQLException e) {
            JsonObjectBuilder obj = Json.createObjectBuilder();

            obj.add("state", "Error");
            obj.add("message", e.getLocalizedMessage());
            obj.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 500 // Server Side Errors

            resp.getWriter().print(obj.build());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonReader reader = Json.createReader(req.getReader());

        JsonObject customer = reader.readObject();

        String cusId = customer.getString("id");
        String cusName = customer.getString("name");
        String cusAddress = customer.getString("address");
        String cusSalary = customer.getString("cusSalary");

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");
            PreparedStatement pstm = connection.prepareStatement("UPDATE Customer SET customerName=?, address=?, salary=? WHERE customerId=?");

            pstm.setString(1, cusName);
            pstm.setString(2, cusAddress);
            pstm.setString(3, cusSalary);
            pstm.setString(4, cusId);
            boolean b = pstm.executeUpdate() > 0;
            if (b){
                JsonObjectBuilder obj = Json.createObjectBuilder();

                obj.add("state", "OK");
                obj.add("message", "Successfully Updated");
                obj.add("data", "");
                resp.setStatus(200);

                resp.getWriter().print(obj.build());
            }else {
                throw new SQLException("No Such Customer ID");
            }
        } catch (ClassNotFoundException e) {
            JsonObjectBuilder obj = Json.createObjectBuilder();

            obj.add("state", "Error");
            obj.add("message", e.getLocalizedMessage());
            obj.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 // Server Side Errors

            resp.getWriter().print(obj.build());
        } catch (SQLException e) {
            JsonObjectBuilder obj = Json.createObjectBuilder();

            obj.add("state", "Error");
            obj.add("message", e.getLocalizedMessage());
            obj.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 500 // Server Side Errors

            resp.getWriter().print(obj.build());
        }

    }

}

// ContentType Header
// Can see the type of data that send or received

// If use FormData then, added a value to ContentType "x-www-form-url-encode"