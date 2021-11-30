package it.polimi.db2project.web;

import it.polimi.db2project.entities.OrderEntity;
import it.polimi.db2project.entities.ServicePackageEntity;
import it.polimi.db2project.entities.ServicePackageToSelectEntity;
import it.polimi.db2project.entities.UserEntity;
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
                userService.createServicePackage(servicePackage, user);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            order = userService.createOrder(new Timestamp(System.currentTimeMillis()), user, servicePackage);
        }
        else{
            order = userService.findOrderByID(Long.parseLong(rejectedOrderID)).get();
        }

        boolean isValid = false;
        if (result.equals("success")) isValid=true;
        else if(result.equals("fail")) isValid=false;
        else if(result.equals("random")) isValid=userService.randomPayment();

        userService.updateOrder(order, isValid);

        if(userService.findRejectedOrdersByUser(user.getUser_id()).size()>=1) userService.setUserInsolvent(user, true);
        else userService.setUserInsolvent(user, false);

        destServlet = "homePageCustomer";

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
