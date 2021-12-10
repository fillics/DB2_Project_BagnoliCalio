package it.polimi.db2project.web;

import it.polimi.db2project.entities.EmployeeEntity;
import it.polimi.db2project.entities.ServicePackageEntity;
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


    ServicePackageEntity servicePackage;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String destServlet;

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserEntity user = null;
        EmployeeEntity employee = null;

        try {
            employee = employeeService.checkCredentials(username, password);
        } catch (CredentialsException e) {
            e.printStackTrace();
        }

        if (employee != null) {
            session.setAttribute("employee", employee);
            destServlet = "homePageEmployee";
        }
        else {
            try {
                user = userService.checkCredentials(username, password);
            } catch (CredentialsException e) {
                e.printStackTrace();
            }
            if(user!=null){
                if(servicePackage==null){
                    destServlet = "homePageCustomer";
                    if(user.getInsolvent()){
                        session.setAttribute("rejectedOrders", userService.findRejectedOrdersByUser(user.getUser_id()));
                    }
                }
                else destServlet = "confirmationPage";

                session.setAttribute("user", user);
            }
            else destServlet = "login?loginFailed=true";
        }

        resp.sendRedirect(destServlet);
        }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        servicePackage = (ServicePackageEntity) req.getSession(false).getAttribute("servicePackage");

        String message = "Invalid email/password";
        if (req.getParameter("loginFailed") != null) req.setAttribute("messageLogin", message);

        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }
}
