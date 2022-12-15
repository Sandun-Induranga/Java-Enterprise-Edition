package servlet;

import model.CustomerDTO;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author : Sandun Induranga
 * @since : 0.1.0
 **/

@WebServlet("/order")
public class PurchaseOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getParameter("option");

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");
            PreparedStatement pstm;
            ResultSet resultSet;

            switch (option) {
                case "customer":
                    String cusId = req.getParameter("cusId");
                    pstm = connection.prepareStatement("SELECT * FROM Customer WHERE customerId=?");
                    pstm.setString(1, cusId);
                    resultSet = pstm.executeQuery();

                    if (resultSet.next()) {
                        JsonObjectBuilder obj = Json.createObjectBuilder();
                        obj.add("cusId", resultSet.getString(1));
                        obj.add("cusName", resultSet.getString(2));
                        obj.add("cusAddress", resultSet.getString(3));
                        obj.add("cusSalary", resultSet.getString(4));

                        obj.add("state", "OK");
                        obj.add("message", "Successfully Loaded..!");
                        obj.add("data", obj.build());
                        resp.setStatus(200);

                        resp.getWriter().print(obj.build());

                    } else {
                        throw new SQLException("No Such Customer ID");
                    }
                    break;
                case "item":
                    String code = req.getParameter("code");
                    pstm = connection.prepareStatement("SELECT * FROM Item WHERE code=?");
                    pstm.setString(1, code);
                    resultSet = pstm.executeQuery();

                    if (resultSet.next()) {
                        JsonObjectBuilder obj = Json.createObjectBuilder();
                        obj.add("code", resultSet.getString(1));
                        obj.add("name", resultSet.getString(2));
                        obj.add("qty", resultSet.getInt(3));
                        obj.add("price", resultSet.getDouble(4));

                        obj.add("state", "OK");
                        obj.add("message", "Successfully Loaded..!");
                        obj.add("data", obj.build());
                        resp.setStatus(200);

                        resp.getWriter().print(obj.build());

                    } else {
                        throw new SQLException("No Such Customer ID");
                    }
                    break;
            }

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder obj = Json.createObjectBuilder();

            obj.add("state", "Error");
            obj.add("message", e.getLocalizedMessage());
            obj.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            resp.getWriter().print(obj.build());
        } catch (SQLException e) {
            JsonObjectBuilder obj = Json.createObjectBuilder();

            obj.add("state", "Error");
            obj.add("message", e.getLocalizedMessage());
            obj.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            resp.getWriter().print(obj.build());
        }

        resp.addHeader("Content-Type", "application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonReader reader = Json.createReader(req.getReader());

        JsonObject details = reader.readObject();
        JsonObject customer = details.getJsonObject("customer");
        JsonArray items = details.getJsonArray("items");
        String total = details.getString("total");

        boolean b = false;

        try {

            String orderId = generateNewID();

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO `Order` VALUES (?,?,?)");
            pstm.setString(1, orderId);
            pstm.setString(2, customer.getString("cusId"));
            pstm.setDouble(3, Double.parseDouble(total));

            pstm.executeUpdate();

            for (JsonValue item : items) {

                pstm = connection.prepareStatement("INSERT INTO Order_Detail VALUES (?,?,?,?)");

                JsonObject jsonObject = item.asJsonObject();

                pstm.setString(1, orderId);
                pstm.setString(2, jsonObject.getString("code"));
                pstm.setDouble(3, Double.parseDouble(jsonObject.getString("unitPrice")));
                pstm.setInt(4, Integer.parseInt(jsonObject.getString("qty")));

                pstm.executeUpdate();

                pstm = connection.prepareStatement("UPDATE Item SET qty=qty-? WHERE code=?");

                pstm.setInt(1, Integer.parseInt(jsonObject.getString("qty")));
                pstm.setString(2, jsonObject.getString("code"));

                b = pstm.executeUpdate() > 0;

            }

            if (b) {

                JsonObjectBuilder obj = Json.createObjectBuilder();

                obj.add("state", "OK");
                obj.add("message", "Order Placed");
                obj.add("data", "");
                resp.setStatus(200);

                resp.getWriter().print(obj.build());

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
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 // Client Side Errors

            resp.getWriter().print(obj.build());
        }
        resp.addHeader("Access-Control-Allow-Origin", "*");

    }

    public String generateNewID() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");
        PreparedStatement pstm = connection.prepareStatement("SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1;");
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            String id = rst.getString("orderId");
            int newCustomerId = Integer.parseInt(id.replace("O", "")) + 1;
            return String.format("O%03d", newCustomerId);
        } else {
            return "O001";
        }
    }
}
