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

// TODO: 15/11/2021 TUTTO SBAGLIAYTOO
@WebServlet("/BuyServlet")
public class BuyServlet extends HttpServlet {
    @EJB
    private UserService userService;

/*
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nameServPackage = request.getParameter("servicePackageToSelect");

        ServicePackageEntity servicePackage = null;
        String destServlet;

        ArrayList<OptionalProductEntity> optionalProductEntities = new ArrayList<>();

        Optional<ServicePackageToSelectEntity> servicePackageEntity = userService.findByServicePackageToSelectID(Long.parseLong(nameServPackage));

        for (String optionalProduct : optionalProducts) {
            optionalProductEntities.add(userService.findByOptProdID(Long.parseLong(optionalProduct)).get());
        }

        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Date startDate = null;
        try {
            startDate = format.parse(startDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LocalDate localDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year  = localDate.getYear();
        int month = localDate.getMonthValue();
        int day   = localDate.getDayOfMonth();

        if (servicePackageEntity.get().getValidityPeriods())


            try {
                servicePackage = userService.createServicePackage(
                        startDate,

                ;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        if (servicePackageToSelect != null) {
            destServlet = "homePageEmployee";
        }
        else
        {
            destServlet = "homePageEmployee?creationServPackageFailed=true";
        }

        response.sendRedirect(destServlet);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("homeCustomer.jsp");
        dispatcher.forward(req, resp);
    }*/
}