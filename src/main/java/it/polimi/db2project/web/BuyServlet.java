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
import java.sql.SQLOutput;
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
    String srvPackageToSelect;
    String destServlet = null;
    ServicePackageEntity servicePackage = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("button1") != null) {

            srvPackageToSelect = request.getParameter("srvPackage");

            Optional<ServicePackageToSelectEntity> servicePackageToSelect = null;
            destServlet = "buyPage";

            //servicePackageToSelect = userService.findByServicePackageToSelectID(Long.parseLong(srvPackage));
            validityPeriods = userService.findValPeriodsOfService(Long.parseLong(srvPackageToSelect));
            optionalProducts = userService.findOptProdOfService(Long.parseLong(srvPackageToSelect));

            response.sendRedirect(destServlet);
        }

        if (request.getParameter("button2") != null){

            String user_id = request.getParameter("button2");

            String valPeriod = request.getParameter("valPeriod");

            String[] optProducts = request.getParameterValues("optProducts");
            String startDateStr = request.getParameter("startDate");

            Date startDate = null;
            Date endDate = null;
            java.sql.Date sqlEndDate= null;
            java.sql.Date sqlStartDate = null;
            String destServlet;

            //userOwner
            UserEntity userOwner = userService.findByUserID(Long.parseLong(user_id)).get();

            //service package to select
            ServicePackageToSelectEntity servicePackageToSelect = userService.findByServicePackageToSelectID(Long.parseLong(srvPackageToSelect)).get();

            //optional products
            ArrayList<OptionalProductEntity> optionalProducts = new ArrayList<>();
            for (String optProd : optProducts) {
                optionalProducts.add(userService.findByOptProdID(Long.parseLong(optProd)).get());
            }

            //validity period
            ValidityPeriodEntity validityPeriod = userService.findByValPeriodID(Long.parseLong(valPeriod)).get();

            //total Value
            float totalValueOptProducts = 0;
            for (OptionalProductEntity optionalProduct : optionalProducts)
                totalValueOptProducts = totalValueOptProducts + optionalProduct.getMonthlyFee() * validityPeriod.getNumOfMonths();
            float totalValue = totalValueOptProducts + (validityPeriod.getMonthlyFee() * validityPeriod.getNumOfMonths());

            //start date and end date
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                startDate = format.parse(startDateStr);
                endDate = startDate;

                if (validityPeriod.getNumOfMonths() <= 11 - startDate.getMonth())
                    endDate.setMonth(startDate.getMonth()+validityPeriod.getNumOfMonths());
                else{
                    int yearsToAdd = validityPeriod.getNumOfMonths() / 12;
                    int monthsToAdd = validityPeriod.getNumOfMonths() % 12;
                    endDate.setMonth(startDate.getMonth() + monthsToAdd);
                    endDate.setYear(startDate.getYear() + yearsToAdd);
                }

//                //crea un oggetto Calendar o lo imposta alla data d
//                Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
//                c.setTime(startDate);
//
//                if (validityPeriod.getNumOfMonths() <= 11 - startDate.getMonth())
//                    c.add(Calendar.MONTH, validityPeriod.getNumOfMonths());
//                else {
//                    int yearsToAdd = validityPeriod.getNumOfMonths() / 12;
//                    int monthsToAdd = validityPeriod.getNumOfMonths() % 12;
//                    c.add(Calendar.YEAR, +yearsToAdd);
//                    c.add(Calendar.MONTH, +monthsToAdd);
//                }
//                endDate = c.getTime();

                sqlStartDate = new java.sql.Date(startDate.getTime());
                sqlEndDate = new java.sql.Date(endDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(sqlStartDate);
            System.out.println(validityPeriod.getNumOfMonths());
            System.out.println(sqlEndDate);
            System.out.println(totalValue);
            System.out.println(servicePackageToSelect);
            System.out.println(validityPeriod);
            System.out.println(userOwner);
            for (OptionalProductEntity opt : optionalProducts)
                System.out.println(opt);


            try {
                servicePackage = userService.createServicePackage(
                    startDate,
                    endDate,
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