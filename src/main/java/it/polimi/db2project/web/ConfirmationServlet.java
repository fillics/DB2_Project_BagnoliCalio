package it.polimi.db2project.web;

import it.polimi.db2project.entities.*;
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
import java.sql.Timestamp;


@WebServlet("/confirmationPage")
public class ConfirmationServlet extends HttpServlet {

    @EJB
    private UserService userService;

    ServicePackageEntity servicePackage;
    boolean creatingPackage = true;
    String rejectedOrderID;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        String result = req.getParameter("result");

        String destServlet;
        OrderEntity order;

        if(creatingPackage){
            try {
                servicePackage = userService.createServicePackage(servicePackage, user);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            order = userService.createOrder(new Timestamp(System.currentTimeMillis()), user, servicePackage);
        }
        else order = userService.findOrderByID(Long.parseLong(rejectedOrderID)).get();
        
        boolean isValid;
        switch (result) {
            case "success":
                isValid = true;
                break;
            case "fail":
                isValid = false;
                break;
            case "random":
                isValid = userService.randomPayment();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + result);
        }

        if(!isValid) user = userService.incrementsFailedPayments(user);

        order = userService.updateOrder(order, isValid);

        if(user.getNumFailedPayments()==3){
            AlertEntity alert = new AlertEntity(order.getTotalValueOrder(), order.getDateAndHour(), user);
            userService.createAlert(alert);
            user = userService.setNumFailedPayments(user);
        }


        // if the user has rejected orders we set him to insolvent
        if(userService.findRejectedOrdersByUser(user.getUser_id()).size()>=1) userService.setUserInsolvent(user, true);
        else userService.setUserInsolvent(user, false);
        
        if(userService.findOrdersToActivate(user.getUser_id()).size()>0) destServlet = "serviceActivationSchedule";
        else destServlet = "homePageCustomer";

        resp.sendRedirect(destServlet);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        rejectedOrderID = req.getParameter("rejectedOrder");

        if(rejectedOrderID!=null){
            servicePackage = userService.findOrderByID(Long.parseLong(rejectedOrderID)).get().getServicePackage();
            creatingPackage = false;

        }
        else{
            servicePackage = (ServicePackageEntity) req.getSession(false).getAttribute("servicePackage");
            creatingPackage = true;
        }
        
        req.setAttribute("servicePackage", servicePackage);

        RequestDispatcher dispatcher = req.getRequestDispatcher("confirmationPage.jsp");
        dispatcher.forward(req, resp);
    }



}
