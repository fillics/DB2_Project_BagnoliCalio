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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userID = request.getParameter("button2");
        if (request.getParameter("button1") != null) {

            srvPackageToSelect = request.getParameter("srvPackage");

            Optional<ServicePackageToSelectEntity> servicePackageToSelect = null;
            destServlet = "buyPage";

            //servicePackageToSelect = userService.findByServicePackageToSelectID(Long.parseLong(srvPackage));
            validityPeriods = userService.findValPeriodsOfService(Long.parseLong(srvPackageToSelect));
            optionalProducts = userService.findOptProdOfService(Long.parseLong(srvPackageToSelect));

            response.sendRedirect(destServlet);
        }

        else if (userID != null){
            HttpSession session = request.getSession();
            session.setAttribute("userID", userID);
            String user_id = request.getParameter("button2");

            String valPeriod = request.getParameter("valPeriod");

            String[] optProducts = request.getParameterValues("optProducts");
            String startDateStr = request.getParameter("startDate");

            LocalDate startDate = null;
            LocalDate endDate = null;
            java.sql.Date sqlStartDate = null;
            java.sql.Date sqlEndDate = null;
            String destServlet;

            //userOwner
            UserEntity userOwner = userService.findByUserID(Long.parseLong(user_id)).get();

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


            //total Value
            float totalValue = totalValueOptProducts + (validityPeriod.getMonthlyFee() * validityPeriod.getNumOfMonths());

            //start date and end date
            startDate = LocalDate.parse(startDateStr);
            endDate = startDate.plusMonths(validityPeriod.getNumOfMonths());

            sqlStartDate = java.sql.Date.valueOf(startDate);
            sqlEndDate = java.sql.Date.valueOf(endDate);

            try {
                servicePackage = userService.createServicePackage(
                    sqlStartDate,
                    sqlEndDate,
                    totalValue,
                    servicePackageToSelect,
                    validityPeriod,
                    optionalProducts,
                    userOwner
                );

                } catch (SQLException e) {
                e.printStackTrace();
            }
            destServlet = "confirmationPage";
            response.sendRedirect(destServlet);
        }

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