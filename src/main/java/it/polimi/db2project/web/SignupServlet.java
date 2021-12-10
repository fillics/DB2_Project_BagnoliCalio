package it.polimi.db2project.web;

import it.polimi.db2project.entities.EmployeeEntity;
import it.polimi.db2project.entities.UserEntity;
import it.polimi.db2project.services.EmployeeService;
import it.polimi.db2project.services.UserService;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private UserService userService;
    @EJB
    private EmployeeService employeeService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean isEmployee = request.getParameter( "employee" ) != null;
        String destServlet = "signup";

        boolean checkLength = username.length() != 0 && email.length() != 0 && password.length() != 0;
        boolean checkAlreadySignup = employeeService.findByUsername(username).isPresent() || employeeService.findByEmail(email).isPresent() ||
                userService.findByUsername(username).isPresent() || userService.findByEmail(email).isPresent();

        //employee case
        if(isEmployee) {
            EmployeeEntity employee;

            if (checkAlreadySignup) destServlet = "signup?signupFailed=true";
            else {
                try {
                    if(checkLength){
                        employee = employeeService.createEmployee(username, email, password);
                        if (employee != null) destServlet = "signup?signupDone=true";
                        else destServlet = "signup?signupError=true";
                    }
                    else destServlet = "signup?signupError=true";

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        //user case
        else {
            UserEntity user;
            if (checkAlreadySignup) destServlet = "signup?signupFailed=true";
            else {
                try {
                    if(checkLength){
                        user = userService.createUser(username, email, password);
                        if (user != null) destServlet = "signup?signupDone=true";
                        else destServlet = "signup?signupError=true";
                    }
                    else destServlet = "signup?signupError=true";

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        response.sendRedirect(destServlet);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        String message;
        if (req.getParameter("signupFailed") != null) {
            message = "Username/Email already exist: try again by entering a new one";
            req.setAttribute("messageSignUp", message);
        }
        else if (req.getParameter("signupError") != null) {
            message = "Registration failed. Retry!";
            req.setAttribute("messageSignUp", message);
        }
        else if (req.getParameter("signupDone") != null) {
            message = "Registration completed. Now you can log in!";
            req.setAttribute("messageSignUp", message);
        }
        dispatcher.forward(req, resp);
    }

}