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
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private UserService userService;
    @EJB
    private EmployeeService employeeService;

    public SignupServlet() {
        super();
    }


    //destServlet = "signup?signupFailed=true"; FIL DEVI SPIEGARMI QUESTE COSE QUA BACI BACI
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean isEmployee = request.getParameter( "employee" ) != null;
        String destServlet = "signup";


        //se sono un employee
        if(isEmployee) {
            EmployeeEntity employee;
            if (employeeService.findByUsername(username).isPresent() || employeeService.findByEmail(email).isPresent()) {
                destServlet = "signup?signupFailed=true";
            }
            else {
                try {
                    employee = employeeService.createEmployee(username, email, password);

                    if (employee != null) {
                        destServlet = "signup?signupDone=true";
                    }
                    else {
                        destServlet = "signup?signupError=true";
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        //se non sono un employee
        else {
            UserEntity user;
            if (userService.findByUsername(username).isPresent() || userService.findByEmail(email).isPresent()) {
                destServlet = "signup?signupFailed=true";
            }
            else {
                try {
                    user = userService.createUser(username, email, password);

                    if (user != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                        destServlet = "signup?signupDone=true";
                    } else {
                        destServlet = "signup?signupError=true";
                    }

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