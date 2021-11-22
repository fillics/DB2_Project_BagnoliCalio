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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/confirmationPage")
public class ConfirmationServlet extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userID = (String) req.getSession(false).getAttribute("userID");
        System.out.println("UserID: "+userID);

        List<ServicePackageEntity> servicePackage = userService.findServPackageUser(Long.parseLong(userID));

        req.setAttribute("servicePackage", servicePackage.get(servicePackage.size()-1));

        RequestDispatcher dispatcher = req.getRequestDispatcher("confirmationPage.jsp");
        dispatcher.forward(req, resp);
    }
}
