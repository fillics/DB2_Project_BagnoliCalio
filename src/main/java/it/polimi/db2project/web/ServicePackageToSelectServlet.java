package it.polimi.db2project.web;

import it.polimi.db2project.entities.OptionalProductEntity;
import it.polimi.db2project.entities.ServiceEntity;
import it.polimi.db2project.entities.ServicePackageToSelectEntity;
import it.polimi.db2project.entities.ValidityPeriodEntity;
import it.polimi.db2project.services.EmployeeService;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/servicePackageToSelect")
public class ServicePackageToSelectServlet extends HttpServlet {
    @EJB
    private EmployeeService employeeService;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nameServPackage = request.getParameter("nameServPackage");
        String[] services = request.getParameterValues("services");
        String[] optionalProducts = request.getParameterValues("optionalProducts");
        String[] validityPeriods = request.getParameterValues("validityPeriods");

        ServicePackageToSelectEntity servicePackageToSelect = null;
        String destServlet;

        ArrayList<ServiceEntity> serviceEntities = new ArrayList<>();
        ArrayList<OptionalProductEntity> optionalProductEntities = new ArrayList<>();
        ArrayList<ValidityPeriodEntity> validityPeriodEntities = new ArrayList<>();

        for (String service : services) {
            serviceEntities.add(employeeService.findByServiceID(Long.parseLong(service)).get());
        }
        for (String optionalProduct : optionalProducts) {
            optionalProductEntities.add(employeeService.findByOptProdID(Long.parseLong(optionalProduct)).get());
        }
        for (String validityPeriod : validityPeriods) {
            validityPeriodEntities.add(employeeService.findByValPeriodID(Long.parseLong(validityPeriod)).get());
        }

        try {
            servicePackageToSelect = employeeService.createServicePackageToSelect(nameServPackage, serviceEntities, optionalProductEntities, validityPeriodEntities);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (servicePackageToSelect != null) {
            destServlet = "homePageEmployee?servPackageCreated=true";
        }
        else
        {
            destServlet = "homePageEmployee?creationServPackageFailed=true";
        }

        response.sendRedirect(destServlet);
    }
    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("homeEmployee.jsp");
        dispatcher.forward(req, resp);
    }
}
