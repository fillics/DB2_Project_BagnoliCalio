package it.polimi.db2project.web;

import it.polimi.db2project.entities.ServiceEntity;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/homePageCustomer")
public class HomePageCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    ServiceEntity serviceEntity;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        RequestDispatcher dispatcher = req.getRequestDispatcher("homeCustomer.jsp");
        dispatcher.forward(req, resp);
    }
}
