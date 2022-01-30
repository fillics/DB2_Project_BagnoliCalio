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
import java.util.List;

@WebServlet("/homePageCustomer")
public class HomePageCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    UserService userService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserEntity user = null;
        HttpSession session = req.getSession();



        List<OrderEntity> rejectedOrders = null;

        if(session.getAttribute("user")!=null){
            user = (UserEntity) session.getAttribute("user");

            if(user.getUsername()!=null) rejectedOrders = userService.findRejectedOrdersByUser(user.getUser_id());

            req.setAttribute("rejectedOrders", rejectedOrders);
        }

        List<ServicePackageToSelectEntity> servicePackagesToSelect = userService.findAllServicePackageToSelect();

        List<ServiceEntity> services = userService.findAllService();

        req.setAttribute("servicePackagesToSelect", servicePackagesToSelect);
        req.setAttribute("services", services);

        RequestDispatcher dispatcher = req.getRequestDispatcher("homeCustomer.jsp");
        dispatcher.forward(req, resp);
    }

}
