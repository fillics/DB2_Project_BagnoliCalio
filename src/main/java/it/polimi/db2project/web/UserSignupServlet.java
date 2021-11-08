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
public class UserSignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private UserService userService;

    public UserSignupServlet() {
        super();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        UserEntity user = null;
        try {
            user = userService.createUser(username, email, password);
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}

