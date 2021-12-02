package it.polimi.db2project.web;

import it.polimi.db2project.entities.OrderEntity;
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
import java.util.List;

@WebServlet("/serviceActivationSchedule")
public class ServiceActivationScheduleServlet extends HttpServlet {

    @EJB
    UserService userService;

    HttpSession session;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String destServlet;

        destServlet = "homePageCustomer";
        resp.sendRedirect(destServlet);


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        session = req.getSession();

        UserEntity user = (UserEntity) session.getAttribute("user");

        List<OrderEntity> ordersToActivate = userService.findOrdersToActivate(user.getUser_id());
        System.out.println(ordersToActivate);

        for (OrderEntity order: ordersToActivate
             ) {
            System.out.println(order.getOrder_id());
        }
        req.setAttribute("ordersToActivate", ordersToActivate);


        RequestDispatcher dispatcher = req.getRequestDispatcher("serviceActivationSchedule.jsp");
        dispatcher.forward(req, resp);
    }


}
