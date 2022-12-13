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
        String cusId = req.getParameter("cusId");

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer WHERE customerId=?");
            pstm.setString(1, cusId);
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()){
                JsonObjectBuilder obj = Json.createObjectBuilder();
                obj.add("cusId",resultSet.getString(1));
                obj.add("cusName",resultSet.getString(2));
                obj.add("cusAddress",resultSet.getString(3));
                obj.add("cusSalary",resultSet.getString(4));

                obj.add("state", "OK");
                obj.add("message", "Successfully Loaded..!");
                obj.add("data", obj.build());
                resp.setStatus(200);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
