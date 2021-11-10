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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private UserService userService;
    @EJB
    private EmployeeService employeeService;

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserEntity user = null;
        EmployeeEntity employee = null;
        String destServlet;
        try {
            employee = employeeService.checkCredentials(username, password);
        } catch (CredentialsException e) {
            e.printStackTrace();
        }

        if (employee != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", employee);
            destServlet = "homePageEmployee";
        }
        else {
            try {
                user = userService.checkCredentials(username, password);
            } catch (CredentialsException e) {
                e.printStackTrace();
            }
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                destServlet = "homePageClient";
            } else {
                destServlet = "login?loginFailed=true";
            }
        }
        response.sendRedirect(destServlet); // <---- questa è una servlet
        }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        String message = "Invalid email/password";
        if (req.getParameter("loginFailed") != null) {
            req.setAttribute("messageLogin", message);
        }
        dispatcher.forward(req, resp);
    }
}
