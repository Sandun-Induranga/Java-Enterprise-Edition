package servlet;

import com.mysql.cj.xdevapi.JsonArray;
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

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String code = req.getParameter("code");
        String name = req.getParameter("itemName");
        int qty = Integer.parseInt(req.getParameter("qtyOnHand"));
        double price = Double.parseDouble(req.getParameter("price"));

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");

            PreparedStatement pstm = connection.prepareStatement("INSERT INTO Item VALUES (?,?,?,?)");

            pstm.setString(1, code);
            pstm.setString(2, name);
            pstm.setInt(3, qty);
            pstm.setDouble(4, price);
            boolean b = pstm.executeUpdate() > 0;

            if (b) {

                JsonObjectBuilder obj = Json.createObjectBuilder();

                obj.add("state", "OK");
                obj.add("message", "Successfully Added");
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


        JsonArrayBuilder allItems = Json.createArrayBuilder();

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item");
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {

                JsonObjectBuilder item = Json.createObjectBuilder();

                item.add("code", resultSet.getString(1));
                item.add("name", resultSet.getString(2));
                item.add("qty", resultSet.getInt(3));
                item.add("price", resultSet.getDouble(4));

                allItems.add(item.build());

            }

            JsonObjectBuilder obj = Json.createObjectBuilder();

            obj.add("state", "OK");
            obj.add("message", "Successfully Loaded..!");
            obj.add("data", allItems);
            resp.setStatus(200);

            resp.getWriter().print(obj.build());

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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String code = req.getParameter("code");

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item WHERE code=?");

            pstm.setString(1, code);
            boolean b = pstm.executeUpdate() > 0;

            if (b) {

                JsonObjectBuilder obj = Json.createObjectBuilder();

                obj.add("state", "OK");
                obj.add("message", "Successfully Deleted");
                obj.add("data", "");
                resp.setStatus(200);

                resp.getWriter().print(obj.build());

            } else {
                throw new SQLException("No Such Item Code");
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

        JsonObject item = reader.readObject();

        String code = item.getString("code");
        String name = item.getString("itemName");
        System.out.println(item.getInt("qtyOnHand"));
        int qty = item.getInt("qtyOnHand");
        String price = item.getString("price");

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");
            PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET name=?, qty=?, price=? WHERE code=?");

            pstm.setString(1, name);
            pstm.setInt(2, qty);
            pstm.setString(3, price);
            pstm.setString(4, code);
            boolean b = pstm.executeUpdate() > 0;
            if (b) {
                JsonObjectBuilder obj = Json.createObjectBuilder();

                obj.add("state", "OK");
                obj.add("message", "Successfully Updated");
                obj.add("data", "");
                resp.setStatus(200);

                resp.getWriter().print(obj.build());
            } else {
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

