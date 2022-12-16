package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author : Sandun Induranga
 * @since : 0.1.0
 **/

//@WebFilter(urlPatterns = "/a")
public class FilterOne implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter One Initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter One do Filter Method Invoked");
        servletResponse.getWriter().write("Request Received From A");
    }

    @Override
    public void destroy() {
        System.out.println("Filter One Destroyed");
    }
}
