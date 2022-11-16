import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : Sandun Induranga
 * @since : 0.1.0
 **/

@WebServlet(urlPatterns = "/customer")
public class Customer extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusId = req.getParameter("cusId");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");

        PrintWriter writer = resp.getWriter();
        writer.write("<h1>Customers</h1><table><thead><tr><th>Customer Id</th><th>Customer Name</th><th>Address</th><th>Salary</th><tbody'></tr></thead><tr><td>" + cusId + "</td><td>" + cusName + "</td><td>" + cusAddress + "</td><td>" + cusSalary + "</td></tr></tbody></table> <style> body{ width: 97.5vw; height: 97.5vh; display: flex; justify-content: center; align-items: center; flex-direction: column;} h1{font-size: 3.5em; color: crimson;} table{border-collapse: collapse;} table,tr,th,td{border: 1px solid black;} thead,tbody{padding: 10px;font-size: 2em;} thead{background: purple; color: white} th,td{width: 250px; height: 50px}</style>");

    }
}
