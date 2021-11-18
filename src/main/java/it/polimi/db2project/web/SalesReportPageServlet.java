package it.polimi.db2project.web;


import it.polimi.db2project.entities.*;
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
import java.util.List;
import java.util.Optional;

@WebServlet("/salesReportPage")
public class SalesReportPageServlet extends HttpServlet {

    @EJB
    private EmployeeService employeeService;
    @EJB
    private UserService userService;

    private List<ValidityPeriodEntity> validityPeriods;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("bottone");
        System.out.println(id);
        String srvPackage = request.getParameter("srvPackage");
        String srvPackageWithValPeriod = request.getParameter("srvPackageWithValPeriod");

        Optional<ServicePackageToSelectEntity> servicePackageToSelect = null;
        String destServlet = "salesReportPage";

        if(srvPackageWithValPeriod!=null){
            validityPeriods = userService.findValPeriodsOfService(Long.parseLong(srvPackage));
        }

        response.sendRedirect(destServlet);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<ServicePackageToSelectEntity> servicePackagesToSelect = employeeService.findAllServicePackageToSelect();
        req.setAttribute("servicePackagesToSelect", servicePackagesToSelect);
        req.setAttribute("validityPeriods", validityPeriods);

        RequestDispatcher dispatcher = req.getRequestDispatcher("salesReportPage.jsp");
        dispatcher.forward(req, resp);
    }
}
