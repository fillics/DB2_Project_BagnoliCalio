package it.polimi.db2project.web;

import it.polimi.db2project.entities.UserEntity;
import it.polimi.db2project.exception.CredentialsException;
import it.polimi.db2project.services.UserService;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class UserLoginServlet extends UserServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private UserService userService;

    public UserLoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            UserEntity user = userService.checkCredentials(username, password);
            newPage(user, request, response);
        } catch (CredentialsException ex) {
            throw new ServletException(ex);
        }
    }
}