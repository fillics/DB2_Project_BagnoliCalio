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

        String message = "error";
        if (req.getParameter("creationOptProductFailed") != null) {
            req.setAttribute("message", message);
        }
        dispatcher.forward(req, resp);
    }
}
