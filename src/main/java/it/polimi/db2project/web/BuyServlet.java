package it.polimi.db2project.web;

import it.polimi.db2project.entities.*;
import it.polimi.db2project.services.EmployeeService;
import it.polimi.db2project.services.UserService;
import jakarta.ejb.EJB;
import jakarta.ejb.Local;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet("/buyPage")
public class BuyServlet extends HttpServlet {

    @EJB
    private UserService userService;

    private List<ValidityPeriodEntity> validityPeriods;
    private List<OptionalProductEntity> optionalProducts;
    String srvPackageToSelect;
    String destServlet = null;
    ServicePackageEntity servicePackage = null;
    String packageSelected = null;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (request.getParameter("servPackageBtn")!=null) {

            srvPackageToSelect = request.getParameter("srvPackage");

            destServlet = "buyPage";

            packageSelected = userService.findByServicePackageToSelectID(Long.parseLong(srvPackageToSelect)).get().getName();

            validityPeriods = userService.findValPeriodsOfService(Long.parseLong(srvPackageToSelect));
            optionalProducts = userService.findOptProdOfService(Long.parseLong(srvPackageToSelect));
        }

        if (request.getParameter("confirmBtn") != null){

            String valPeriod = request.getParameter("valPeriod");

            String[] optProducts = request.getParameterValues("optProducts");
            String startDateStr = request.getParameter("startDate");

            LocalDate startDate = null;
            LocalDate endDate = null;
            java.sql.Date sqlStartDate = null;
            java.sql.Date sqlEndDate = null;


            //service package to select
            ServicePackageToSelectEntity servicePackageToSelect = userService.findByServicePackageToSelectID(Long.parseLong(srvPackageToSelect)).get();

            //validity period
            ValidityPeriodEntity validityPeriod = userService.findByValPeriodID(Long.parseLong(valPeriod)).get();
            
            //optional products
            ArrayList<OptionalProductEntity> optionalProducts = null;
            float totalValueOptProducts = 0;
            if(optProducts!=null){
                optionalProducts = new ArrayList<>();
                for (String optProd : optProducts) {
                    optionalProducts.add(userService.findByOptProdID(Long.parseLong(optProd)).get());
                }
                for (OptionalProductEntity optionalProduct : optionalProducts)
                    totalValueOptProducts = totalValueOptProducts + optionalProduct.getMonthlyFee() * validityPeriod.getNumOfMonths();

            }

            float valuePackage = validityPeriod.getMonthlyFee() * validityPeriod.getNumOfMonths();


            //start date and end date
            startDate = LocalDate.parse(startDateStr);
            endDate = startDate.plusMonths(validityPeriod.getNumOfMonths());

            sqlStartDate = java.sql.Date.valueOf(startDate);
            sqlEndDate = java.sql.Date.valueOf(endDate);

            servicePackage = new ServicePackageEntity(servicePackageToSelect,
                                                        validityPeriod,
                                                        sqlStartDate,
                                                        sqlEndDate,
                                                        valuePackage,
                                                        totalValueOptProducts,
                                                        optionalProducts);

            session.setAttribute("servicePackage", servicePackage);

            destServlet = "confirmationPage";
        }
        response.sendRedirect(destServlet);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<ServicePackageToSelectEntity> servicePackagesToSelect = userService.findAllServicePackageToSelect();

        req.setAttribute("servicePackagesToSelect", servicePackagesToSelect);
        req.setAttribute("validityPeriods", validityPeriods);
        req.setAttribute("optionalProducts", optionalProducts);
        req.setAttribute("packageSelected", packageSelected);


        RequestDispatcher dispatcher = req.getRequestDispatcher("buyPage.jsp");
        dispatcher.forward(req, resp);
    }
}