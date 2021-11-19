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
import jakarta.servlet.http.HttpSession;

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
        String user_id = request.getParameter("bottonUser");
        System.out.println(user_id);
        String srvPackageToSelect = request.getParameter("srvPackage");
        String valPeriod = request.getParameter("valPeriod");
        String[] optProducts = request.getParameterValues("optProducts");
        String startDateStr = request.getParameter("startDate");
        Date startDate = null;
        Date endDate = null;
        String destServlet;
        ServicePackageEntity servicePackage = null;

        //se lo user è gia registrato
        if (user_id != null) {
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
            destServlet = "login";
        }

        // se l'utente non è ancora registrato
        else{
            userService.setTheUserWantsToBuyAndHeIsNotLogged(true);
            destServlet = "login";
        }

        response.sendRedirect(destServlet);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("homeCustomer.jsp");
        dispatcher.forward(req, resp);
    }
}