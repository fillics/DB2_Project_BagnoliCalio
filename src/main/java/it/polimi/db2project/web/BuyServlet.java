package it.polimi.db2project.web;

import it.polimi.db2project.entities.*;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@WebServlet("/buyPage")
public class BuyServlet extends HttpServlet {

    @EJB
    private UserService userService;

    private List<ValidityPeriodEntity> validityPeriods;
    private List<OptionalProductEntity> optionalProducts;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String srvPackage = request.getParameter("srvPackage");

        Optional<ServicePackageToSelectEntity> servicePackageToSelect = null;
        String destServlet = "buyPage";

        //servicePackageToSelect = userService.findByServicePackageToSelectID(Long.parseLong(srvPackage));

        validityPeriods = userService.findValPeriodsOfService(Long.parseLong(srvPackage));
        optionalProducts = userService.findOptProdOfService(Long.parseLong(srvPackage));

        response.sendRedirect(destServlet);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ServicePackageToSelectEntity> servicePackagesToSelect = userService.findAllServicePackageToSelect();

        req.setAttribute("servicePackagesToSelect", servicePackagesToSelect);
        req.setAttribute("validityPeriods", validityPeriods);
        req.setAttribute("optionalProducts", optionalProducts);

        RequestDispatcher dispatcher = req.getRequestDispatcher("buyPage.jsp");
        dispatcher.forward(req, resp);
    }
}