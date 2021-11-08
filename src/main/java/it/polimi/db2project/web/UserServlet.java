package it.polimi.db2project.web;

import it.polimi.db2project.entities.UserEntity;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class UserServlet extends HttpServlet {


    public void newPage(UserEntity user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String destPage = "index.jsp";

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            destPage = "home.jsp";
        }
        // If the login fails, sets error message as an attribute in the request, and forwards to the login page again:
        else {
            String message = "Invalid email/password";
            request.setAttribute("message", message);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
        dispatcher.forward(request, response);
    }
}
