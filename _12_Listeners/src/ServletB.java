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
public class ServletB extends HttpServlet {
    public ServletB() {
        System.out.println("Servlet B Initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Servlet B doGet() method invoked..!");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("Init Method Invoked");
    }

    @Override
    public void destroy() {
        System.out.println("Destroy Method Invoked");
    }
}
