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
import java.time.LocalDate;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        destServlet = "buyPage";

        String servPackageBtn = request.getParameter("servPackageBtn");

        if (servPackageBtn.length()!=0) {
            srvPackageToSelect = request.getParameter("srvPackage");

            packageSelected = userService.findByServicePackageToSelectID(Long.parseLong(srvPackageToSelect)).get().getName();

            validityPeriods = userService.findValPeriodsOfService(Long.parseLong(srvPackageToSelect));
            optionalProducts = userService.findOptProdOfService(Long.parseLong(srvPackageToSelect));
        }

        if (request.getParameter("confirmBtn") != null){

            String valPeriod = request.getParameter("valPeriod");

            String[] optProducts = request.getParameterValues("optProducts");
            String startDateStr = request.getParameter("startDate");

            LocalDate startDate, endDate;
            java.sql.Date sqlStartDate, sqlEndDate;

            ServicePackageToSelectEntity servicePackageToSelect = userService.findByServicePackageToSelectID(Long.parseLong(srvPackageToSelect)).get();

            ValidityPeriodEntity validityPeriod = userService.findByValPeriodID(Long.parseLong(valPeriod)).get();
            
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