package it.polimi.db2project.web;

import it.polimi.db2project.entities.UserEntity;
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
import java.sql.SQLException;

@WebServlet("/signup")
public class UserSignupServlet extends UserServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private UserService userService;

    public UserSignupServlet() {
        super();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        UserEntity user = null;
        try {
            user = userService.createUser(username, email, password);
            newPage(user, request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}

