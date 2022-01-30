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
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@WebServlet("/homePageEmployee")
public class HomePageEmployeeServlet extends HttpServlet {

    @EJB
    EmployeeService employeeService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<OptionalProductEntity> optionalProducts = employeeService.findAllOptProd();
        List<ServiceEntity> services = employeeService.findAllServices();
        List<ValidityPeriodEntity> validityPeriods = employeeService.findAllValidityPeriods();
        List<ServicePackageToSelectEntity> servicePackagesToSelect = employeeService.findAllServicePackageToSelect();


        req.setAttribute("optionalProducts", optionalProducts);
        req.setAttribute("validityPeriods", validityPeriods);
        req.setAttribute("services", services);
        req.setAttribute("servicePackagesToSelect", servicePackagesToSelect);

        RequestDispatcher dispatcher = req.getRequestDispatcher("homeEmployee.jsp");
        String message;


        if (req.getParameter("creationOptProductFailed") != null) {
            message = "Error in the creation of the optional product, please retry!";
            req.setAttribute("messageOptProduct", message);
        }
        else if (req.getParameter("optProductCreated") != null) {
            message = "Optional Product created successfully!";
            req.setAttribute("messageOptProduct", message);
        }
        else if (req.getParameter("optProductExists") != null) {
            message = "Name Optional Product already exists. Please retry with another name!";
            req.setAttribute("messageOptProduct", message);
        }
        else if (req.getParameter("servPackageCreated") != null) {
            message = "Service Package to select created successfully!";
            req.setAttribute("messageServicePackage", message);
        }
        else if (req.getParameter("creationServPackageFailed") != null) {
            message = "Error in the creation of the service package, please retry!";
            req.setAttribute("messageServicePackage", message);
        }
        else if (req.getParameter("nameAlreadyExist") != null) {
            message = "Name Package already exists. Please retry with another name!";
            req.setAttribute("messageServicePackage", message);
        }
        dispatcher.forward(req, resp);
    }
}
