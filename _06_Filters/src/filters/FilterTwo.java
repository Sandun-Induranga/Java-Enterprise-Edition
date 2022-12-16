package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author : Sandun Induranga
 * @since : 0.1.0
 **/

//@WebFilter(urlPatterns = "/b")
public class FilterTwo implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter Two Initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter Two do Filter Method Invoked");
        String name = servletRequest.getParameter("name");

        if (name.equals("ijse")) {
            servletResponse.getWriter().write("<h1>Authenticated User</h1>");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletResponse.getWriter().write("<h1>Non Authenticated User</h1>");
        }

    }

    @Override
    public void destroy() {
        System.out.println("Filter Two Destroyed");
    }
}
