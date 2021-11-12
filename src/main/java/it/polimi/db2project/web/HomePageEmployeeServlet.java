package it.polimi.db2project.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/homePageEmployee")
public class HomePageEmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("homeEmployee.jsp");
        String message;
        if (req.getParameter("creationOptProductFailed") != null) {
            message = "Error in the creation of the optional product, please retry!";
            req.setAttribute("messageOptProduct", message);
        }
        else if (req.getParameter("optProductCreated") != null) {
            message = "Optional Product created successfully!";
            req.setAttribute("messageOptProduct", message);
        }
        dispatcher.forward(req, resp);
    }
}
