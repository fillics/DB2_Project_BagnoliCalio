package it.polimi.db2project.web;

import it.polimi.db2project.entities.EmployeeEntity;
import it.polimi.db2project.entities.UserEntity;
import it.polimi.db2project.exception.CredentialsException;
import it.polimi.db2project.services.EmployeeService;
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
    @EJB
    private EmployeeService employeeService;

    public UserSignupServlet() {
        super();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean cbState = request.getParameter( "employee" ) != null;

        //se sono un employee
        if(cbState) {
            String destPage = "index.jsp";
            EmployeeEntity employee = null;
            if (employeeService.findByUsername(username).isPresent() || employeeService.findByEmail(email).isPresent()) {
                String message = "Username/Email already exists: try again by entering a new one";
                request.setAttribute("messageSignUp", message);
            } else {
                try {
                    employee = employeeService.createEmployee(username, email, password);

                    if (employee != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", employee);
                        destPage = "homeEmployee.jsp";
                    }
                    // If the login fails, sets error message as an attribute in the request, and forwards to the login page again:
                    else {
                        String message = "Registration failed. Retry!";
                        request.setAttribute("messageSignUp", message);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            dispatcher.forward(request, response);
        }
        //se non sono un employee
        else {
            UserEntity user = null;
            try {
                user = userService.createUser(username, email, password);
                String destPage = "index.jsp";

                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    destPage = "homeCustomer.jsp";
                }
                // If the login fails, sets error message as an attribute in the request, and forwards to the login page again:
                else {
                    String message = "Registration failed. Retry!";
                    request.setAttribute("messageSignUp", message);
                }

                RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
                dispatcher.forward(request, response);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }


}

