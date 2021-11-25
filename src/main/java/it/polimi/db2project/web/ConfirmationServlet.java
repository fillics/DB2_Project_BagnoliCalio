package it.polimi.db2project.web;

import it.polimi.db2project.entities.ServicePackageEntity;
import it.polimi.db2project.entities.ServicePackageToSelectEntity;
import it.polimi.db2project.services.UserService;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/confirmationPage")
public class ConfirmationServlet extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loggedInStr = req.getParameter("button");
        Boolean loggedIn;
        System.out.println("loggedIn: "+loggedInStr);
        if(loggedInStr.equals("true")) loggedIn=true;
        else loggedIn=false;
        String destServlet;


        if (loggedIn){
            destServlet = "serviceActivationSchedule";

        }
        else{
            destServlet = "confirmationPage?loginNeeded=true";

        }



            /*try {
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
        }*/


        resp.sendRedirect(destServlet);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ServicePackageEntity servicePackage = (ServicePackageEntity) req.getSession(false).getAttribute("servicePackage");
        req.setAttribute("servicePackage", servicePackage);

        RequestDispatcher dispatcher = req.getRequestDispatcher("confirmationPage.jsp");

        dispatcher.forward(req, resp);
    }
}
