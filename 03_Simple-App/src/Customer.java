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
        writer.write(cusId + cusName + cusAddress + cusSalary);

    }
}
