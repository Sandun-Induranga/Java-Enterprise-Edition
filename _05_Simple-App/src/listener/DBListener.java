package listener;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author : Sandun Induranga
 * @since : 0.1.0
 **/
//@WebListener
//public class DBListener implements ServletContextListener {
//
//    @Override
//    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        BasicDataSource bds = new BasicDataSource();
//        bds.setDriverClassName("com.mysql.jdbc.Driver");
//        bds.setUrl("jdbc:mysql://localhost:3306/POS");
//        bds.setPassword("1234");
//        bds.setUsername("sandu");
//        // how many connection
//        bds.setMaxTotal(2);
//        // how many connection should be initialized from created connections
//        bds.setInitialSize(2);
//        ServletContext servletContext = servletContextEvent.getServletContext();
//        servletContext.setAttribute("pool", bds);
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//
//    }
//}
