package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Sandun Induranga
 * @since : 0.1.0
 **/
@WebFilter(urlPatterns = "/*")
public class DefaultFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Default Filter Invoked");
        String name = servletRequest.getParameter("name");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String servletPath = request.getServletPath();

        if (name.equals("ijse")) {
//            servletResponse.getWriter().write("<h1>Authenticated User</h1>");
            if (servletPath.equals("/a")||servletPath.equals("/b")){
                    filterChain.doFilter(servletRequest, servletResponse);
            }else {
                servletResponse.getWriter().write("<h1>No Servlet Like This</h1>");
            }

        } else {
            servletResponse.getWriter().write("<h1>Non Authenticated User</h1>");
        }
    }

    @Override
    public void destroy() {

    }
}
