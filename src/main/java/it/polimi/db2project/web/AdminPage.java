package it.polimi.db2project.web;

import it.polimi.db2project.services.EmployeeService;
import jakarta.ejb.EJB;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



import java.io.IOException;

@WebServlet("/adminPage")
public class AdminPage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //private TemplateEngine templateEngine;

    @EJB
    private EmployeeService employeeService;


    public void init() throws ServletException {
        /*ServletContext servletContext = (ServletContext) getServletContext();

        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver((javax.servlet.ServletContext) servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");*/
    }


    public AdminPage() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

}
