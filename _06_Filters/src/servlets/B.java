package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Sandun Induranga
 * @since : 0.1.0
 **/
@WebServlet(urlPatterns = "/b")
public class B extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Servlet servlets.B doGet Method Invoked");
        resp.getWriter().write("<h1>B : DO GET Called</h1>");
    }
}
