package servlet;

import model.CustomerDTO;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
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
        String cusId = req.getParameter("cusId");

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");

            switch (option) {
                case "customer":
                    PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer WHERE customerId=?");
                    pstm.setString(1, cusId);
                    ResultSet resultSet = pstm.executeQuery();

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
}
