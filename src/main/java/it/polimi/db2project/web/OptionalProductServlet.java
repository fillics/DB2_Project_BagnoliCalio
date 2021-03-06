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

@WebServlet("/optionalProduct")
public class OptionalProductServlet extends HttpServlet {
    @EJB
    private EmployeeService employeeService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String monthlyFee = request.getParameter("monthlyFee");

        OptionalProductEntity optionalProduct = null;
        String destServlet;

        if(employeeService.findByNameOptProd(name).isPresent()) destServlet = "homePageEmployee?optProductExists=true";
        else{
            try {
                optionalProduct = employeeService.createOptionalProduct(name, Float.parseFloat(monthlyFee));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (optionalProduct != null) destServlet = "homePageEmployee?optProductCreated=true";
            else destServlet = "homePageEmployee?creationOptProductFailed=true";
        }


        response.sendRedirect(destServlet);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("homeEmployee.jsp");
        dispatcher.forward(req, resp);
    }
}
