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
import java.time.LocalDateTime;
import java.util.Random;


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
                System.out.println("service package persistato: "+servicePackage);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            order = userService.createOrder(new Timestamp(System.currentTimeMillis()), user, servicePackage);
            System.out.println("order appena creato: "+order);
        }
        else{
            order = userService.findOrderByID(Long.parseLong(rejectedOrderID)).get();
            System.out.println("dentro appena creato: "+order);
        }


        for (OrderEntity orderEntity: userService.findAllOrdersByUser(user.getUser_id())){
            System.out.println(orderEntity);
        }

        boolean isValid = false;
        if (result.equals("success")) isValid=true;
        else if(result.equals("fail")) isValid=false;
        else if(result.equals("random")) isValid=userService.randomPayment();

        if(!isValid) user = userService.incrementsFailedPayments(user);

        order = userService.updateOrder(order, isValid);
        System.out.println("order updated: "+order);


        if(user.getNumFailedPayments()==3){
            AlertEntity alert = new AlertEntity(order.getTotalValueOrder(), order.getDateAndHour(), user);
            userService.createAlert(alert);
            userService.setNumFailedPagaments(user);
        }

        System.out.println("sopra tutto ok\n\n");
        System.out.println("stampiamo rejected orders");

        for (OrderEntity orderEntity: userService.findRejectedOrdersByUser(user.getUser_id())){
            System.out.println(orderEntity);
        }

        // if the user has rejected orders we set him to insolvent
        if(userService.findRejectedOrdersByUser(user.getUser_id()).size()>=1) userService.setUserInsolvent(user, true);
        else userService.setUserInsolvent(user, false);

        System.out.println("\n\nstampiamo orders to activate");
        for (OrderEntity orderEntity: userService.findOrdersToActivate(user.getUser_id())){
            System.out.println(orderEntity);
        }

        if(userService.findOrdersToActivate(user.getUser_id()).size()!=0) destServlet = "serviceActivationSchedule";
        else destServlet = "homePageCustomer";

        resp.sendRedirect(destServlet);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        rejectedOrderID = req.getParameter("rejectedOrder");

        System.out.println("rejectedOrderID: "+rejectedOrderID);

        if(rejectedOrderID!=null){
            servicePackage = userService.findOrderByID(Long.parseLong(rejectedOrderID)).get().getServicePackage();
            creatingPackage = false;
            System.out.println("service package rejected: "+servicePackage);

        }
        else{
            servicePackage = (ServicePackageEntity) req.getSession(false).getAttribute("servicePackage");
            creatingPackage = true;
            System.out.println("service package non ancora persistato: "+servicePackage);

        }
        req.setAttribute("servicePackage", servicePackage);

        RequestDispatcher dispatcher = req.getRequestDispatcher("confirmationPage.jsp");

        dispatcher.forward(req, resp);
    }



}
