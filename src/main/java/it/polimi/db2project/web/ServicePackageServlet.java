package it.polimi.db2project.web;

import it.polimi.db2project.entities.OptionalProductEntity;
import it.polimi.db2project.services.EmployeeService;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.function.ToDoubleBiFunction;

@WebServlet("/servicePackage")
public class ServicePackageServlet extends HttpServlet {
    @EJB
    private EmployeeService employeeService;


    // TODO: 11/11/2021 SISTEMARE 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String monthlyFee = request.getParameter("monthlyFee");

        OptionalProductEntity optionalProduct = null;
        String destServlet;
        try {
            optionalProduct = employeeService.createOptionalProduct(name, Float.parseFloat(monthlyFee));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (optionalProduct != null) {
            destServlet = "homePageEmployee";
        }
        else
        {
            destServlet = "homePageEmployee?creationOptProductFailed=true";
        }

        response.sendRedirect(destServlet); // <---- questa è una servlet
    }
    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("homeEmployee.jsp");
        dispatcher.forward(req, resp);
    }
}
