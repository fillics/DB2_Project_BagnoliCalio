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

@WebServlet("/servicePackage")
public class ServicePackageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private UserService userService;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String valPeriod = request.getParameter("valPeriod");
        String[] optProducts = request.getParameterValues("optProducts");
        String startDateStr = request.getParameter("startDate");
        Date startDate = null;
        Date endDate = null;

        ServicePackageEntity servicePackage = null;
        String destServlet = null;
        float totalValueOptProducts = 0;
        ArrayList<OptionalProductEntity> optionalProducts = new ArrayList<>();

        ValidityPeriodEntity validityPeriod = userService.findByValPeriodID(Long.parseLong(valPeriod)).get();

        for (String optProd : optProducts) {
            optionalProducts.add(userService.findByOptProdID(Long.parseLong(optProd)).get());
        }

        for (OptionalProductEntity optionalProduct : optionalProducts)
            totalValueOptProducts = totalValueOptProducts + optionalProduct.getMonthlyFee();

        float totalValue = totalValueOptProducts + validityPeriod.getMonthlyFee();

        try {
            //converte la stringa della data in un oggetto di classe Date
            DateFormat formatoData = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
            //imposta che i calcoli di conversione della data siano rigorosi
            formatoData.setLenient(false);
            startDate = formatoData.parse(startDateStr);

            //crea un oggetto Calendar o lo imposta alla data d
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
            c.setTime(startDate);

            if (validityPeriod.getNumOfMonths() <= 11 - startDate.getMonth())
                c.add(Calendar.MONTH, validityPeriod.getNumOfMonths());
            else {
                int yearsToAdd = validityPeriod.getNumOfMonths() / 12;
                int monthsToAdd = validityPeriod.getNumOfMonths() % 12;
                c.add(Calendar.YEAR, +yearsToAdd);
                c.add(Calendar.MONTH, +monthsToAdd);
            }
            endDate = c.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //TODO finish
//        try {
//            servicePackage = userService.createServicePackage(
//                startDate,
//                endDate,
//                totalValue,
//                servicePackageTo,
//                validityPeriod,
//                optionalProducts,
//
//
//            );
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        response.sendRedirect(destServlet);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("homeCustomer.jsp");
        dispatcher.forward(req, resp);
    }
}