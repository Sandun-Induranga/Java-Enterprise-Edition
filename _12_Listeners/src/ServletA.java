import javax.servlet.ServletContext;
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
@WebServlet(urlPatterns = "/a")
public class ServletA extends HttpServlet {
    public ServletA() {
        System.out.println("Servlet A Initialized"); // When Object create
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Servlet A doGet() method invoked..!");
        ServletContext servletContext = getServletContext();
        String pKey = (String) servletContext.getAttribute("pKey");
        System.out.println(pKey);
    }

    @Override
    public void init() throws ServletException {
        System.out.println("Servlet A Init Method Invoked"); // When class become servlet
    }

    @Override
    public void destroy() {
        System.out.println("Servlet A Destroy Method Invoked"); // When server off
    }
}

// Servlet is a singleton class

// Servlets are managed by Servlet Container

// Life cycle of a Servlet

// Initiate -> Init -> Destroy
